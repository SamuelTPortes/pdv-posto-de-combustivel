package com.br.pdvpostocombustivel.api.estoque;

import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest;
import com.br.pdvpostocombustivel.api.estoque.dto.EstoqueResponse;
import com.br.pdvpostocombustivel.domain.entity.Estoque;
import com.br.pdvpostocombustivel.domain.repository.EstoqueRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EstoqueService {

    private final EstoqueRepository repository;

    public EstoqueService(EstoqueRepository repository) {
        this.repository = repository;
    }

    public EstoqueResponse create(EstoqueRequest req) {
        validarUnicidadeLocal(req.localTanque(), null);
        Estoque nova = toEntity(req);
        return toResponse(repository.save(nova));
    }

    @Transactional(readOnly = true)
    public EstoqueResponse getById(Long id) {
        Estoque p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public Page<EstoqueResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    public EstoqueResponse update(Long id, EstoqueRequest req) {
        Estoque p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));

        if (req.localTanque() != null && !req.localTanque().equals(p.getLocalTanque())) {
            validarUnicidadeLocal(req.localTanque(), id);
        }
        if (req.localEndereco() != null && !req.localEndereco().equals(p.getLocalEndereco())) {
            validarUnicidadeLocal(req.localEndereco(), id);
        }

        if (req.quantidade() != null) p.setQuantidade(req.quantidade());
        if (req.localTanque() != null) p.setLocalTanque(req.localTanque());
        if (req.localEndereco() != null) p.setLocalEndereco(req.localEndereco());
        if (req.localFabricacao() != null) p.setLocalFabricacao(req.localFabricacao());
        if (req.dataValidade() != null) p.setDataValidade(req.dataValidade());
        if (req.tipoEstoque() != null) p.setTipoEstoque(req.tipoEstoque());

        return toResponse(repository.save(p));
    }

    public EstoqueResponse patch(Long id, EstoqueRequest req) {
        Estoque p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estoque não encontrado. id=" + id));

        if (req.quantidade() != null) p.setQuantidade(req.quantidade());
        if (req.localTanque() != null) {
            if (!req.localTanque().equals(p.getLocalTanque())) validarUnicidadeLocal(req.localTanque(), id);
            p.setLocalTanque(req.localTanque());
        }
        if (req.localEndereco() != null) {
            if (!req.localEndereco().equals(p.getLocalEndereco())) validarUnicidadeLocal(req.localEndereco(), id);
            p.setLocalEndereco(req.localEndereco());
        }
        if (req.localFabricacao() != null) p.setLocalFabricacao(req.localFabricacao());
        if (req.dataValidade() != null) p.setDataValidade(req.dataValidade());
        if (req.tipoEstoque() != null) p.setTipoEstoque(req.tipoEstoque());

        return toResponse(repository.save(p));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Estoque não encontrado. id=" + id);
        }
        repository.deleteById(id);
    }

    private void validarUnicidadeLocal(String valor, Long idAtual) {
        if (valor == null) return;
        repository.findByLocalTanque(valor).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("Local já cadastrado: " + valor);
            }
        });
        repository.findByLocalEndereco(valor).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("Local endereço já cadastrado: " + valor);
            }
        });
    }

    private Estoque toEntity(com.br.pdvpostocombustivel.api.estoque.dto.EstoqueRequest req) {
        return new Estoque(
                req.quantidade(),
                req.localTanque(),
                req.localEndereco(),
                req.localFabricacao(),
                req.dataValidade(),
                req.tipoEstoque()
        );
    }

    private EstoqueResponse toResponse(Estoque p) {
        return new EstoqueResponse(
                p.getQuantidade(),
                p.getLocalTanque(),
                p.getLocalEndereco(),
                p.getLocalFabricacao(),
                p.getDataValidade()
        );
    }
}

