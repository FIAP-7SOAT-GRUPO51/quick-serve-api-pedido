package br.com.fiap.techchallenge.quickserveapi.application.handler.controllers;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderItem;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderPaymentStatusEnum;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.ProductDTO;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.Gateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.http.PaymentClient;
import br.com.fiap.techchallenge.quickserveapi.application.handler.http.ProductClient;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.stream.Collectors;

public class OrderRepositoryImpl implements OrderRepository {

    private final Gateway gateway;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;

    @Autowired
    public OrderRepositoryImpl(Gateway gateway, ProductClient productClient, PaymentClient paymentClient) {
        this.gateway = gateway;
        this.productClient = productClient;
        this.paymentClient = paymentClient;
    }

    @Override
    public OrderEntity save(OrderEntity orderEntity) {
        // Salva o pedido via Gateway (microserviço de pedidos)
        Long orderId = gateway.saveOrder(orderEntity);
        orderEntity.setId(orderId);

        // Salva os itens do pedido (microserviço de produtos)
        orderEntity.getOrderItems().forEach(item -> {
            // Chama o microserviço de produtos para salvar no banco
            gateway.saveOrderProduct(orderId, item.getProductId(), item.getQuantity());
        });

        // Processa o pagamento via Feign Client
        paymentClient.processPayment(orderEntity.getId(), orderEntity.getTotalOrderValue());

        return orderEntity;
    }
}

/*
    @Override
    public OrderEntity updateStatus(OrderEntity order) {
        // Atualiza o status do pedido via Gateway
        gateway.updateOrderStatus(order.getId(), order.getStatus());
        return order;
    }





    private long countProductQuantity(OrderEntity orderEntity, Long productId) {
        return orderEntity.getOrderItems().stream().filter(p -> p.getId().equals(productId)).count();
    }

     */
