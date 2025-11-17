package com.br.pdvpostocombustivelfrontend.frontend.test;

import com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa;
import com.br.pdvpostocombustivelfrontend.frontend.model.Produto;
import com.br.pdvpostocombustivelfrontend.frontend.service.PessoaService;
import com.br.pdvpostocombustivelfrontend.frontend.service.CrudService;

public class FullCrudTest {
    public static void main(String[] args) throws Exception {
        System.out.println("=== TESTE CRUD PESSOA ===");
        Pessoa p = new Pessoa();
        p.setNomeCompleto("Teste API");
        p.setCpfCnpj("000.000.000-99");
        p.setNumeroCtps(123456789L);
        p.setDataNascimento("1990-01-01");
        p.setTipoPessoa("FISICA");

        System.out.println("Criando pessoa...");
        String created = PessoaService.create(p);
        System.out.println("CREATE RESPONSE: " + created);

        System.out.println("Listando pessoas (raw)...");
        String list = PessoaService.list(0, 50);
        System.out.println(list);

        // Tentar criar produto
        System.out.println("=== TESTE CRUD PRODUTO ===");
        Produto prod = new Produto();
        prod.setNome("Produto Teste");
        prod.setReferencia("REF-TESTE");
        prod.setFornecedor("Fornecedor X");
        prod.setMarca("Marca Y");
        prod.setTipoProduto("LUBRIFICANTE");

        System.out.println("Criando produto...");
        String prodCreated = CrudService.createProduto(prod);
        System.out.println("CREATE PROD RESPONSE: " + prodCreated);

        System.out.println("Listando produtos (raw)...");
        String prodList = CrudService.listProdutos(0, 50);
        System.out.println(prodList);

        System.out.println("Teste finalizado.");
    }
}
