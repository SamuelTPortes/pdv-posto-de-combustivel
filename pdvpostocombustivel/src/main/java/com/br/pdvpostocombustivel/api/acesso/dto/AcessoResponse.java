package com.br.pdvpostocombustivel.api.acesso.dto;

import com.br.pdvpostocombustivel.enums.TipoAcesso;

import java.time.LocalDate;

public record AcessoResponse(
        String usuario,
        String senha
) {}