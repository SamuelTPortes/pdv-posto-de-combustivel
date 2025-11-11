package com.br.pdvpostocombustivel.api.produto.dto;

public record ProdutoResponse(
        String nome,
        String referencia,
        String fornecedor,
        String marca
) {}

