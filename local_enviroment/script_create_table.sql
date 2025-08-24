CREATE TABLE usuarios (
    id_usuario CHAR(36) NOT NULL DEFAULT (UUID()),  -- Genera automáticamente un UUID
    numero_documento VARCHAR(255) NOT NULL,  -- Longitud arbitraria para el número de documento
    nombres VARCHAR(255) NOT NULL,  -- Longitud arbitraria para nombres
    apellidos VARCHAR(255) NOT NULL,  -- Longitud arbitraria para apellidos
    fecha_nacimiento DATE NOT NULL,  -- Tipo de datos para la fecha
    direccion VARCHAR(255),  -- Longitud arbitraria para dirección
    telefono VARCHAR(255),  -- Longitud arbitraria para teléfono
    correo_electronico VARCHAR(255) NOT NULL,  -- Longitud arbitraria para el correo electrónico
    salario_base DECIMAL(15, 2) NOT NULL,  -- Tipo adecuado para valores decimales
    PRIMARY KEY (id_usuario)  -- Definimos la columna `id_usuario` como clave primaria
)
