package com.br.pdvpostocombustivelfrontend.frontend.model;

public class Venda {
    private Long id;
    private String descricaoProduto;
    private String quantidade;
    private String valorUnitario;
    private String valorTotal;
    private String dataVenda;
    private String horaVenda;
    private String tipoPreco;
    private String tipoCombustivel;
    private String observacoes;

    public Venda() {}

    public Venda(String descricaoProduto, String quantidade, String valorUnitario,
                 String dataVenda, String horaVenda, String tipoPreco, String tipoCombustivel, String observacoes) {
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.dataVenda = dataVenda;
        this.horaVenda = horaVenda;
        this.tipoPreco = tipoPreco;
        this.tipoCombustivel = tipoCombustivel;
        this.observacoes = observacoes;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescricaoProduto() { return descricaoProduto; }
    public void setDescricaoProduto(String descricaoProduto) { this.descricaoProduto = descricaoProduto; }

    public String getQuantidade() { return quantidade; }
    public void setQuantidade(String quantidade) { this.quantidade = quantidade; }

    public String getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(String valorUnitario) { this.valorUnitario = valorUnitario; }

    public String getValorTotal() { return valorTotal; }
    public void setValorTotal(String valorTotal) { this.valorTotal = valorTotal; }

    public String getDataVenda() { return dataVenda; }
    public void setDataVenda(String dataVenda) { this.dataVenda = dataVenda; }

    public String getHoraVenda() { return horaVenda; }
    public void setHoraVenda(String horaVenda) { this.horaVenda = horaVenda; }

    public String getTipoPreco() { return tipoPreco; }
    public void setTipoPreco(String tipoPreco) { this.tipoPreco = tipoPreco; }

    public String getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}

