package br.com.fiap.techchallenge.quickserveapi.application.handler.entities;

public class OrderItem {

    private Long productId;         // ID do produto associado, ignorado no JSON
    private int quantity;           // Quantidade do produto no pedido, ignorado no JSON
    private double priceAtPurchase; // Preço no momento da compra, ignorado no JSON
    private String name;            // Nome do produto, ignorado no JSON
    private ProductDTO product;     // Objeto ProductDTO com os detalhes completos do produto

    // Construtor sem parâmetros
    public OrderItem() {}

    // Construtor com todos os parâmetros
    public OrderItem(Long productId, int quantity, double priceAtPurchase, String name) {
        this.productId = productId;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
        this.name = name;
    }

    // Getters e Setters
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
        // Se o produto for definido, podemos atualizar o preço e o nome automaticamente
        if (product != null) {
            this.priceAtPurchase = product.getPrice();
            this.name = product.getName();
        }
    }

    // Método para calcular o preço total do item
    public double getTotalPrice() {
        return priceAtPurchase * quantity;
    }

    // Método para atualizar o preço, caso haja alguma alteração no produto
    public void updatePrice() {
        if (product != null) {
            this.priceAtPurchase = product.getPrice();
        }
    }

    // Método para exibir informações do item de forma legível
    @Override
    public String toString() {
        return "OrderItem [productId=" + productId + ", quantity=" + quantity + ", priceAtPurchase=" + priceAtPurchase
                + ", name=" + name + ", product=" + product + "]";
    }
}