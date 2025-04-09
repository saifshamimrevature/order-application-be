CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        status VARCHAR(255),
                        created_at TIMESTAMP
);

CREATE TABLE order_items (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             product_name VARCHAR(255),
                             quantity INT,
                             price DOUBLE,
                             order_id BIGINT,
                             CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
