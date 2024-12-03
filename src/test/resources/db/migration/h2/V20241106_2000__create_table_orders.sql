CREATE TABLE orders
(
    id BIGINT AUTO_INCREMENT UNIQUE,
    status VARCHAR(100) NOT NULL,
    customer_id VARCHAR(13) NOT NULL,
    total_order_value float NOT NULL,
    payment_status VARCHAR(100) NOT NULL,
    CONSTRAINT pk_order_id PRIMARY KEY (id)
);