package com.br.pdvpostocombustivelbackend.api.acesso;

import com.br.pdvpostocombustivelbackend.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivelbackend.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivelbackend.api.acesso.dto.LoginRequest;
import com.br.pdvpostocombustivelbackend.api.acesso.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/acessos")
@Tag(name = "Acessos", description = "Endpoints para gerenciamento de acessos")
public class AcessoController {

    // Controller para endpoints de Acesso

    private final AcessoService service;

    public AcessoController(AcessoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Criar novo acesso", description = "Cria um novo registro de acesso no sistema")
    public AcessoResponse create(@RequestBody AcessoRequest req) {
        return service.create(req);
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar acesso por usuário", description = "Retorna um acesso pelo nome de usuário")
    public AcessoResponse findByUsuario(@RequestParam String usuario) {
        return service.getByCpfCnpj(usuario);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar acesso por ID", description = "Retorna um acesso específico pelo ID")
    public AcessoResponse get(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    @Operation(summary = "Listar acessos", description = "Retorna uma lista paginada de acessos")
    public Page<AcessoResponse> list(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "id") String sortBy,
                                     @RequestParam(defaultValue = "ASC") Sort.Direction dir) {
        return service.list(page, size, sortBy, dir);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar acesso", description = "Atualiza todos os campos de um acesso")
    public AcessoResponse update(@PathVariable Long id, @RequestBody AcessoRequest req) {
        return service.update(id, req);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar parcialmente acesso", description = "Atualiza apenas os campos fornecidos de um acesso")
    public AcessoResponse patch(@PathVariable Long id, @RequestBody AcessoRequest req) {
        return service.patch(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Excluir acesso", description = "Exclui um acesso do sistema")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica um usuário no sistema")
    public LoginResponse login(@RequestBody LoginRequest req) {
        return service.login(req);
    }
}
