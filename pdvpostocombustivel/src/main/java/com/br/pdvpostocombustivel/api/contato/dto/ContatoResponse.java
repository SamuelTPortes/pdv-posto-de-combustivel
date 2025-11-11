package com.br.pdvpostocombustivel.api.contato.dto;

public record ContatoResponse(
        String telefone,
        String email,
        String endereco
) {}

