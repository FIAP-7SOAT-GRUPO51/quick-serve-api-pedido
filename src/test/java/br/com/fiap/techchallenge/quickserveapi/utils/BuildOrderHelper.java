package br.com.fiap.techchallenge.quickserveapi.utils;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.*;

import java.util.ArrayList;
import java.util.List;

public abstract class BuildOrderHelper {

    public static OrderPostEntity buildOrder() {
        // Aqui você pode passar valores fixos ou parâmetros para customizar os valores
        OrderPostEntity order = new OrderPostEntity();

        order.setId(1L);
        order.setCustomerID(123L);
        order.setStatus(OrderStatusEnum.RECEBIDO); // Substitua pelo valor do enum necessário

        order.setPaymentStatus(OrderPaymentStatusEnum.PENDENTE); // Substitua pelo valor do enum necessário

        // Adicionando um exemplo de OrderItem à lista
        List<OrderDTO> orderItems = new ArrayList<>();
        // Criação do item de pedido com preenchimento dos dados
        OrderDTO item = new OrderDTO();
        item.setId(1L);
        item.setQuantity(2);
        orderItems.add(item);

        order.setOrderDTOs(orderItems);
        order.setTotalOrderValue(250.0); // Exemplo de valor total
        System.out.println(order);

        return order;

    }
}