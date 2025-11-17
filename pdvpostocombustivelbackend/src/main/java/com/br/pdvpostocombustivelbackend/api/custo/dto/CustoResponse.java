package com.br.pdvpostocombustivelbackend.api.custo.dto;

public record CustoResponse(
        Double imposto,
        Double frete,
        Double seguro,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro
) {}

