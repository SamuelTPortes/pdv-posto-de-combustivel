package com.br.pdvpostocombustivel.enums;

public enum TipoEstoque {

    MINIMO("Estoque Minimo"),
    VENDAS("Estoque de Vendas"),
    RESERVA("Estoque de Reserva");

    private final String descricao;

    private TipoEstoque(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
