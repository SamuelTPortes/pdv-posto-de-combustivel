package com.br.pdvpostocombustivel.api.preco.dto;

import java.math.BigDecimal;
import java.util.Date;

public record PrecoResponse(
        BigDecimal valor,
        Date dataAlteracao,
        Date horaAlteracao
) {}

