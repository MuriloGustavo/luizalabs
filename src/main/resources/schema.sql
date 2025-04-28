CREATE TABLE clients (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    image VARCHAR(255),
    price DECIMAL(10, 2),
    review VARCHAR(255),
    client_id BIGINT,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES clients(id),
    CONSTRAINT uc_client_product UNIQUE (client_id, product_id)
);
