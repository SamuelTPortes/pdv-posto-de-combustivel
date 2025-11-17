package com.br.pdvpostocombustivelfrontend.frontend.model;

public class Estoque {
    private Long id;
    private String quantidade;
    private String localTanque;
    private String localEndereco;
    private String localFabricacao;
    private String dataValidade;
    private String tipoEstoque;

    public Estoque() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuantidade() { return quantidade; }
    public void setQuantidade(String quantidade) { this.quantidade = quantidade; }

    public String getLocalTanque() { return localTanque; }
    public void setLocalTanque(String localTanque) { this.localTanque = localTanque; }

    public String getLocalEndereco() { return localEndereco; }
    public void setLocalEndereco(String localEndereco) { this.localEndereco = localEndereco; }

    public String getLocalFabricacao() { return localFabricacao; }
    public void setLocalFabricacao(String localFabricacao) { this.localFabricacao = localFabricacao; }

    public String getDataValidade() { return dataValidade; }
    public void setDataValidade(String dataValidade) { this.dataValidade = dataValidade; }

    public String getTipoEstoque() { return tipoEstoque; }
    public void setTipoEstoque(String tipoEstoque) { this.tipoEstoque = tipoEstoque; }
}

