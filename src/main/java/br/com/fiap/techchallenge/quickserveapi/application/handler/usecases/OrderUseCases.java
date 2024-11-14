package br.com.fiap.techchallenge.quickserveapi.application.handler.usecases;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces.OrderRepository;

import java.util.List;

public class OrderUseCases implements OrderRepository {

    public final OrderRepository orderRepository;

    public OrderUseCases (OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public OrderEntity save(OrderEntity order) {
        return this.orderRepository.save(order);
    }
 /*


    public OrderEntity updateStatus(OrderEntity order) {
        return this.orderRepository.updateStatus(order);
    }

    public List<OrderEntity> findAll() {
        return this.orderRepository.findAll();
    }

     */
}
