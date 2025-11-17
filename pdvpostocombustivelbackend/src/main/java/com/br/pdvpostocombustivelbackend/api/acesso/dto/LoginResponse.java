package com.br.pdvpostocombustivelbackend.api.acesso.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoAcesso;

public record LoginResponse(
        boolean sucesso,
        String mensagem,
        String usuario,
        TipoAcesso tipoAcesso
) {}

