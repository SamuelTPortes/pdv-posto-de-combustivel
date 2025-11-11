        if (req.margemLucro() != null) p.setMargemLucro(req.margemLucro());
        if (req.tipoCusto() != null) p.setTipoCusto(req.tipoCusto());

        return toResponse(repository.save(p));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Custo não encontrado. id=" + id);
        }
        repository.deleteById(id);
    }

    private void validarUnicidadeMargem(Double margem, Long idAtual) {
        if (margem == null) return;
        repository.findByMargemLucro(margem).ifPresent(existente -> {
            if (idAtual == null || !existente.getId().equals(idAtual)) {
                throw new DataIntegrityViolationException("Margem já cadastrada: " + margem);
            }
        });
    }

    private Custo toEntity(com.br.pdvpostocombustivel.api.custo.dto.CustoRequest req) {
        return new Custo(
                req.imposto(),
                req.frete(),
                req.seguro(),
                req.custoVariavel(),
                req.custoFixo(),
                req.margemLucro(),
                req.tipoCusto()
        );
    }

    private CustoResponse toResponse(Custo p) {
        return new CustoResponse(
                p.getImposto(),
                p.getFrete(),
                p.getSeguro(),
                p.getCustoVariavel(),
                p.getCustoFixo(),
                p.getMargemLucro()
        );
    }
}
package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import com.br.pdvpostocombustivel.domain.entity.Custo;
import com.br.pdvpostocombustivel.domain.repository.CustoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustoService {

    private final CustoRepository repository;

    public CustoService(CustoRepository repository) {
        this.repository = repository;
    }

    public CustoResponse create(CustoRequest req) {
        validarUnicidadeMargem(req.margemLucro(), null);
        Custo nova = toEntity(req);
        return toResponse(repository.save(nova));
    }

    @Transactional(readOnly = true)
    public CustoResponse getById(Long id) {
        Custo p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Custo não encontrado. id=" + id));
        return toResponse(p);
    }

    @Transactional(readOnly = true)
    public Page<CustoResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    public CustoResponse update(Long id, CustoRequest req) {
        Custo p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Custo não encontrado. id=" + id));

        if (req.margemLucro() != null && req.margemLucro() != p.getMargemLucro()) {
            validarUnicidadeMargem(req.margemLucro(), id);
        }

        if (req.imposto() != null) p.setImposto(req.imposto());
        if (req.frete() != null) p.setFrete(req.frete());
        if (req.seguro() != null) p.setSeguro(req.seguro());
        if (req.custoVariavel() != null) p.setCustoVariavel(req.custoVariavel());
        if (req.custoFixo() != null) p.setCustoFixo(req.custoFixo());
        if (req.margemLucro() != null) p.setMargemLucro(req.margemLucro());
        if (req.tipoCusto() != null) p.setTipoCusto(req.tipoCusto());

        return toResponse(repository.save(p));
    }

    public CustoResponse patch(Long id, CustoRequest req) {
        Custo p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Custo não encontrado. id=" + id));

        if (req.imposto() != null) p.setImposto(req.imposto());
        if (req.frete() != null) p.setFrete(req.frete());
        if (req.seguro() != null) p.setSeguro(req.seguro());
        if (req.custoVariavel() != null) p.setCustoVariavel(req.custoVariavel());
        if (req.custoFixo() != null) p.setCustoFixo(req.custoFixo());

