package com.br.pdvpostocombustivelbackend.domain.repository;

import com.br.pdvpostocombustivelbackend.domain.entity.Contato;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    Optional<Contato> findByEmail(String email);

    Optional<Contato> findByTelefone(String telefone);

    boolean existsByEmail(String email);
    boolean existsByTelefone(String telefone);
}
