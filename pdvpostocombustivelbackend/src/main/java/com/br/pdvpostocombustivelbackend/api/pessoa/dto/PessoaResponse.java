package com.br.pdvpostocombustivelbackend.api.pessoa.dto;

import java.time.LocalDate;

public record PessoaResponse(
        Long id,
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento
) {}
