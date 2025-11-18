package com.br.pdvpostocombustivelbackend.domain.entity;

import com.br.pdvpostocombustivelbackend.enums.TipoCombustivel;
import com.br.pdvpostocombustivelbackend.enums.TipoPreco;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vendas")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricaoProduto;

    @Column(nullable = false)
    private BigDecimal quantidade;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Column(nullable = false)
    private LocalDate dataVenda;

    @Column(nullable = false)
    private LocalTime horaVenda;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private TipoPreco tipoPreco;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private TipoCombustivel tipoCombustivel;

    @Column(length = 500)
    private String observacoes;

    @ManyToOne
    @JoinColumn(name = "estoque_id")
    private Estoque estoque;

    protected Venda() {}

    public Venda(String descricaoProduto, BigDecimal quantidade, BigDecimal valorUnitario,
                 BigDecimal valorTotal, LocalDate dataVenda, LocalTime horaVenda,
                 TipoPreco tipoPreco, TipoCombustivel tipoCombustivel, String observacoes) {
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
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

    public BigDecimal getQuantidade() { return quantidade; }
    public void setQuantidade(BigDecimal quantidade) { this.quantidade = quantidade; }

    public BigDecimal getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(BigDecimal valorUnitario) { this.valorUnitario = valorUnitario; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public LocalDate getDataVenda() { return dataVenda; }
    public void setDataVenda(LocalDate dataVenda) { this.dataVenda = dataVenda; }

    public LocalTime getHoraVenda() { return horaVenda; }
    public void setHoraVenda(LocalTime horaVenda) { this.horaVenda = horaVenda; }

    public TipoPreco getTipoPreco() { return tipoPreco; }
    public void setTipoPreco(TipoPreco tipoPreco) { this.tipoPreco = tipoPreco; }

    public TipoCombustivel getTipoCombustivel() { return tipoCombustivel; }
    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public Estoque getEstoque() { return estoque; }
    public void setEstoque(Estoque estoque) { this.estoque = estoque; }
}

