package com.br.pdvpostocombustivelbackend.api.contato.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoContato;

/**
 * Response DTO para Contato incluindo id e tipoContato para permitir edição no frontend.
 */
public record ContatoResponse(
        Long id,
        String telefone,
        String email,
        String endereco,
        TipoContato tipoContato
) {}
