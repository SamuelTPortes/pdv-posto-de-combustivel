package com.br.pdvpostocombustivelfrontend.frontend.model;

/**
 * Modelo de dados para Pessoa
 */
public class Pessoa {
    private Long id;
    private String nomeCompleto;
    private String cpfCnpj;
    private Long numeroCtps;
    private String dataNascimento;
    private String tipoPessoa;

    public Pessoa() {}

    public Pessoa(String nomeCompleto, String cpfCnpj, Long numeroCtps, String dataNascimento) {
        this.nomeCompleto = nomeCompleto;
        this.cpfCnpj = cpfCnpj;
        this.numeroCtps = numeroCtps;
        this.dataNascimento = dataNascimento;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getCpfCnpj() { return cpfCnpj; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }

    public Long getNumeroCtps() { return numeroCtps; }
    public void setNumeroCtps(Long numeroCtps) { this.numeroCtps = numeroCtps; }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getTipoPessoa() { return tipoPessoa; }
    public void setTipoPessoa(String tipoPessoa) { this.tipoPessoa = tipoPessoa; }

    @Override
    public String toString() {
        return nomeCompleto + " (" + cpfCnpj + ")";
    }
}
