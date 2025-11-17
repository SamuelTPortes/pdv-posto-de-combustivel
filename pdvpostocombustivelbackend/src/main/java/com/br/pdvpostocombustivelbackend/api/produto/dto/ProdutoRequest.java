package com.br.pdvpostocombustivelbackend.api.produto.dto;

import com.br.pdvpostocombustivelbackend.enums.TipoProduto;

public record ProdutoRequest(
        String nome,
        String referencia,
        String fornecedor,
        String marca,
        TipoProduto tipoProduto
) {}

