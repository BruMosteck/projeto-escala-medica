CREATE TABLE alocacao (
    id_alocacao INTEGER PRIMARY KEY AUTOINCREMENT,
    id_medico INTEGER NOT NULL,
    id_departamento INTEGER NOT NULL,
    id_turno INTEGER NOT NULL,
    dia INTEGER NOT NULL CHECK (dia >= 1 AND dia <= 7),
    FOREIGN KEY (id_medico) REFERENCES medico(id_medico),
    FOREIGN KEY (id_departamento) REFERENCES departamento(id_departamento),
    FOREIGN KEY (id_turno) REFERENCES turno(id_turno),
    UNIQUE (id_medico, dia, id_turno)
);