package com.br.pdvpostocombustivelbackend.api.venda;

import com.br.pdvpostocombustivelbackend.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivelbackend.api.venda.dto.VendaResponse;
import com.br.pdvpostocombustivelbackend.domain.entity.Venda;
import com.br.pdvpostocombustivelbackend.domain.entity.Estoque;
import com.br.pdvpostocombustivelbackend.domain.repository.VendaRepository;
import com.br.pdvpostocombustivelbackend.domain.repository.EstoqueRepository;
import com.br.pdvpostocombustivelbackend.enums.TipoCombustivel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class VendaService {

    private final VendaRepository repository;
    private final EstoqueRepository estoqueRepository;

    public VendaService(VendaRepository repository, EstoqueRepository estoqueRepository) {
        this.repository = repository;
        this.estoqueRepository = estoqueRepository;
    }

    // CREATE - Atualiza estoque automaticamente
    public VendaResponse create(VendaRequest req) {
        BigDecimal valorTotal = req.quantidade().multiply(req.valorUnitario());

        Venda nova = new Venda(
                req.descricaoProduto(),
                req.quantidade(),
                req.valorUnitario(),
                valorTotal,
                req.dataVenda(),
                req.horaVenda(),
                req.tipoPreco(),
                req.tipoCombustivel(),
                req.observacoes()
        );

        // Buscar estoque correspondente por tipo
        List<Estoque> estoques = estoqueRepository.findByTipoEstoque(TipoCombustivel.valueOf(req.tipoCombustivel().toString()));
        if (!estoques.isEmpty()) {
            Estoque estoque = estoques.get(0);
            nova.setEstoque(estoque);

            // Diminuir quantidade do estoque
            BigDecimal novaQuantidade = estoque.getQuantidade().subtract(req.quantidade());
            if (novaQuantidade.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Quantidade insuficiente em estoque");
            }
            estoque.setQuantidade(novaQuantidade);
            estoqueRepository.save(estoque);
        }

        return toResponse(repository.save(nova));
    }

    @Transactional(readOnly = true)
    public VendaResponse getById(Long id) {
        Venda v = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada. id=" + id));
        return toResponse(v);
    }

    @Transactional(readOnly = true)
    public Page<VendaResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> findByDataVenda(LocalDate dataVenda) {
        return repository.findByDataVenda(dataVenda)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<VendaResponse> findVendasEntreDatas(LocalDate dataInicio, LocalDate dataFim) {
        return repository.findVendasEntreDatas(dataInicio, dataFim)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public VendaResponse update(Long id, VendaRequest req) {
        Venda v = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venda não encontrada. id=" + id));

        v.setDescricaoProduto(req.descricaoProduto());
        v.setQuantidade(req.quantidade());
        v.setValorUnitario(req.valorUnitario());

        BigDecimal valorTotal = req.quantidade().multiply(req.valorUnitario());
        v.setValorTotal(valorTotal);

        v.setDataVenda(req.dataVenda());
        v.setHoraVenda(req.horaVenda());
        v.setTipoPreco(req.tipoPreco());
        v.setTipoCombustivel(req.tipoCombustivel());
        v.setObservacoes(req.observacoes());

        return toResponse(repository.save(v));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Venda não encontrada. id=" + id);
        }
        repository.deleteById(id);
    }

    private VendaResponse toResponse(Venda v) {
        return new VendaResponse(
                v.getId(),
                v.getDescricaoProduto(),
                v.getQuantidade(),
                v.getValorUnitario(),
                v.getValorTotal(),
                v.getDataVenda(),
                v.getHoraVenda(),
                v.getTipoPreco(),
                v.getTipoCombustivel(),
                v.getObservacoes()
        );
    }
}

