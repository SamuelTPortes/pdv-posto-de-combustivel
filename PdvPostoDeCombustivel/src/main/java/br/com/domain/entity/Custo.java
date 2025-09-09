package br.com.domain.entity;

public class Custo {

    // atributos

    private double imposto;
    private double frete;
    private double seguro;
    private double custoVariavel;
    private double custoFixo;
    private double margemLucro;


    // construtor

    public Custo(double imposto, double frete, double seguro, double custoVariavel, double custoFixo, double margemLucro) {
        this.imposto = imposto;
        this.frete = frete;
        this.seguro = seguro;
        this.custoVariavel = custoVariavel;
        this.custoFixo = custoFixo;
        this.margemLucro = margemLucro;
    }


    // getters

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


    // setters

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


}
