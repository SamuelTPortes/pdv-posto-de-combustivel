package com.br.pdvpostocombustivel.domain.repository;

import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.enums.TipoPreco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface PrecoRepositoy extends JpaRepository<Preco, Long> {
    Optional<Preco> findByValor(double valor);

    Optional<Preco> findByDataAlteracao(Date dataAlteracao);

    Optional<Preco> findByHoraAlteracao(Date horaAlteracao);

    boolean existsByValor(double valor);
    boolean existsByDataAlteracao(Date dataAlteracao);
    boolean existsByHoraAlteracao(Date horaAlteracao);
}
