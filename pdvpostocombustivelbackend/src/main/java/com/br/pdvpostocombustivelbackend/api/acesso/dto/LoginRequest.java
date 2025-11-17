package com.br.pdvpostocombustivelbackend.api.acesso.dto;

public record LoginRequest(
        String usuario,
        String senha
) {}

