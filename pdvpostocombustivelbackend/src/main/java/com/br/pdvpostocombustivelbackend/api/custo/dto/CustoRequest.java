package com.br.pdvpostocombustivelbackend.api.custo.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoCusto;

public record CustoRequest(
        Double imposto,
        Double frete,
        Double seguro,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro,
        TipoCusto tipoCusto
) {}

