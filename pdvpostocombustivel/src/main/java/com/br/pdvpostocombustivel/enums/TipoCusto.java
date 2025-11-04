package com.br.pdvpostocombustivel.enums;

public enum TipoCusto {
    CUSTOFIXO("Custo Fixo"),
    CUSTOVARIAVEL("Custo Variavel"),
    CUSTOFRETE("Custo Frete"),
    IMPOSTO("Custo Imposto");

    private final String descricao;

    private TipoCusto(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
