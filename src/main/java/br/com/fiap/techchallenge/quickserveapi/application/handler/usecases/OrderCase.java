package br.com.fiap.techchallenge.quickserveapi.application.handler.usecases;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.*;
import br.com.fiap.techchallenge.quickserveapi.application.handler.exception.NotFoundException;
import br.com.fiap.techchallenge.quickserveapi.application.handler.gateway.Gateway;
import br.com.fiap.techchallenge.quickserveapi.application.handler.http.PaymentClient;
import br.com.fiap.techchallenge.quickserveapi.application.handler.http.ProductClient;
import org.springframework.http.HttpStatus;

import java.util.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderCase {

    private final Gateway gateway;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;

    public OrderCase(Gateway gateway, PaymentClient paymentClient, ProductClient productClient) {
        this.gateway = gateway;
        this.paymentClient = paymentClient;
        this.productClient = productClient;
    }

    public OrderEntity save(OrderEntity orderEntity) {
        Long orderId = gateway.saveOrder(orderEntity);
        orderEntity.setId(orderId);

        orderEntity.getOrderItems().forEach(item -> {
            gateway.saveOrderProduct(orderId, item.getProductId(), item.getQuantity());
        });

        return orderEntity;
    }

    public OrderResponseDTO findById(Long id) {
        // Recupera o pedido do gateway
        OrderEntity orderEntity = gateway.findOrderById(id);
        if (orderEntity != null) {
            // Criação do DTO de resposta
            List<OrderItemResponseDTO> orderItemResponseDTOs = orderEntity.getOrderItems().stream()
                    .map(item -> {
                        ProductDTO product = productClient.getProductById(item.getProductId());
                        if (product != null) {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    product.getName(),
                                    product.getCategory(),
                                    product.getPrice(),
                                    product.getDescription(),
                                    product.getImagePath()
                            );
                        } else {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    "Produto não encontrado",
                                    "N/A",
                                    0.0,
                                    "Sem descrição",
                                    "Sem imagem"
                            );
                        }
                    })
                    .collect(Collectors.toList());

            // Calcular o valor total do pedido
            Double totalOrderValue = orderEntity.getOrderItems().stream()
                    .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                    .sum();

            // Certifique-se de que os valores estão corretos
            System.out.println("orderItemResponseDTOs: " + orderItemResponseDTOs);

            // Retorno do DTO preenchido
            return new OrderResponseDTO(
                    orderEntity.getId(),
                    String.valueOf(orderEntity.getCustomerID()),
                    orderEntity.getStatus(),
                    orderEntity.getPaymentStatus(),
                    orderItemResponseDTOs,
                    totalOrderValue
            );
        }
        throw new NotFoundException("Pedido não encontrado");
    }

    public PaymentStatusDTO checkPaymentStatus(Long id) {
        // Recupera o pedido do gateway
        PaymentStatusDTO pagamento = gateway.findPaymentStatus(id);

        if (pagamento == null) {
            throw new NotFoundException("Pagamento não encontrado");
        }
        return pagamento;
    }

    public List<OrderResponseDTO> findAll() {
        // Recupera todos os pedidos
        List<OrderEntity> orderEntities = gateway.findAllOrders();
        List<OrderResponseDTO> orderResponseDTOs = new ArrayList<>();

        for (OrderEntity orderEntity : orderEntities) {
            // Para cada pedido, cria os itens de resposta com os produtos
            List<OrderItemResponseDTO> orderItemResponseDTOs = orderEntity.getOrderItems().stream()
                    .map(item -> {
                        // Consultando o produto para cada item do pedido
                        ProductDTO product = productClient.getProductById(item.getProductId());
                        if (product != null) {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    product.getName(),
                                    product.getCategory(),
                                    product.getPrice(),
                                    product.getDescription(),
                                    product.getImagePath()
                            );
                        } else {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    "Produto não encontrado",
                                    "N/A",
                                    0.0,
                                    "Sem descrição",
                                    "Sem imagem"
                            );
                        }
                    })
                    .collect(Collectors.toList());

            // Calculando o valor total do pedido
            Double totalOrderValue = orderEntity.getOrderItems().stream()
                    .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                    .sum();

            // Adiciona o DTO do pedido à lista de respostas
            orderResponseDTOs.add(new OrderResponseDTO(
                    orderEntity.getId(),
                    String.valueOf(orderEntity.getCustomerID()),
                    orderEntity.getStatus(),
                    orderEntity.getPaymentStatus(),
                    orderItemResponseDTOs,
                    totalOrderValue
            ));
        }

        return orderResponseDTOs; // Retorna a lista de pedidos com os produtos
    }

    public List<OrderResponseDTO> listByFiltersWithSorting(String sortOrder) {
        // Mapeamento de caso para a ordenação
        Map<String, Integer> caseFiltros = new HashMap<>();
        caseFiltros.put("PRONTO", 1);
        caseFiltros.put("EM_PREPARACAO", 2);
        caseFiltros.put("RECEBIDO", 3);

        // Chama o Gateway para buscar os pedidos com a ordenação e filtros
        List<OrderEntity> orderEntities = gateway.findOrdersWithSorting(caseFiltros, sortOrder);

        // Mapeia os resultados para DTOs de pedido e enriquece com os dados dos produtos
        return orderEntities.stream()
                .map(orderEntity -> {
                    // Enriquecer os itens do pedido com os dados dos produtos
                    List<OrderItemResponseDTO> orderItemResponseDTOs = orderEntity.getOrderItems().stream()
                            .map(item -> {
                                // Enriquecer os dados de produto usando o client
                                ProductDTO product = productClient.getProductById(item.getProductId());
                                if (product != null) {
                                    return new OrderItemResponseDTO(
                                            item.getProductId(),
                                            product.getName(),
                                            product.getCategory(),
                                            product.getPrice(),
                                            product.getDescription(),
                                            product.getImagePath()
                                    );
                                } else {
                                    return new OrderItemResponseDTO(
                                            item.getProductId(),
                                            "Produto não encontrado",
                                            "N/A",
                                            0.0,
                                            "Sem descrição",
                                            "Sem imagem"
                                    );
                                }
                            })
                            .collect(Collectors.toList());

                    // Calcula o valor total do pedido
                    Double totalOrderValue = orderEntity.getOrderItems().stream()
                            .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                            .sum();

                    // Retorna o DTO do pedido enriquecido
                    return new OrderResponseDTO(
                            orderEntity.getId(),
                            String.valueOf(orderEntity.getCustomerID()),
                            orderEntity.getStatus(),
                            orderEntity.getPaymentStatus(),
                            orderItemResponseDTOs,
                            totalOrderValue
                    );
                })
                .collect(Collectors.toList());
    }

    public OrderResponseDTO updateStatus(OrderResponseDTO order) {
        // Assegure que o status está sendo passado como OrderStatusEnum
        OrderEntity orderEntity = gateway.updateOrderStatus(order.getId(), OrderStatusEnum.valueOf(order.getStatus()));

        if (orderEntity != null) {
            // Mapeamento dos itens do pedido
            List<OrderItemResponseDTO> orderItemResponseDTOs = orderEntity.getOrderItems().stream()
                    .map(item -> {
                        ProductDTO product = productClient.getProductById(item.getProductId());
                        if (product != null) {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    product.getName(),
                                    product.getCategory(),
                                    product.getPrice(),
                                    product.getDescription(),
                                    product.getImagePath()
                            );
                        } else {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    "Produto não encontrado",
                                    "N/A",
                                    0.0,
                                    "Sem descrição",
                                    "Sem imagem"
                            );
                        }
                    })
                    .collect(Collectors.toList());

            // Cálculo do valor total do pedido
            Double totalOrderValue = orderEntity.getOrderItems().stream()
                    .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                    .sum();

            return new OrderResponseDTO(
                    orderEntity.getId(),
                    String.valueOf(orderEntity.getCustomerID()),
                    orderEntity.getStatus(),
                    orderEntity.getPaymentStatus(),
                    orderItemResponseDTOs,
                    totalOrderValue
            );
        }
        return null;  // Caso o pedido não seja encontrado
    }

    public OrderResponseDTO updatePayment(OrderResponseDTO order) {
        // Assegure que o status está sendo passado como OrderStatusEnum
        OrderEntity orderEntity = gateway.updatePaymentStatus(order.getId(), OrderPaymentStatusEnum.valueOf(order.getPaymentStatus()));

        if (orderEntity != null) {
            // Mapeamento dos itens do pedido
            List<OrderItemResponseDTO> orderItemResponseDTOs = orderEntity.getOrderItems().stream()
                    .map(item -> {
                        ProductDTO product = productClient.getProductById(item.getProductId());
                        if (product != null) {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    product.getName(),
                                    product.getCategory(),
                                    product.getPrice(),
                                    product.getDescription(),
                                    product.getImagePath()
                            );
                        } else {
                            return new OrderItemResponseDTO(
                                    item.getProductId(),
                                    "Produto não encontrado",
                                    "N/A",
                                    0.0,
                                    "Sem descrição",
                                    "Sem imagem"
                            );
                        }
                    })
                    .collect(Collectors.toList());

            // Cálculo do valor total do pedido
            Double totalOrderValue = orderEntity.getOrderItems().stream()
                    .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                    .sum();

            return new OrderResponseDTO(
                    orderEntity.getId(),
                    String.valueOf(orderEntity.getCustomerID()),
                    orderEntity.getStatus(),
                    orderEntity.getPaymentStatus(),
                    orderItemResponseDTOs,
                    totalOrderValue
            );
        }
        return null;  // Caso o pedido não seja encontrado
    }
}