package com.br.pdvpostocombustivelbackend.api.estoque.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoEstoque;
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

