DROP DATABASE IF EXISTS controlescolar;
SET NAMES 'utf8' COLLATE 'utf8_spanish_ci';
CREATE DATABASE controlescolar DEFAULT CHARACTER SET utf8;
DROP USER IF EXISTS 'IngSW2021'@'localhost';
CREATE USER 'IngSW2021'@'localhost' IDENTIFIED BY 'UAZsw2021';
GRANT ALL ON controlescolar.* TO 'IngSW2021'@'localhost';

USE controlescolar;

-- -----------------------------------------------------
-- Tabla estado
-- -----------------------------------------------------
DROP TABLE IF EXISTS estado;

CREATE TABLE IF NOT EXISTS estado (
  id_estado SMALLINT NOT NULL,
  nombre_estado VARCHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
  PRIMARY KEY (id_estado))
  COMMENT = 'Catálogo de estados';


-- -----------------------------------------------------
-- Tabla municipio
-- -----------------------------------------------------
DROP TABLE IF EXISTS municipio ;

CREATE TABLE IF NOT EXISTS municipio (
  id_municipio INT NOT NULL,
  nombre_municipio VARCHAR(50) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
  PRIMARY KEY (id_municipio))
  COMMENT = 'Catalogo de municipios';


-- -----------------------------------------------------
-- Tabla carrera
-- -----------------------------------------------------
DROP TABLE IF EXISTS carrera;
  
CREATE TABLE IF NOT EXISTS carrera (
  clave_carrera CHAR(10),
  nombre_carrera VARCHAR (50) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
  PRIMARY KEY (clave_carrera))
  COMMENT = 'carreras ofertadas';


-- -----------------------------------------------------
-- Tabla materia
-- -----------------------------------------------------
DROP TABLE IF EXISTS materia;

CREATE TABLE IF NOT EXISTS materia (
    clave_materia CHAR(10) PRIMARY KEY,
    nombre_materia VARCHAR (50) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
    semestre TINYINT UNSIGNED NOT NULL,
    clave_carrera CHAR(10) NOT NULL)
	COMMENT = 'Información sobre materias de cada carrera';
    

-- -----------------------------------------------------
-- Tabla periodoEscolar
-- -----------------------------------------------------
DROP TABLE IF EXISTS periodoescolar;

CREATE TABLE IF NOT EXISTS periodoescolar (
    id_periodo INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    year SMALLINT UNSIGNED NOT NULL,
    periodo TINYINT UNSIGNED NOT NULL COMMENT '1(Ago-Dic), 2(Ene-Jun), 3(Verano)')
	COMMENT = 'Información sobre períodos escolares';


-- -----------------------------------------------------
-- Tabla estudiante
-- -----------------------------------------------------
DROP TABLE IF EXISTS estudiante;

CREATE TABLE IF NOT EXISTS estudiante (
    matricula CHAR(8) PRIMARY KEY,
    nombre VARCHAR (25) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
    ap_paterno VARCHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
    ap_materno VARCHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci',
    calle VARCHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci',
    colonia VARCHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci',    
    cod_postal CHAR(5),
    telefono CHAR(10),
    email VARCHAR(30) NOT NULL,
    id_estado SMALLINT,
	id_municipio INT)
	COMMENT='Información sobre estudiantes';
    


-- -----------------------------------------------------
-- Tabla profesor
-- -----------------------------------------------------
DROP TABLE IF EXISTS profesor;

CREATE TABLE IF NOT EXISTS profesor (
    rfc CHAR(13) PRIMARY KEY,
    nombre VARCHAR (25) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
    ap_paterno VARCHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci' NOT NULL,
    ap_materno VARCHAR(20) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci',
    calle VARCHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci',
    colonia VARCHAR(30) CHARACTER SET 'utf8' COLLATE 'utf8_spanish_ci',
    cod_postal CHAR(5),
    telefono CHAR(10),
    email VARCHAR(30) NOT NULL,
    id_estado SMALLINT,
	id_municipio INT)
	COMMENT='Información sobre profesores';
    

-- -----------------------------------------------------
-- Tabla cargaestudiante
-- -----------------------------------------------------
DROP TABLE IF EXISTS cargaestudiante;

CREATE TABLE IF NOT EXISTS cargaestudiante (
	id_carga_estudiante INT NOT NULL AUTO_INCREMENT,
    matricula CHAR(8) NOT NULL,
    clave_materia CHAR(10) NOT NULL,
    id_periodo INT UNSIGNED NOT NULL,    
    PRIMARY KEY(id_carga_estudiante))
	COMMENT='Información sobre carga de estudiantes';

-- -----------------------------------------------------
-- Tabla cargaprofesor
-- -----------------------------------------------------
DROP TABLE IF EXISTS cargaprofesor;

CREATE TABLE IF NOT EXISTS cargaprofesor (
	id_carga_profesor INT NOT NULL AUTO_INCREMENT,
    rfc CHAR(13) NOT NULL,
    clave_materia CHAR(10) NOT NULL,
    id_periodo INT UNSIGNED NOT NULL,
    PRIMARY KEY(id_carga_profesor))
	COMMENT='Información sobre carga de profesores';
