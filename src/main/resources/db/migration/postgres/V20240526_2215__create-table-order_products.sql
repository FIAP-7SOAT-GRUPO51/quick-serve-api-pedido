CREATE TABLE order_products
(
    id BIGSERIAL UNIQUE,
    order_id integer,
    product_id integer,
    product_quantity integer,
    CONSTRAINT pk_order_products PRIMARY KEY (id),
    FOREIGN KEY(order_id) REFERENCES orders (order_id),
    FOREIGN KEY(product_id) REFERENCES products (product_id)
);