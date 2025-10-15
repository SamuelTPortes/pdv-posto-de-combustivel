package com.br.pdvpostocombustivel.domain.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Acesso {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private String senha;

    protected Acesso() {}

    public Acesso(String usuario, String senha){
        this.usuario = usuario;
        this.senha = senha;
    }


    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
