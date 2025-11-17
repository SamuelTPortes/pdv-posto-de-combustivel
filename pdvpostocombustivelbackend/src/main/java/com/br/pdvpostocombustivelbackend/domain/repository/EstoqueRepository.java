package com.br.pdvpostocombustivelbackend.domain.repository;

import com.br.pdvpostocombustivelbackend.domain.entity.Estoque;
import com.br.pdvpostocombustivelbackend.enums.TipoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByLocalTanque(String localTanque);

    Optional<Estoque> findByLocalEndereco(String localEndereco);

    Optional<Estoque> findByLocalFabricacao(String localFabricacao);

    boolean existsByLocalTanque(String localTanque);

    boolean existsByLocalEndereco(String localEndereco);
}
