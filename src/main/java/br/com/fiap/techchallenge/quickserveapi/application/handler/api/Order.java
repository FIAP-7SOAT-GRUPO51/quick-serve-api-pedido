package br.com.fiap.techchallenge.quickserveapi.application.handler.api;

import br.com.fiap.techchallenge.quickserveapi.application.handler.controllers.OrderController;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quick_service/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class Order {

    private final OrderController orderController;

    @Autowired
    public Order(OrderController orderController) {
        this.orderController = orderController;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Inserir novo pedido",
            description = "Este endpoint insere um novo pedido"
    )
    public OrderEntity placeOrder(@RequestBody OrderEntity orderInput) {
        return this.orderController.save(orderInput);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Encontrar Pedido por ID",
            description = "Este endpoint é utilizado para encontrar pedido por ID"
    )
    public OrderResponseDTO FindOrderById(@PathVariable Long id) {
        return this.orderController.findById(id);
    }

    @GetMapping("/payment/{id}")
    @Operation(
            summary = "Encontrar Status do Pagamento por ID",
            description = "Este endpoint é utilizado para encontrar o status do pagamento do Pedido"
    )
    public PaymentStatusDTO checkPaymentStatus(@PathVariable Long id) {
        return this.orderController.checkPaymentStatus(id);
    }

    @GetMapping("/")
    @Operation(
            summary = "Buscar tudo",
            description = "Este endpoint é utilizado para buscar todos os pedido na base"
    )
    public List<OrderResponseDTO> findAll() {
        return orderController.findAll();
    }

    @GetMapping("/list")
    @Operation(
            summary = "Buscar por filtro",
            description = "Este endpoint é utilizado para buscar todos os pedido na base utilizando o filtro ordenado por" +
                    "PRONTO" +
                    "EM_PREPARACAO" +
                    "RECEBIDO"
    )
    public List<OrderResponseDTO> listByFilters(@RequestParam(defaultValue = "order_id ASC") String sortOrder) {
        return orderController.findAllSorted(sortOrder);
    }

    @PutMapping("/{id}/{status}")
    @Operation(
            summary = "Atualiza o status de um pedido",
            description = "Este endpoint atualiza o status de um pedido com base no ID fornecido e no novo status."
    )
    public OrderResponseDTO updateOrderEntityStatus(@PathVariable Long id, @PathVariable OrderStatusEnum status){
        OrderResponseDTO order = this.orderController.findById(id);
        order.setStatus(status.toString()); // Alterar para status do tipo Enum
        return this.orderController.updateStatus(order);
    }

    @PutMapping("/payment-approver/{id}/{statusPayment}")
    @Operation(
            summary = "Atualiza o status do pagamento",
            description = "Este endpoint atualiza o status do pagamento do pedido"
    )
    public OrderResponseDTO paymentApprover(@PathVariable Long id, @PathVariable  OrderPaymentStatusEnum statusPayment) {

        OrderResponseDTO order = this.orderController.findById(id);
        order.setPaymentStatus(String.valueOf(statusPayment));
        return this.orderController.paymentApprover(order);
    }
}



