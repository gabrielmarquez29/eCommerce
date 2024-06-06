-- V1__intit_tables.sql
-- Creación de la base de datos products si no existe
CREATE DATABASE IF NOT EXISTS products;

-- Cambio al contexto de la base de datos products
USE products;

-- Creación de la tabla category
CREATE TABLE IF NOT EXISTS category
(
	id INT NOT NULL PRIMARY KEY,
	description VARCHAR(255),
	name VARCHAR(255)
);

-- Creación de la tabla product
CREATE TABLE IF NOT EXISTS product
(
	id INT NOT NULL PRIMARY KEY,
	description VARCHAR(255),
	name VARCHAR(255),
	avalailable_quantity DOUBLE PRECISION NOT NULL,
	price NUMERIC (38,2),
	category_id INT 
	CONSTRAINT fk1_constrain_cat FOREIGN KEY (category_id) REFERENCES category(id)
);
-- Creación de secuencias (MySQL usa AUTO_INCREMENT en lugar de SEQUENCE)
CREATE SEQUENCE IF NOT EXISTS category_seq INCREMENT BY 50;
CREATE SEQUENCE IF NOT EXISTS product_seq INCREMENT BY 50;