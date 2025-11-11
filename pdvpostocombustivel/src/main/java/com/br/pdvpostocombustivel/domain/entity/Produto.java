package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoProduto;
import jakarta.persistence.*;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String nome;

    @Column(length = 30, nullable = false)
    private String referencia;

    @Column(length = 30, nullable = false)
    private String fornecedor;

    @Column(length = 30, nullable = false)
    private String marca;

    @Column(length = 30, nullable = false)
    private TipoProduto tipoProduto;

    protected Produto() {}

    public Produto(String nome, String referencia, String fornecedor, String marca, TipoProduto tipoProduto) {
        this.nome = nome;
        this.referencia = referencia;
        this.fornecedor = fornecedor;
        this.marca = marca;
        this.tipoProduto = tipoProduto;
    }



    public String getNome() {
        return nome;
    }

    public String getReferencia() {
        return referencia;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public String getMarca() {
        return marca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }


}
