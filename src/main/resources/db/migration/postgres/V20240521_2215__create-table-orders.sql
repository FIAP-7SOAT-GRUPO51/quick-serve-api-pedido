CREATE TABLE orders
(
    order_id BIGSERIAL UNIQUE,
    status character varying(100),
    customer_id BIGSERIAL,
    total_order_value double precision DEFAULT 0.0,
    CONSTRAINT pk_order_id PRIMARY KEY (order_id),
    FOREIGN KEY(customer_id) REFERENCES category (id)
);