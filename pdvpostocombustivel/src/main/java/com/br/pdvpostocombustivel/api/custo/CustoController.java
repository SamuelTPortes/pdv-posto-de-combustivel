package com.br.pdvpostocombustivel.api.custo;

import com.br.pdvpostocombustivel.api.custo.dto.CustoRequest;
import com.br.pdvpostocombustivel.api.custo.dto.CustoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/custos")
public class CustoController {

    private final CustoService service;

    public CustoController(CustoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustoResponse create(@RequestBody CustoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public CustoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<CustoResponse> list(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy,
                                    @RequestParam(defaultValue = "ASC") Sort.Direction dir) {
        return service.list(page, size, sortBy, dir);
    }

    @PutMapping("/{id}")
    public CustoResponse update(@PathVariable Long id, @RequestBody CustoRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}")
    public CustoResponse patch(@PathVariable Long id, @RequestBody CustoRequest req) {
        return service.patch(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

