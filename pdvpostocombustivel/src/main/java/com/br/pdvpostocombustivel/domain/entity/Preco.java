package com.br.pdvpostocombustivel.domain.entity;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Preco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private BigDecimal valor;
    private Date dataAlteracao;
    private Date horaAlteracao;

    protected Preco(){}

    public Preco(BigDecimal valor, Date dataAlteracao, Date horaAlteracao) {
        this.valor = valor;
        this.dataAlteracao = dataAlteracao;
        this.horaAlteracao = horaAlteracao;
    }


    public BigDecimal getValor() {
        return valor;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public Date getHoraAlteracao() {
        return horaAlteracao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public void setHoraAlteracao(Date horaAlteracao) {
        this.horaAlteracao = horaAlteracao;
    }


}
