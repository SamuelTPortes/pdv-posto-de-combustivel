package com.br.pdvpostocombustivelbackend.api.acesso.dto;

public record AcessoResponse(
        Long id,
        String usuario,
        String senha,
        String tipoAcesso
) {}
