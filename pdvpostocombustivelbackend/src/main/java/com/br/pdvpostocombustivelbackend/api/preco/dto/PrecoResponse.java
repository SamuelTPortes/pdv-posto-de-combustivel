package com.br.pdvpostocombustivelbackend.api.preco.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Response DTO para Preco incluindo id para permitir edição via frontend.
 */
public record PrecoResponse(
        Long id,
        BigDecimal valor,
        Date dataAlteracao,
        Date horaAlteracao
) {}
