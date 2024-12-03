CREATE TABLE order_products
(
    id BIGINT PRIMARY KEY UNIQUE,
    order_id BIGINT,           -- Referência ao pedido
    product_id BIGINT,         -- Identificador do produto, sem chave estrangeira
    product_quantity INTEGER   -- Quantidade do produto
);