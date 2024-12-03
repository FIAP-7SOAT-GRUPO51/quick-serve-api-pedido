CREATE TABLE order_products
(
    id BIGINT AUTO_INCREMENT UNIQUE,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_quantity INTEGER,
    CONSTRAINT pk_order_products_id PRIMARY KEY (id)
);