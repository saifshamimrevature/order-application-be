-- Orders
INSERT INTO orders (status, created_at) VALUES ('PENDING', CURRENT_TIMESTAMP);
INSERT INTO orders (status, created_at) VALUES ('PROCESSING', DATEADD('DAY', -1, CURRENT_TIMESTAMP));
INSERT INTO orders (status, created_at) VALUES ('SHIPPED', DATEADD('DAY', -2, CURRENT_TIMESTAMP));
INSERT INTO orders (status, created_at) VALUES ('DELIVERED', DATEADD('DAY', -3, CURRENT_TIMESTAMP));
INSERT INTO orders (status, created_at) VALUES ('CANCELLED', DATEADD('DAY', -4, CURRENT_TIMESTAMP));

-- Order Items (order_id assumes sequential order IDs 1-5)
INSERT INTO order_items (product_name, quantity, price, order_id) VALUES ('iPhone 15', 1, 999.99, 1);
INSERT INTO order_items (product_name, quantity, price, order_id) VALUES ('AirPods Pro', 2, 249.99, 2);
INSERT INTO order_items (product_name, quantity, price, order_id) VALUES ('MacBook Pro', 1, 2299.99, 3);
INSERT INTO order_items (product_name, quantity, price, order_id) VALUES ('Magic Mouse', 1, 79.99, 4);
INSERT INTO order_items (product_name, quantity, price, order_id) VALUES ('Apple Watch', 1, 399.99, 5);
