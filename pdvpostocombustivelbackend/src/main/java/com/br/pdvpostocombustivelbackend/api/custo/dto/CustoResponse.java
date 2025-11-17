package com.br.pdvpostocombustivelbackend.api.custo.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoCusto;

/**
 * Response DTO para Custo incluindo id e tipoCusto para que o frontend possa identificar e editar registros.
 */
public record CustoResponse(
        Long id,
        Double imposto,
        Double frete,
        Double seguro,
        Double custoVariavel,
        Double custoFixo,
        Double margemLucro,
        TipoCusto tipoCusto
) {}
