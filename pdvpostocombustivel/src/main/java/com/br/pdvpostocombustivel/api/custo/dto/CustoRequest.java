package com.br.pdvpostocombustivel.api.custo.dto;

import com.br.pdvpostocombustivel.enums.TipoCusto;

public record CustoRequest(
        Double imposto,
        Double frete,
        Double seguro,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro,
        TipoCusto tipoCusto
) {}

