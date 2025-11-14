package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoEstoque;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "estoques")
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal quantidade;

    @Column(length = 10, nullable = false)
    private String localTanque;

    @Column(length = 10, nullable = false)
    private String localEndereco;

    @Column(length = 10, nullable = false)
    private String localFabricacao;

    private Date dataValidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private TipoEstoque tipoEstoque;

    protected Estoque() {}

    public Estoque(BigDecimal quantidade, String localTanque, String localEndereco, String localFabricacao, Date dataValidade, TipoEstoque tipoEstoque) {
        this.quantidade = quantidade;
        this.localTanque = localTanque;
        this.localEndereco = localEndereco;
        this.localFabricacao = localFabricacao;
        this.dataValidade = dataValidade;
        this.tipoEstoque = tipoEstoque;
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

    public TipoEstoque getTipoEstoque() {
        return tipoEstoque;
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

    public void setTipoEstoque(TipoEstoque tipoEstoque) {
        this.tipoEstoque = tipoEstoque;
    }

}
