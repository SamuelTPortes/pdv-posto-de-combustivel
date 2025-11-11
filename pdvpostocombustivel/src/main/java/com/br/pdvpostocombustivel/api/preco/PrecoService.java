package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import com.br.pdvpostocombustivel.domain.entity.Preco;
import com.br.pdvpostocombustivel.domain.repository.PrecoRepositoy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PrecoService {

    private final PrecoRepositoy repository;

    public PrecoService(PrecoRepositoy repository) {
        this.repository = repository;
    }

    public PrecoResponse create(PrecoRequest req) {
        validarUnicidadeValor(req.valor(), null);
        Preco nova = toEntity(req);
        return toResponse(repository.save(nova));
    }

    @Transactional(readOnly = true)
    public PrecoResponse getById(Long id) {
        Preco p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Preco não encontrado. id=" + id));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public Page<PrecoResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    public PrecoResponse update(Long id, PrecoRequest req) {
        Preco p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Preco não encontrado. id=" + id));

        if (req.valor() != null && !req.valor().equals(p.getValor())) {
            validarUnicidadeValor(req.valor(), id);
        }

        if (req.valor() != null) p.setValor(req.valor());
        if (req.dataAlteracao() != null) p.setDataAlteracao(req.dataAlteracao());
        if (req.horaAlteracao() != null) p.setHoraAlteracao(req.horaAlteracao());
        if (req.tipoPreco() != null) p.setTipoPreco(req.tipoPreco());

        return toResponse(repository.save(p));
    }

    public PrecoResponse patch(Long id, PrecoRequest req) {
        Preco p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Preco não encontrado. id=" + id));

        if (req.valor() != null) p.setValor(req.valor());
        if (req.dataAlteracao() != null) p.setDataAlteracao(req.dataAlteracao());
        if (req.horaAlteracao() != null) p.setHoraAlteracao(req.horaAlteracao());
        if (req.tipoPreco() != null) p.setTipoPreco(req.tipoPreco());

        return toResponse(repository.save(p));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Preco não encontrado. id=" + id);
        }
        repository.deleteById(id);
    }

    private void validarUnicidadeValor(java.math.BigDecimal valor, Long idAtual) {
        if (valor == null) return;
        repository.findByValor(valor.doubleValue()).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("Valor já cadastrado: " + valor);
            }
        });
    }

    private Preco toEntity(com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest req) {
        return new Preco(
                req.valor(),
                req.dataAlteracao(),
                req.horaAlteracao(),
                req.tipoPreco()
        );
    }

    private PrecoResponse toResponse(Preco p) {
        return new PrecoResponse(
                p.getValor(),
                p.getDataAlteracao(),
                p.getHoraAlteracao()
        );
    }
}

