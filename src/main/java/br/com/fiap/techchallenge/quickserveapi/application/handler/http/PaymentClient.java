package br.com.fiap.techchallenge.quickserveapi.application.handler.http;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderPaymentStatusEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service", url = "${payment-service.url}")
public interface PaymentClient {

    @GetMapping("/payments/{orderId}")
    String checkPaymentStatus(@PathVariable Long orderId);

    @PutMapping("/payments/{orderId}")
    void updatePaymentStatus(@PathVariable Long orderId, @RequestBody OrderPaymentStatusEnum status);

    @PostMapping("/payments")
    void processPayment(@RequestParam("orderId") Long orderId, @RequestParam("totalAmount") Double totalAmount);

    @GetMapping("/payments/{orderId}")
    String getPaymentStatus(@PathVariable("orderId") Long orderId);
}


