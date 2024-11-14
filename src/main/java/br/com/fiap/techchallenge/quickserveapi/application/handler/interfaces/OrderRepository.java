package br.com.fiap.techchallenge.quickserveapi.application.handler.interfaces;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;

import java.util.List;


public interface OrderRepository {

    OrderEntity save(OrderEntity order);
 /*

    OrderEntity updateStatus(OrderEntity order);

    List<OrderEntity> findAll();

     */
}
