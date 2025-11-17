package com.br.pdvpostocombustivelbackend.domain.repository;

import com.br.pdvpostocombustivelbackend.domain.entity.Preco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;
import java.math.BigDecimal;

public interface PrecoRepositoy extends JpaRepository<Preco, Long> {
    Optional<Preco> findByValor(BigDecimal valor);

    Optional<Preco> findByDataAlteracao(Date dataAlteracao);

    Optional<Preco> findByHoraAlteracao(Date horaAlteracao);

    boolean existsByValor(BigDecimal valor);
    boolean existsByDataAlteracao(Date dataAlteracao);
    boolean existsByHoraAlteracao(Date horaAlteracao);
}
