package com.br.pdvpostocombustivelbackend.api.preco.dto;

import java.math.BigDecimal;
import java.util.Date;

public record PrecoResponse(
        BigDecimal valor,
        Date dataAlteracao,
        Date horaAlteracao
) {}

