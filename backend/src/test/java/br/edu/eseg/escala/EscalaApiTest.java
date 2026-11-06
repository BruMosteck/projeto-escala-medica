package br.edu.eseg.escala;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Testes de ponta a ponta: HTTP -> controller -> serviço -> JPA -> SQLite.
 * Cada execução usa uma CÓPIA temporária de database/escala.sqlite, então o banco original não muda.
 *
 * Dados usados (vindos dos scripts DML da Parte 1):
 *   médico 1  = Ana Ribeiro, Ortopedia, Parcial 30h, já com 30h alocadas (segunda de manhã no Amb. Ortopedia)
 *   médico 25 = Beatriz Falcão, Clínica Médica, Parcial 30h, sem alocação
 *   departamento 1 = Pronto-Socorro Adulto (aceita Clínica Médica e Ortopedia)
 *   departamento 4 = Ambulatório de Ortopedia | departamento 9 = Maternidade (não aceita Ortopedia)
 *   turno 1 = Manhã (6h) | turno 2 = Tarde (6h)
 */
@SpringBootTest
class EscalaApiTest {

    @DynamicPropertySource
    static void bancoTemporario(DynamicPropertyRegistry registry) throws IOException {
        Path copia = Files.createTempFile("escala-teste-", ".sqlite");
        Files.copy(Path.of("../database/escala.sqlite"), copia, StandardCopyOption.REPLACE_EXISTING);
        copia.toFile().deleteOnExit();
        registry.add("spring.datasource.url", () -> "jdbc:sqlite:" + copia + "?foreign_keys=on");
    }

    @Autowired
    private WebApplicationContext contexto;

    @Autowired
    private JdbcTemplate jdbc;

    private MockMvc mvc;

    @BeforeEach
    void configurarMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(contexto).build();
    }

    private static String json(long idMedico, long idDepartamento, long idTurno, int dia) {
        return """
                {"idMedico": %d, "idDepartamento": %d, "idTurno": %d, "diaSemana": %d}
                """.formatted(idMedico, idDepartamento, idTurno, dia);
    }

    // ------------------------------------------------------------------ consultas

    @Test
    void q1_escalaSemanalDeUmMedico() throws Exception {
        mvc.perform(get("/api/medicos/1/escala"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].medico").value("Ana Ribeiro"))
                .andExpect(jsonPath("$[0].dia").value("Segunda-feira"))
                .andExpect(jsonPath("$[0].turno").value("Manhã"))
                .andExpect(jsonPath("$[0].predio").value("Bloco B"))
                .andExpect(jsonPath("$[0].departamento").value("Ambulatório de Ortopedia"));
    }

    @Test
    void q2_quemEstaNoBlocoCNaQuartaATarde() throws Exception {
        mvc.perform(get("/api/alocacoes").param("idPredio", "3").param("dia", "3").param("idTurno", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].predio", everyItem(org.hamcrest.Matchers.is("Bloco C"))));
    }

    @Test
    void q3aq6_relatorios() throws Exception {
        mvc.perform(get("/api/relatorios/medicos-sem-alocacao"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(26)));
        mvc.perform(get("/api/relatorios/alocacoes-incompativeis"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
        mvc.perform(get("/api/relatorios/carga"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(36)))
                .andExpect(jsonPath("$[*].excedeu", everyItem(org.hamcrest.Matchers.is(false))));
        mvc.perform(get("/api/relatorios/ocupacao"))
                .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(26)));
    }

    @Test
    void cadastrosDeApoio() throws Exception {
        mvc.perform(get("/api/predios")).andExpect(jsonPath("$", hasSize(3)));
        mvc.perform(get("/api/departamentos")).andExpect(jsonPath("$", hasSize(9)));
        mvc.perform(get("/api/departamentos/9"))
                .andExpect(jsonPath("$.nome").value("Maternidade"))
                .andExpect(jsonPath("$.especialidadesAceitas", hasSize(3)));
        mvc.perform(get("/api/turnos")).andExpect(jsonPath("$", hasSize(3)));
        mvc.perform(get("/api/especialidades")).andExpect(jsonPath("$", hasSize(12)));
        mvc.perform(get("/api/regimes")).andExpect(jsonPath("$", hasSize(5)));
        mvc.perform(get("/api/medicos").param("idEspecialidade", "4")).andExpect(jsonPath("$", hasSize(3)));
    }

    // ------------------------------------------------------------------ escrita e regras

    @Test
    void criaERemoveAlocacaoValida() throws Exception {
        MvcResult r = mvc.perform(post("/api/alocacoes")
                        .contentType(MediaType.APPLICATION_JSON).content(json(25, 1, 1, 1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.medico").value("Beatriz Falcão"))
                .andExpect(jsonPath("$.departamento").value("Pronto-Socorro Adulto"))
                .andReturn();
        String location = r.getResponse().getHeader("Location");

        mvc.perform(delete(location)).andExpect(status().isNoContent());
        mvc.perform(get(location)).andExpect(status().isNotFound());
    }

    @Test
    void rn01_semConflitoDeHorario() throws Exception {
        // Ana já está na segunda de manhã
        mvc.perform(post("/api/alocacoes").contentType(MediaType.APPLICATION_JSON).content(json(1, 4, 1, 1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.regra").value("RN01"));
    }

    @Test
    void rn02_especialidadeCompativel() throws Exception {
        // Ortopedia não é aceita na Maternidade
        mvc.perform(post("/api/alocacoes").contentType(MediaType.APPLICATION_JSON).content(json(1, 9, 1, 3)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.regra").value("RN02"));
    }

    @Test
    void rn03_cargaDoRegime() throws Exception {
        // Ana (30h) já tem 30h alocadas: mais um turno de 6h estoura a carga
        mvc.perform(post("/api/alocacoes").contentType(MediaType.APPLICATION_JSON).content(json(1, 4, 2, 3)))
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.regra").value("RN03"));
    }

    @Test
    void rn04_diaDaSemanaValido() throws Exception {
        mvc.perform(post("/api/alocacoes").contentType(MediaType.APPLICATION_JSON).content(json(25, 1, 1, 8)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.campos.diaSemana").exists());
    }

    @Test
    void rn06_medicoInexistente() throws Exception {
        mvc.perform(post("/api/alocacoes").contentType(MediaType.APPLICATION_JSON).content(json(999, 1, 1, 1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void rn06_bancoRecusaFkInvalidaMesmoSemPassarPeloServico() {
        // Prova de que "foreign_keys=on" na URL está ativo: um INSERT direto também é recusado
        assertThatThrownBy(() -> jdbc.update(
                "INSERT INTO alocacao (id_medico, id_departamento, id_turno, dia_semana) VALUES (999, 1, 1, 1)"))
                .isInstanceOf(DataAccessException.class)
                .hasMessageContaining("FOREIGN KEY");
    }
}
