package com.br.pdvpostocombustivelbackend.api.contato.dto;

public record ContatoResponse(
        String telefone,
        String email,
        String endereco
) {}

