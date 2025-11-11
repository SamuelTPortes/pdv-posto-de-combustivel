package com.br.pdvpostocombustivel.api.contato.dto;

import com.br.pdvpostocombustivel.enums.TipoContato;

public record ContatoRequest(
        String telefone,
        String email,
        String endereco,
        TipoContato tipoContato
) {}

