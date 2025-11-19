package com.br.pdvpostocombustivelbackend.api.pessoa.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoPessoa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;


public record PessoaRequest(
        @NotBlank String nomeCompleto,
        @NotBlank @CPF String cpfCnpj,
        @NotNull @PastOrPresent LocalDate dataNascimento,
        Long numeroCtps,
        @NotNull TipoPessoa tipoPessoa,
        String email,
        String telefone,
        String endereco
) {}
