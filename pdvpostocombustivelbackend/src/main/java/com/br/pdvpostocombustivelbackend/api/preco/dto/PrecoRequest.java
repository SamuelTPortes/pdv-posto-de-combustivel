package com.br.pdvpostocombustivelbackend.api.preco.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoPreco;
import java.math.BigDecimal;
import java.util.Date;

public record PrecoRequest(
        BigDecimal valor,
        Date dataAlteracao,
        Date horaAlteracao,
        TipoPreco tipoPreco
) {}

