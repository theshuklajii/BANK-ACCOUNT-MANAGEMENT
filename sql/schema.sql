-- ShopEase Database Schema

CREATE DATABASE IF NOT EXISTS shopease DEFAULT CHARACTER SET utf8mb4;
USE shopease;

-- Users table ------------------------------------------------------
CREATE TABLE IF NOT EXISTS users (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    email        VARCHAR(100) NOT NULL UNIQUE,
    password     VARCHAR(255) NOT NULL,
    full_name    VARCHAR(100) NOT NULL,
    role         ENUM('CUSTOMER','ADMIN') DEFAULT 'CUSTOMER'
);

-- Products table ---------------------------------------------------
CREATE TABLE IF NOT EXISTS products (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    price       DECIMAL(10,2) NOT NULL,
    category    VARCHAR(50),
    image_url   VARCHAR(255)
);

-- Orders & Order Items --------------------------------------------
CREATE TABLE IF NOT EXISTS orders (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT NOT NULL,
    total_amount  DECIMAL(10,2) NOT NULL,
    address       TEXT NOT NULL,
    status        ENUM('PENDING','SHIPPED') DEFAULT 'PENDING',
    order_date    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS order_items (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    order_id    INT NOT NULL,
    product_id  INT NOT NULL,
    quantity    INT NOT NULL,
    price       DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id)
        ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Sample Data ------------------------------------------------------
INSERT IGNORE INTO users (email, password, full_name, role)
VALUES ('admin@example.com', 'admin123', 'Administrator', 'ADMIN'),
       ('user@example.com',  'user123',  'John Doe',     'CUSTOMER');

INSERT IGNORE INTO products (name, description, price, category)
VALUES ('Laptop',       '14-inch powerful laptop',      799.99, 'Electronics'),
       ('Smartphone',   'Latest model smartphone',      599.99, 'Electronics'),
       ('T-Shirt',      '100% cotton T-shirt',           19.99, 'Apparel');