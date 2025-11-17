package com.br.pdvpostocombustivelfrontend.frontend.model;

public class Contato {
    private Long id;
    private String telefone;
    private String email;
    private String endereco;
    private String tipoContato;

    public Contato() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTipoContato() { return tipoContato; }
    public void setTipoContato(String tipoContato) { this.tipoContato = tipoContato; }
}

