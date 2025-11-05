package com.br.pdvpostocombustivel.domain.entity;

import com.br.pdvpostocombustivel.enums.TipoCusto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="acessos")

public class Custo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double imposto;
    private double frete;
    private double seguro;
    private double custoVariavel;
    private double custoFixo;
    private double margemLucro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private TipoCusto tipoCusto;

    protected Custo() {}

    public Custo(double imposto, double frete, double seguro, double custoVariavel, double custoFixo, double margemLucro, TipoCusto tipoCusto) {
        this.imposto = imposto;
        this.frete = frete;
        this.seguro = seguro;
        this.custoVariavel = custoVariavel;
        this.custoFixo = custoFixo;
        this.margemLucro = margemLucro;
        this.tipoCusto = tipoCusto;
    }

    public double getImposto() {
        return imposto;
    }

    public double getFrete() {
        return frete;
    }

    public double getSeguro() {
        return seguro;
    }

    public double getCustoVariavel() {
        return custoVariavel;
    }

    public double getCustoFixo() {
        return custoFixo;
    }

    public double getMargemLucro() {
        return margemLucro;
    }

    public Long getId() {
        return id;
    }

    public TipoCusto getTipoCusto() {
        return tipoCusto;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setImposto(double imposto) {
        this.imposto = imposto;
    }

    public void setFrete(double frete) {
        this.frete = frete;
    }

    public void setSeguro(double seguro) {
        this.seguro = seguro;
    }

    public void setCustoVariavel(double custoVariavel) {
        this.custoVariavel = custoVariavel;
    }

    public void setCustoFixo(double custoFixo) {
        this.custoFixo = custoFixo;
    }

    public void setMargemLucro(double margemLucro) {
        this.margemLucro = margemLucro;
    }

    public void setTipoCusto(TipoCusto tipoCusto) {
        this.tipoCusto = tipoCusto;
    }


}
