package com.br.pdvpostocombustivel.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal quantidade;
    private String localTanque;
    private String localEndereco;
    private String localFabricacao;
    private Date dataValidade;

    protected Estoque() {}

    public Estoque(BigDecimal quantidade, String localTanque, String localEndereco, String localFabricacao, Date dataValidade) {
        this.quantidade = quantidade;
        this.localTanque = localTanque;
        this.localEndereco = localEndereco;
        this.localFabricacao = localFabricacao;
        this.dataValidade = dataValidade;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public String getLocalTanque() {
        return localTanque;
    }

    public String getLocalEndereco() {
        return localEndereco;
    }

    public String getLocalFabricacao() {
        return localFabricacao;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public void setLocalTanque(String localTanque) {
        this.localTanque = localTanque;
    }

    public void setLocalEndereco(String localEndereco) {
        this.localEndereco = localEndereco;
    }

    public void setLocalFabricacao(String localFabricacao) {
        this.localFabricacao = localFabricacao;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }


}
