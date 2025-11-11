package com.br.pdvpostocombustivel.api.produto;

import com.br.pdvpostocombustivel.api.produto.dto.ProdutoRequest;
import com.br.pdvpostocombustivel.api.produto.dto.ProdutoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponse create(@RequestBody ProdutoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public ProdutoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<ProdutoResponse> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy,
                                      @RequestParam(defaultValue = "ASC") Sort.Direction dir) {
        return service.list(page, size, sortBy, dir);
    }

    @PutMapping("/{id}")
    public ProdutoResponse update(@PathVariable Long id, @RequestBody ProdutoRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}")
    public ProdutoResponse patch(@PathVariable Long id, @RequestBody ProdutoRequest req) {
        return service.patch(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

