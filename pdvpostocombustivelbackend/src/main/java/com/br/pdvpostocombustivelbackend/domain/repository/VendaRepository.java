package com.br.pdvpostocombustivelbackend.domain.repository;

import com.br.pdvpostocombustivelbackend.domain.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    // Buscar vendas por data
    List<Venda> findByDataVenda(LocalDate dataVenda);

    // Buscar vendas entre datas
    @Query("SELECT v FROM Venda v WHERE v.dataVenda BETWEEN :dataInicio AND :dataFim")
    List<Venda> findVendasEntreDatas(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    // Buscar vendas por tipo de preço
    List<Venda> findByTipoPreco(com.br.pdvpostocombustivelbackend.enums.TipoPreco tipoPreco);

    // Buscar vendas por descrição do produto
    List<Venda> findByDescricaoProdutoContainingIgnoreCase(String descricaoProduto);

    // Calcular total de vendas por data
    @Query("SELECT SUM(v.valorTotal) FROM Venda v WHERE v.dataVenda = :dataVenda")
    Optional<BigDecimal> calcularTotalVendasPorData(@Param("dataVenda") LocalDate dataVenda);
}

