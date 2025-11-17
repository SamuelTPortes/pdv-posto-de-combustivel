package com.br.pdvpostocombustivelbackend.api.pessoa.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoPessoa;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public record PessoaRequest(
        String nomeCompleto,
        String cpfCnpj,
        Long numeroCtps,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataNascimento,
        TipoPessoa tipoPessoa
) {}
