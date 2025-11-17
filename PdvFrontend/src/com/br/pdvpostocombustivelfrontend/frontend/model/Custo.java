package com.br.pdvpostocombustivelfrontend.frontend.model;

public class Custo {
    private Long id;
    private String imposto;
    private String frete;
    private String seguro;
    private String custoVariavel;
    private String custoFixo;
    private String margemLucro;
    private String tipoCusto;

    public Custo() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getImposto() { return imposto; }
    public void setImposto(String imposto) { this.imposto = imposto; }

    public String getFrete() { return frete; }
    public void setFrete(String frete) { this.frete = frete; }

    public String getSeguro() { return seguro; }
    public void setSeguro(String seguro) { this.seguro = seguro; }

    public String getCustoVariavel() { return custoVariavel; }
    public void setCustoVariavel(String custoVariavel) { this.custoVariavel = custoVariavel; }

    public String getCustoFixo() { return custoFixo; }
    public void setCustoFixo(String custoFixo) { this.custoFixo = custoFixo; }

    public String getMargemLucro() { return margemLucro; }
    public void setMargemLucro(String margemLucro) { this.margemLucro = margemLucro; }

    public String getTipoCusto() { return tipoCusto; }
    public void setTipoCusto(String tipoCusto) { this.tipoCusto = tipoCusto; }
}

