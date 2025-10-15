package com.br.pdvpostocombustivel.api.pessoa.dto;

import java.time.LocalDate;

public record PessoaResponse(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento
) {}