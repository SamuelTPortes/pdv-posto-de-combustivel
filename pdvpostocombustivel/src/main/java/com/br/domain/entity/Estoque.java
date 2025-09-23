package br.com.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Estoque {

    // atributos

    private BigDecimal quantidade;
    private String localTanque;
    private String localEndereco;
    private String localFabricacao;
    private Date dataValidade;


    // construtor

    public Estoque(BigDecimal quantidade, String localTanque, String localEndereco, String localFabricacao, Date dataValidade) {
        this.quantidade = quantidade;
        this.localTanque = localTanque;
        this.localEndereco = localEndereco;
        this.localFabricacao = localFabricacao;
        this.dataValidade = dataValidade;
    }


    // getters

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


    // setters

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
