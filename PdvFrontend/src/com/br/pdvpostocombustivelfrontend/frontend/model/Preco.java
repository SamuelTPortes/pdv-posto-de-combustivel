package com.br.pdvpostocombustivelfrontend.frontend.model;

public class Preco {
    private Long id;
    private String valor; // use String for simplicity; service will emit numeric value if appropriate
    private String dataAlteracao;
    private String horaAlteracao;
    private String tipoPreco;

    public Preco() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public String getDataAlteracao() { return dataAlteracao; }
    public void setDataAlteracao(String dataAlteracao) { this.dataAlteracao = dataAlteracao; }

    public String getHoraAlteracao() { return horaAlteracao; }
    public void setHoraAlteracao(String horaAlteracao) { this.horaAlteracao = horaAlteracao; }

    public String getTipoPreco() { return tipoPreco; }
    public void setTipoPreco(String tipoPreco) { this.tipoPreco = tipoPreco; }
}

