CREATE DATABASE IF NOT EXISTS servicio_social_db CHARACTER
SET
  utf8mb4 COLLATE utf8mb4_unicode_ci;

USE servicio_social_db;

CREATE TABLE
  IF NOT EXISTS usuarios (
    id_usuario INT NOT NULL AUTO_INCREMENT,
    correo_electronico VARCHAR(255) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    rol ENUM ('Administrador', 'Profesor', 'Alumno') NOT NULL,
    CONSTRAINT pk_usuarios PRIMARY KEY (id_usuario),
    CONSTRAINT uq_usuarios_correo UNIQUE (correo_electronico)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE
  IF NOT EXISTS administradores (
    id_administrador INT NOT NULL AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    CONSTRAINT pk_administradores PRIMARY KEY (id_administrador),
    CONSTRAINT uq_administradores_usuario UNIQUE (id_usuario),
    CONSTRAINT fk_administradores_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON UPDATE CASCADE ON DELETE RESTRICT
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE
  IF NOT EXISTS profesores (
    id_profesor INT NOT NULL AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100),
    CONSTRAINT pk_profesores PRIMARY KEY (id_profesor),
    CONSTRAINT uq_profesores_usuario UNIQUE (id_usuario),
    CONSTRAINT fk_profesores_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON UPDATE CASCADE ON DELETE RESTRICT
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE
  IF NOT EXISTS programas (
    id_programa INT NOT NULL AUTO_INCREMENT,
    nombre_programa VARCHAR(255) NOT NULL,
    descripcion TEXT,
    area_laboratorio VARCHAR(150) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_termino DATE NOT NULL,
    horas_requeridas INT NOT NULL,
    estado_programa ENUM ('Activo', 'Inactivo', 'Finalizado') NOT NULL DEFAULT 'Activo',
    CONSTRAINT pk_programas PRIMARY KEY (id_programa),
    CONSTRAINT chk_programas_fechas CHECK (fecha_termino > fecha_inicio),
    CONSTRAINT chk_programas_horas CHECK (horas_requeridas > 0)
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE
  IF NOT EXISTS programa_profesor (
    id_programa INT NOT NULL,
    id_profesor INT NOT NULL,
    CONSTRAINT pk_programa_profesor PRIMARY KEY (id_programa, id_profesor),
    CONSTRAINT fk_pp_programa FOREIGN KEY (id_programa) REFERENCES programas (id_programa) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_pp_profesor FOREIGN KEY (id_profesor) REFERENCES profesores (id_profesor) ON UPDATE CASCADE ON DELETE RESTRICT
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE
  IF NOT EXISTS alumnos (
    id_alumno INT NOT NULL AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_programa INT NOT NULL,
    numero_cuenta VARCHAR(20) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100),
    carrera VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    estado_alumno ENUM ('Activo', 'Baja', 'Concluido') NOT NULL DEFAULT 'Activo',
    total_horas_acumuladas DECIMAL(8, 2) NOT NULL DEFAULT 0.00,
    CONSTRAINT pk_alumnos PRIMARY KEY (id_alumno),
    CONSTRAINT uq_alumnos_usuario UNIQUE (id_usuario),
    CONSTRAINT uq_alumnos_cuenta UNIQUE (numero_cuenta),
    CONSTRAINT fk_alumnos_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT fk_alumnos_programa FOREIGN KEY (id_programa) REFERENCES programas (id_programa) ON UPDATE CASCADE ON DELETE RESTRICT
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE TABLE
  IF NOT EXISTS registro_horas (
    id_registro INT NOT NULL AUTO_INCREMENT,
    id_alumno INT NOT NULL,
    fecha DATE NOT NULL,
    hora_entrada TIME NOT NULL,
    hora_salida TIME,
    actividades TEXT,
    total_horas_periodo DECIMAL(6, 2),
    modalidad ENUM ('Completa', 'Escalonada') NOT NULL,
    CONSTRAINT pk_registro_horas PRIMARY KEY (id_registro),
    CONSTRAINT fk_registro_alumno FOREIGN KEY (id_alumno) REFERENCES alumnos (id_alumno) ON UPDATE CASCADE ON DELETE RESTRICT,
    CONSTRAINT chk_registro_horas_salida CHECK (
      hora_salida IS NULL
      OR hora_salida > hora_entrada
    )
  ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;

CREATE INDEX idx_usuarios_correo ON usuarios (correo_electronico);

CREATE INDEX idx_programas_estado ON programas (estado_programa);

CREATE INDEX idx_alumnos_programa ON alumnos (id_programa);

CREATE INDEX idx_alumnos_estado ON alumnos (estado_alumno);

CREATE INDEX idx_registro_alumno ON registro_horas (id_alumno);

CREATE INDEX idx_registro_abierto ON registro_horas (id_alumno, hora_salida);

CREATE INDEX idx_registro_fecha ON registro_horas (fecha);

CREATE INDEX idx_pp_profesor ON programa_profesor (id_profesor);

-- DATOS DE PRUEBA
INSERT INTO
  usuarios (correo_electronico, contrasena, rol)
VALUES
  (
    'admin@universidad.edu.mx',
    '123456',
    'Administrador'
  ),
  (
    'profesor@universidad.edu.mx',
    '123456',
    'Profesor'
  ),
  (
    'alejandro@universidad.edu.mx',
    '123456',
    'Alumno'
  ),
  ('ana@universidad.edu.mx', '123456', 'Alumno');

INSERT INTO
  administradores (id_usuario, nombre)
VALUES
  (1, 'Administrador del Sistema');

INSERT INTO
  profesores (
    id_usuario,
    nombre,
    apellido_paterno,
    apellido_materno
  )
VALUES
  (2, 'Roberto', 'Gómez', 'Pérez');

INSERT INTO
  programas (
    nombre_programa,
    descripcion,
    area_laboratorio,
    fecha_inicio,
    fecha_termino,
    horas_requeridas,
    estado_programa
  )
VALUES
  (
    'Desarrollo de Sistema de Control',
    'Refactorización y creación de API REST para el control de asistencia',
    'Laboratorio de Ingeniería de Software',
    '2026-02-01',
    '2026-08-01',
    480,
    'Activo'
  );

INSERT INTO
  programa_profesor (id_programa, id_profesor)
VALUES
  (1, 1);

INSERT INTO
  alumnos (
    id_usuario,
    id_programa,
    numero_cuenta,
    nombre,
    apellido_paterno,
    apellido_materno,
    carrera,
    telefono,
    estado_alumno,
    total_horas_acumuladas
  )
VALUES
  (
    3,
    1,
    '319000001',
    'Alejandro',
    'López',
    'Martínez',
    'Ingeniería en Software',
    '5551234567',
    'Activo',
    10.50
  ),
  (
    4,
    1,
    '319000002',
    'Ana',
    'Ramírez',
    'García',
    'Ingeniería en Software',
    '5557654321',
    'Activo',
    0.00
  );

INSERT INTO
  registro_horas (
    id_alumno,
    fecha,
    hora_entrada,
    hora_salida,
    actividades,
    total_horas_periodo,
    modalidad
  )
VALUES
  (
    1,
    '2026-05-14',
    '08:00:00',
    '12:00:00',
    'Configuración de entorno Docker, bases de datos y healthchecks',
    4.00,
    'Completa'
  ),
  (
    1,
    '2026-05-15',
    '09:00:00',
    '15:30:00',
    'Creación de Dockerfiles multi-stage para backend API y frontend MVC',
    6.50,
    'Escalonada'
  );