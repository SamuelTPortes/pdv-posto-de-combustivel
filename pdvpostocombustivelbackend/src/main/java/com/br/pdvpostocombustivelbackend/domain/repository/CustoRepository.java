package com.br.pdvpostocombustivelbackend.domain.repository;


import com.br.pdvpostocombustivelbackend.domain.entity.Custo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustoRepository extends JpaRepository<Custo, Long> {
    Optional<Custo> findByMargemLucro(double margemLucro);

    boolean existsByMargemLucro(double margemLucro);
}
