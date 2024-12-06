# Order Application - Microsserviço Pedido

Esse é um microserviço em Java Spring Boot para gerenciamento de pedidos. Esse microsserviço disponibiliza APIs para manipular operações de criação de pedidos e status atual de pagamento e produção.

## Tecnologias utilizadas

- Java
- Spring Boot
- Maven
- JUnit
- Mockito
- Cucumber

## Features

- Recebe um um pedido realizado pelo cliente
- API para ser consumida pelo microsserviço de pagamento para informar status do pagamento
- API para ser consumida pelo microsserviço de produção para informar status da produção do pedido
- Consome API do microsserviço ADM para enriquecer as informações de produto, na consulta de pedido

## Extrutura do projeto

- `src/main/java`: Contem o código da aplicação
- `src/test/java`: Contem os testes unitários
- `src/test/resources`: Contem os arquivos de configuração para testes BDD (Cucumber e Mockito)

## Rodando a aplicação

To run the application, use the following command:

```sh
mvn spring-boot:run

```

## Evidência da cobertura de testes


