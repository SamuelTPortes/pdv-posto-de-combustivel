package com.br.pdvpostocombustivelbackend.domain.repository;

import com.br.pdvpostocombustivelbackend.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Optional<Produto> findByReferencia(String referencia);

    Optional<Produto> findByNome(String nome);
    Optional<Produto> findByMarca(String marca);

    boolean existsByReferencia(String referencia);

    boolean existsByNome(String nome);

    boolean existsByMarca(String marca);
}
