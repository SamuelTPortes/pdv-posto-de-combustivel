package com.br.pdvpostocombustivel.api.estoque.dto;

import com.br.pdvpostocombustivel.enums.TipoEstoque;
import java.math.BigDecimal;
import java.util.Date;

public record EstoqueRequest(
        BigDecimal quantidade,
        String localTanque,
        String localEndereco,
        String localFabricacao,
        Date dataValidade,
        TipoEstoque tipoEstoque
) {}

