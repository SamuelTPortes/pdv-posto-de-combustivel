package com.br.pdvpostocombustivel.util;


import com.br.pdvpostocombustivel.domain.entity.Pessoa;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdvpostocombustivelApplication {

    public static void main(String[] args) {
        //SpringApplication.run(PdvpostocombustivelApplication.class, args);
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setNomeCompleto("Kaio Jorge");
        pessoa1.setCpfCnpj("151.343.881-99");
        //pessoa1.setDataNascimento();
        pessoa1.setNumeroCtps((long)14581482);

        Pessoa pessoa2 = new Pessoa();
        pessoa2.setNomeCompleto("Matheus Pereira");
        pessoa2.setCpfCnpj("437.221.106-21");
        //pessoa2.setDataNascimento();
        pessoa2.setNumeroCtps(14999482L);

        Pessoa pessoa3 = new Pessoa();
        pessoa3.setNomeCompleto("Fabricio Bruno");
        pessoa3.setCpfCnpj("740.355.811-01");
        //pessoa1.setDataNascimento();
        pessoa3.setNumeroCtps(14581182L);

        //Instância Pessoa 1
        System.out.println("Nome Completo: " + pessoa1.getNomeCompleto());
        System.out.println("CPF/CNPJ: " + pessoa1.getCpfCnpj());
        System.out.println("Numero CTPs: "+ pessoa1.getNumeroCtps());

        System.out.println("-------------------------------------------------");

        //Instância Pessoa 2
        System.out.println("Nome Completo: " + pessoa2.getNomeCompleto());
        System.out.println("CPF/CNPJ: " + pessoa2.getCpfCnpj());
        System.out.println("Numero CTPs: "+ pessoa2.getNumeroCtps());

        System.out.println("-------------------------------------------------");

        //Instância Pessoa 3
        System.out.println("Nome Completo: " + pessoa3.getNomeCompleto());
        System.out.println("CPF/CNPJ: " + pessoa3.getCpfCnpj());
        System.out.println("Numero CTPs: "+ pessoa3.getNumeroCtps());
    }

}

