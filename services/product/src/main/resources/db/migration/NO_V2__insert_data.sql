-- V2__insert_data.sql

-- Cambio al contexto de la base de datos products
USE products;

-- Eliminar tablas existentes si es que ya existen
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category;
-- Creación de la base de datos products si no existe
CREATE DATABASE IF NOT EXISTS products;

-- Creación de la tabla category
CREATE TABLE IF NOT EXISTS category
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255)
);

-- Creación de la tabla product
CREATE TABLE IF NOT EXISTS product
(
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    name VARCHAR(255),
    available_quantity DOUBLE PRECISION NOT NULL,
    price NUMERIC(38,2),
    category_id INT,
    CONSTRAINT fk1_constrain_cat FOREIGN KEY (category_id) REFERENCES category(id)
);

-- Inserción de datos en la tabla category
INSERT INTO category (description, name) VALUES
('Laptops', 'Laptops'),
('PCs de Escritorio', 'PCs de Escritorio'),
('Monitores', 'Monitores'),
('Teclados', 'Teclados'),
('Ratones', 'Ratones'),
('Almacenamiento', 'Almacenamiento'),
('Componentes de PC', 'Componentes de PC'),
('Impresoras', 'Impresoras'),
('Redes', 'Redes'),
('Tarjetas Gráficas', 'Tarjetas Gráficas'),
('Periféricos', 'Periféricos'),
('Audio', 'Audio');

-- Inserción de datos en la tabla product
INSERT INTO product (description, name, available_quantity, price, category_id) VALUES
('Laptop Dell XPS 13', 'Dell XPS 13', 50, 1099.99, 1), -- Laptop - Categoría 1
('Laptop Lenovo ThinkPad X1 Carbon', 'Lenovo ThinkPad X1 Carbon', 30, 1499.99, 1), -- Laptop - Categoría 1
('PC de escritorio HP Pavilion', 'HP Pavilion Desktop PC', 25, 699.99, 2), -- PC de escritorio - Categoría 2
('PC de escritorio Acer Aspire TC', 'Acer Aspire TC Desktop PC', 20, 499.99, 2), -- PC de escritorio - Categoría 2
('Monitor LG UltraWide 34"', 'LG UltraWide 34" Monitor', 15, 399.99, 3), -- Monitor - Categoría 3
('Monitor Samsung Odyssey G7', 'Samsung Odyssey G7 Monitor', 20, 699.99, 3), -- Monitor - Categoría 3
('Teclado mecánico Razer BlackWidow', 'Razer BlackWidow Mechanical Keyboard', 50, 99.99, 4), -- Teclado - Categoría 4
('Teclado Logitech K840', 'Logitech K840 Mechanical Keyboard', 30, 79.99, 4), -- Teclado - Categoría 4
('Mouse inalámbrico Logitech MX Master 3', 'Logitech MX Master 3 Wireless Mouse', 40, 99.99, 5), -- Ratón - Categoría 5
('Mouse gaming Razer DeathAdder V2', 'Razer DeathAdder V2 Gaming Mouse', 30, 69.99, 5), -- Ratón - Categoría 5
('Disco duro externo Seagate Backup Plus 4TB', 'Seagate Backup Plus 4TB External Hard Drive', 30, 99.99, 6), -- Almacenamiento - Categoría 6
('SSD interno Samsung 970 EVO 1TB', 'Samsung 970 EVO 1TB Internal SSD', 20, 149.99, 6), -- Almacenamiento - Categoría 6
('Memoria RAM Corsair Vengeance LPX 16GB', 'Corsair Vengeance LPX 16GB RAM', 40, 79.99, 7), -- Componente de PC - Categoría 7
('Impresora multifunción HP OfficeJet Pro 9015', 'HP OfficeJet Pro 9015 Multifunction Printer', 25, 199.99, 8), -- Impresora - Categoría 8
('Router ASUS RT-AX86U', 'ASUS RT-AX86U Router', 20, 199.99, 9), -- Red - Categoría 9
('Tarjeta gráfica NVIDIA GeForce RTX 3070', 'NVIDIA GeForce RTX 3070 Graphics Card', 30, 499.99, 10), -- Tarjeta Gráfica - Categoría 10
('Webcam Logitech C920', 'Logitech C920 Webcam', 15, 79.99, 11), -- Periférico - Categoría 11
('Auriculares inalámbricos Sony WH-1000XM4', 'Sony WH-1000XM4 Wireless Headphones', 20, 349.99, 12), -- Audio - Categoría 12
('Altavoces Bluetooth JBL Flip 5', 'JBL Flip 5 Bluetooth Speakers', 30, 99.99, 13); -- Audio - Categoría 12
