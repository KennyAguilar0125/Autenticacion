CREATE TABLE usuarios (
    id_usuario CHAR(36) NOT NULL DEFAULT (UUID()),  -- Genera automáticamente un UUID
    numero_documento VARCHAR(255) NOT NULL,        -- Número de documento
    nombres VARCHAR(255) NOT NULL,                 -- Nombres
    apellidos VARCHAR(255) NOT NULL,               -- Apellidos
    fecha_nacimiento DATE NULL,                -- Fecha de nacimiento
    direccion VARCHAR(255),                        -- Dirección
    telefono VARCHAR(255),                          -- Teléfono
    correo_electronico VARCHAR(255) NOT NULL,      -- Correo electrónico
    salario_base DECIMAL(15, 2) NOT NULL,         -- Salario base
    PRIMARY KEY (id_usuario),                      -- Clave primaria
    UNIQUE (correo_electronico),                  -- Correo único
    UNIQUE (numero_documento)                     -- Número de documento único
);
