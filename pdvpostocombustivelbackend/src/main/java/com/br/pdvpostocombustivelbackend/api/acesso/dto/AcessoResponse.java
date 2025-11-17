package com.br.pdvpostocombustivelbackend.api.acesso.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoAcesso;

import java.time.LocalDate;

public record AcessoResponse(
        String usuario,
        String senha
) {}
