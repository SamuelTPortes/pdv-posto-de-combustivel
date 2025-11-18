package com.br.pdvpostocombustivelbackend.api.venda.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoCombustivel;
import com.br.pdvpostocombustivelbackend.enums.TipoPreco;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record VendaRequest(
        String descricaoProduto,
        BigDecimal quantidade,
        BigDecimal valorUnitario,
        LocalDate dataVenda,
        LocalTime horaVenda,
        TipoPreco tipoPreco,
        TipoCombustivel tipoCombustivel,
        String observacoes
) {}

