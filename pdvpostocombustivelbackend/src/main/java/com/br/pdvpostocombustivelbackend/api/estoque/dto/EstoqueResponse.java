package com.br.pdvpostocombustivelbackend.api.estoque.dto;

import java.math.BigDecimal;
import java.util.Date;

public record EstoqueResponse(
        BigDecimal quantidade,
        String localTanque,
        String localEndereco,
        String localFabricacao,
        Date dataValidade
) {}

