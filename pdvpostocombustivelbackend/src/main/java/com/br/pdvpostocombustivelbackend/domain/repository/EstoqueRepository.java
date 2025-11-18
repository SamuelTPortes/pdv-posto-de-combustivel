package com.br.pdvpostocombustivelbackend.domain.repository;

import com.br.pdvpostocombustivelbackend.domain.entity.Estoque;
import com.br.pdvpostocombustivelbackend.enums.TipoEstoque;
import com.br.pdvpostocombustivelbackend.enums.TipoCombustivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByLocalTanque(String localTanque);

    Optional<Estoque> findByLocalEndereco(String localEndereco);

    Optional<Estoque> findByLocalFabricacao(String localFabricacao);

    boolean existsByLocalTanque(String localTanque);

    boolean existsByLocalEndereco(String localEndereco);

    // Buscar estoque por tipo de combustível (convertendo para tipo estoque)
    @Query("SELECT e FROM Estoque e WHERE CAST(e.tipoEstoque AS string) = CAST(:tipo AS string)")
    List<Estoque> findByTipoEstoque(@Param("tipo") TipoCombustivel tipo);
}
