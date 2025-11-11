package com.br.pdvpostocombustivel.api.custo.dto;

public record CustoResponse(
        Double imposto,
        Double frete,
        Double seguro,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro
) {}

