package com.br.pdvpostocombustivel.api.preco.dto;

import com.br.pdvpostocombustivel.enums.TipoPreco;
import java.math.BigDecimal;
import java.util.Date;

public record PrecoRequest(
        BigDecimal valor,
        Date dataAlteracao,
        Date horaAlteracao,
        TipoPreco tipoPreco
) {}

