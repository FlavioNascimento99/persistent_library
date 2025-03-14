USE javadatabase;

-- Criação da tabela clients
CREATE TABLE clients (
    id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Criação da tabela books
CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL UNIQUE,
    price DOUBLE NOT NULL
);

-- Criação da tabela sales
CREATE TABLE sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT UNSIGNED,
    dateSale DATE NOT NULL,
    totalValue DOUBLE NOT NULL,
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

-- Criação da tabela items_sales
CREATE TABLE items_sales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    quantity INT NOT NULL,
    totalValue DOUBLE NOT NULL,
    sale_id INT,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (sale_id) REFERENCES sales(id)
);


