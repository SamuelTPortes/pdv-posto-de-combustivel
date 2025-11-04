package com.br.pdvpostocombustivel.enums;

public enum TipoPreco {

    UNITARIO("Preco por Valor Unitario"),
    TOTAL("Preco por Valor Total");

    private final String descricao;

    private TipoPreco(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
