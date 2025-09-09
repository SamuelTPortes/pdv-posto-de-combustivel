package br.com.domain.entity;

public class Acesso {

    // atributos

    private String usuario;
    private String senha;


    // construtor

    public Acesso(String usuario, String senha){
        this.usuario = usuario;
        this.senha = senha;
    }


    // getters

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }


    // setters

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


}
