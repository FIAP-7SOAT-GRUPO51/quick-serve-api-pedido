CREATE TABLE products
(
    product_id BIGSERIAL UNIQUE,
    name character varying(50),
    category_id BIGSERIAL,
    description character varying(100),
    price double precision DEFAULT 0.0,
    image_path character varying(100),
    CONSTRAINT pk_products_id PRIMARY KEY (product_id),
    FOREIGN KEY(category_id) REFERENCES customers (id)
);