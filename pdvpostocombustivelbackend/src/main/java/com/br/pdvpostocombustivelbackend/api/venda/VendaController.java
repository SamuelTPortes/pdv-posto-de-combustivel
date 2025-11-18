package com.br.pdvpostocombustivelbackend.api.venda;

import com.br.pdvpostocombustivelbackend.api.venda.dto.VendaRequest;
import com.br.pdvpostocombustivelbackend.api.venda.dto.VendaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vendas")
@Tag(name = "Vendas", description = "Endpoints para gerenciamento de vendas")
public class VendaController {

    private final VendaService service;

    public VendaController(VendaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar nova venda", description = "Cria um novo registro de venda no sistema")
    public VendaResponse create(@RequestBody VendaRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar venda por ID", description = "Retorna uma venda específica pelo ID")
    public VendaResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    @Operation(summary = "Listar vendas", description = "Retorna uma lista paginada de vendas")
    public Page<VendaResponse> list(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy,
                                    @RequestParam(defaultValue = "DESC") Sort.Direction dir) {
        return service.list(page, size, sortBy, dir);
    }

    @GetMapping("/data/{dataVenda}")
    @Operation(summary = "Buscar vendas por data", description = "Retorna todas as vendas de um dia específico")
    public List<VendaResponse> findByData(@PathVariable LocalDate dataVenda) {
        return service.findByDataVenda(dataVenda);
    }

    @GetMapping("/intervalo")
    @Operation(summary = "Buscar vendas entre datas", description = "Retorna vendas dentro de um intervalo de datas")
    public List<VendaResponse> findEntreDatas(@RequestParam LocalDate dataInicio,
                                              @RequestParam LocalDate dataFim) {
        return service.findVendasEntreDatas(dataInicio, dataFim);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar venda", description = "Atualiza todos os campos de uma venda")
    public VendaResponse update(@PathVariable Long id, @RequestBody VendaRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir venda", description = "Exclui uma venda do sistema")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

