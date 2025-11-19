package com.br.pdvpostocombustivelbackend.api.pessoa.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoPessoa;

import java.time.LocalDate;

public record PessoaResponse(
        Long id,
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa,
        String email,
        String telefone,
        String endereco
) {
}
