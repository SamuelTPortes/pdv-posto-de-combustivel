package com.br.pdvpostocombustivelfrontend.frontend.model;

/**
 * Enum que representa os tipos de acesso no sistema
 */
public enum TipoAcesso {
    ADMINISTRADOR("Acesso Administrador"),
    GESTAO("Acesso Gestão"),
    FUNCIONARIO("Acesso Funcionário");

    private final String descricao;

    TipoAcesso(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

