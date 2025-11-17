package com.br.pdvpostocombustivelbackend.api.acesso.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoAcesso;


public record AcessoRequest(
         String usuario,
         String senha,
         TipoAcesso tipoAcesso
) {}
