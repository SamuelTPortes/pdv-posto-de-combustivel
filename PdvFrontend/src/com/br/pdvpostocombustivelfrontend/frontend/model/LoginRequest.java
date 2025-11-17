package com.br.pdvpostocombustivelfrontend.frontend.model;

/**
 * Requisição de login
 */
public class LoginRequest {
    private String usuario;
    private String senha;

    public LoginRequest() {}

    public LoginRequest(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

