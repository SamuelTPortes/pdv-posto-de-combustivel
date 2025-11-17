package com.br.pdvpostocombustivelbackend.enums;

public enum TipoAcesso {

    ADMINISTRADOR("Acesso Administrador"),
    GESTAO("Acesso Gestão"),
    FUNCIONARIO("Acesso Funcionário");

    private final String descricao;

    private TipoAcesso(String descricao){
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
