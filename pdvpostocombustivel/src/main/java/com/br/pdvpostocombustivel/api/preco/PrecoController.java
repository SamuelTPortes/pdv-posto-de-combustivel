package com.br.pdvpostocombustivel.api.preco;

import com.br.pdvpostocombustivel.api.preco.dto.PrecoRequest;
import com.br.pdvpostocombustivel.api.preco.dto.PrecoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/precos")
public class PrecoController {

    private final PrecoService service;

    public PrecoController(PrecoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PrecoResponse create(@RequestBody PrecoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public PrecoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<PrecoResponse> list(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size,
                                    @RequestParam(defaultValue = "id") String sortBy,
                                    @RequestParam(defaultValue = "ASC") Sort.Direction dir) {
        return service.list(page, size, sortBy, dir);
    }

    @PutMapping("/{id}")
    public PrecoResponse update(@PathVariable Long id, @RequestBody PrecoRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}")
    public PrecoResponse patch(@PathVariable Long id, @RequestBody PrecoRequest req) {
        return service.patch(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

