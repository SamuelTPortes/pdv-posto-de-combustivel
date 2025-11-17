package com.br.pdvpostocombustivelbackend.api.acesso;


import com.br.pdvpostocombustivelbackend.api.acesso.dto.AcessoRequest;
import com.br.pdvpostocombustivelbackend.api.acesso.dto.AcessoResponse;
import com.br.pdvpostocombustivelbackend.api.acesso.dto.LoginRequest;
import com.br.pdvpostocombustivelbackend.api.acesso.dto.LoginResponse;
import com.br.pdvpostocombustivelbackend.domain.entity.Acesso;
import com.br.pdvpostocombustivelbackend.domain.repository.AcessoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AcessoService {

    // implementa a interface de Acesso
    private final AcessoRepository repository;


    public AcessoService(AcessoRepository repository) {
        this.repository = repository;
    }

    // CREATE
    public AcessoResponse create(AcessoRequest req) {

        Acesso nova = toEntity(req);

        return toResponse(repository.save(nova));
    }

    // READ by ID - validar a utilização desse método
    @Transactional(readOnly = true)
    public AcessoResponse getById(Long id) {
        Acesso p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acesso não encontrada. id=" + id));
        return toResponse(p);
    }

    // READ by CPF/CNPJ
    @Transactional(readOnly = true)
    public AcessoResponse getByCpfCnpj(String usuario) {
        Acesso p = repository.findByUsuario(usuario)
                .orElseThrow(() -> new IllegalArgumentException("Acesso não encontrada. usuario=" + usuario));
        return toResponse(p);
    }

    // LIST paginado
    @Transactional(readOnly = true)
    public Page<AcessoResponse> list(int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return repository.findAll(pageable).map(this::toResponse);
    }

    // UPDATE - substitui todos os campos
    public AcessoResponse update(Long id, AcessoRequest req) {
        Acesso p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acesso não encontrada. id=" + id));

        p.setUsuario(req.usuario());
        p.setSenha(req.senha());
        p.setTipoAcesso(req.tipoAcesso());

        return toResponse(repository.save(p));
    }

    // PATCH - atualiza apenas campos não nulos
    public AcessoResponse patch(Long id, AcessoRequest req) {
        Acesso p = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Acesso não encontrada. id=" + id));

        if (req.usuario() != null)  p.setUsuario(req.usuario());
        if (req.senha() != null)    p.setSenha(req.senha());
        if (req.tipoAcesso() != null) p.setTipoAcesso(req.tipoAcesso());

        return toResponse(repository.save(p));
    }

    // DELETE
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Acesso não encontrada. id=" + id);
        }
        repository.deleteById(id);
    }

    // LOGIN
    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest req) {
        try {
            Acesso acesso = repository.findByUsuario(req.usuario())
                    .orElse(null);

            if (acesso == null) {
                return new LoginResponse(false, "Usuário não encontrado", null, null);
            }

            if (!acesso.getSenha().equals(req.senha())) {
                return new LoginResponse(false, "Senha incorreta", null, null);
            }

            return new LoginResponse(true, "Login realizado com sucesso",
                    acesso.getUsuario(), acesso.getTipoAcesso());
        } catch (Exception e) {
            return new LoginResponse(false, "Erro ao realizar login: " + e.getMessage(), null, null);
        }
    }


    private Acesso toEntity(AcessoRequest req) {
        return new Acesso(
                req.usuario(),
                req.senha(),
                req.tipoAcesso()
        );
    }

    private AcessoResponse toResponse(Acesso p) {
        return new AcessoResponse(
                p.getUsuario(),
                p.getSenha()
        );
    }
}
