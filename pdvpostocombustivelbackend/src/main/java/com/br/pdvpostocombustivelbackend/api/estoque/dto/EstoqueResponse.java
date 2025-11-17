package com.br.pdvpostocombustivelbackend.api.estoque.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.br.pdvpostocombustivelbackend.enums.TipoEstoque;

/**
 * Response DTO para Estoque incluindo id e tipoEstoque
 */
public record EstoqueResponse(
        Long id,
        BigDecimal quantidade,
        String localTanque,
        String localEndereco,
        String localFabricacao,
        Date dataValidade,
        TipoEstoque tipoEstoque
) {}
