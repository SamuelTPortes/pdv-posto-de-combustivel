package com.br.pdvpostocombustivelbackend.enums;

public enum TipoCombustivel {
    GASOLINA("Gasolina"),
    DIESEL("Diesel"),
    ALCOOL("Álcool"),
    OUTROS("Outros");

    private final String descricao;

    TipoCombustivel(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

