package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import com.br.pdvpostocombustivel.domain.entity.Contato;
import com.br.pdvpostocombustivel.domain.repository.ContatoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ContatoService {

    private final ContatoRepository repository;

    public ContatoService(ContatoRepository repository) {
        this.repository = repository;
    }

    public ContatoResponse create(ContatoRequest req) {
        validarUnicidadeEmailTelefone(req.email(), null);
        Contato nova = toEntity(req);
        return toResponse(repository.save(nova));
    }

    @Transactional(readOnly = true)
    public ContatoResponse getById(Long id) {
        Contato p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado. id=" + id));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public Page<ContatoResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    public ContatoResponse update(Long id, ContatoRequest req) {
        Contato p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado. id=" + id));

        if (req.email() != null && !req.email().equals(p.getEmail())) {
            validarUnicidadeEmailTelefone(req.email(), id);
        }
        if (req.telefone() != null && !req.telefone().equals(p.getTelefone())) {
            validarUnicidadeEmailTelefone(req.telefone(), id);
        }

        p.setTelefone(req.telefone());
        p.setEmail(req.email());
        p.setEndereco(req.endereco());
        p.setTipoContato(req.tipoContato());

        return toResponse(repository.save(p));
    }

    public ContatoResponse patch(Long id, ContatoRequest req) {
        Contato p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contato não encontrado. id=" + id));

        if (req.telefone() != null) {
            if (!req.telefone().equals(p.getTelefone())) validarUnicidadeEmailTelefone(req.telefone(), id);
            p.setTelefone(req.telefone());
        }
        if (req.email() != null) {
            if (!req.email().equals(p.getEmail())) validarUnicidadeEmailTelefone(req.email(), id);
            p.setEmail(req.email());
        }
        if (req.endereco() != null) p.setEndereco(req.endereco());
        if (req.tipoContato() != null) p.setTipoContato(req.tipoContato());

        return toResponse(repository.save(p));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Contato não encontrado. id=" + id);
        }
        repository.deleteById(id);
    }

    private void validarUnicidadeEmailTelefone(String valor, Long idAtual) {
        // verifica email
        repository.findByEmail(valor).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("Email já cadastrado: " + valor);
            }
        });
        // verifica telefone
        repository.findByTelefone(valor).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("Telefone já cadastrado: " + valor);
            }
        });
    }

    private Contato toEntity(com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest req) {
        return new Contato(
                req.telefone(),
                req.email(),
                req.endereco(),
                req.tipoContato()
        );
    }

    private ContatoResponse toResponse(Contato p) {
        return new ContatoResponse(
                p.getTelefone(),
                p.getEmail(),
                p.getEndereco()
        );
    }
}

