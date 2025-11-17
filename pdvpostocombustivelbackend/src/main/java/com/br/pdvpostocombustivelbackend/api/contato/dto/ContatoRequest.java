package com.br.pdvpostocombustivelbackend.api.contato.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoContato;

public record ContatoRequest(
        String telefone,
        String email,
        String endereco,
        TipoContato tipoContato
) {}

