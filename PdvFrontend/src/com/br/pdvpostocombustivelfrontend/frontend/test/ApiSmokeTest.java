package com.br.pdvpostocombustivelfrontend.frontend.test;

import com.br.pdvpostocombustivelfrontend.frontend.service.PessoaService;

public class ApiSmokeTest {
    public static void main(String[] args) throws Exception {
        System.out.println("Teste list pessoas (raw):");
        String res = PessoaService.list(0, 10);
        System.out.println(res);
    }
}

