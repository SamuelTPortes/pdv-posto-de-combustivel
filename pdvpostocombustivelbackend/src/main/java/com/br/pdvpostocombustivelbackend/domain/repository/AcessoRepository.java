package com.br.pdvpostocombustivelbackend.domain.repository;

import com.br.pdvpostocombustivelbackend.domain.entity.Acesso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AcessoRepository extends JpaRepository <Acesso, Long> {

    Optional<Acesso> findByUsuario(String usuario);

    boolean existsByUsuario(String usuario);
}
