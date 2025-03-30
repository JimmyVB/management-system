CREATE TABLE clients (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         age INT NOT NULL,
                         birth_date DATE NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
