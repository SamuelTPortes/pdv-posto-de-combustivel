    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
    @PutMapping("/{id}")
    public ContatoResponse update(@PathVariable Long id, @RequestBody ContatoRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}")
    public ContatoResponse patch(@PathVariable Long id, @RequestBody ContatoRequest req) {
        return service.patch(id, req);
    }

package com.br.pdvpostocombustivel.api.contato;

import com.br.pdvpostocombustivel.api.contato.dto.ContatoRequest;
import com.br.pdvpostocombustivel.api.contato.dto.ContatoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contatos")
public class ContatoController {

    private final ContatoService service;

    public ContatoController(ContatoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContatoResponse create(@RequestBody ContatoRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public ContatoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public Page<ContatoResponse> list(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size,
                                      @RequestParam(defaultValue = "id") String sortBy,
                                      @RequestParam(defaultValue = "ASC") Sort.Direction dir) {
        return service.list(page, size, sortBy, dir);
    }


