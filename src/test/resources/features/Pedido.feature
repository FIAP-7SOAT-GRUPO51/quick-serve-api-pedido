# language: pt

Funcionalidade: Pedido

    Cenario: Registrar Pedido
        Quando registar um novo pedido
        Então o pedido é registrado com sucesso
        E deve ser apresentado

    Cenario: Buscar Pedido
        Dado que um pedido ja foi publicado
        Quando efetuar a busca pelo pedido
        Então o pedido é exibido com sucesso