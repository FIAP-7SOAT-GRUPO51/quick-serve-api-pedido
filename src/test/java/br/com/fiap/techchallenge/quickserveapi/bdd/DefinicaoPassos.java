package br.com.fiap.techchallenge.quickserveapi.bdd;

import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderPostEntity;
import br.com.fiap.techchallenge.quickserveapi.application.handler.entities.OrderResponseDTO;
import br.com.fiap.techchallenge.quickserveapi.utils.BuildOrderHelper;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class DefinicaoPassos {

    private Response response;

    private OrderResponseDTO orderResponse;

    private final String ENDPOINT_API_MENSAGENS = "http://localhost:8088/quick_service/orders";

    @Quando("registar um novo pedido")
    public OrderResponseDTO registar_um_novo_pedido() {
        var orderRequest = BuildOrderHelper.buildOrder();

        response = given().contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderRequest)
                .when().post(ENDPOINT_API_MENSAGENS);

        // Log the response body to see its content
        System.out.println("Response Body: " + response.getBody().asString());

        return response.then().extract().as(OrderResponseDTO.class);
    }
    @Então("o pedido é registrado com sucesso")
    public void a_mensagem_é_registrada_com_sucesso() {
        response.then().statusCode(HttpStatus.CREATED.value())
                .body(matchesJsonSchemaInClasspath("schemas/order.schema.json"));
    }

    @Então("deve ser apresentado")
    public void deve_ser_apresentada() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/order.schema.json"));
    }

    @Dado("que um pedido ja foi publicado")
    public void que_uma_mensagem_ja_foi_publicada() {
        orderResponse = registar_um_novo_pedido();
    }

    @Quando("efetuar a busca pelo pedido")
    public void efetuar_a_busca_pela_mensagem() {
        response = when().get(ENDPOINT_API_MENSAGENS + "/{id}", orderResponse.getId());
    }

    @Então("o pedido é exibido com sucesso")
    public void a_mensagem_é_exibida_com_sucesso() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/order.schema.json"));
    }
}