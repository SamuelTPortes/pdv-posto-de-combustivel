package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;

/**
 * Serviço para operações CRUD de Pessoa
 */
public class PessoaService {

    /**
     * Criar uma nova pessoa
     */
    public static String create(Pessoa pessoa) throws Exception {
        String json = toJson(pessoa);
        return HttpClient.post(AppConfig.API_BASE_URL + "/pessoas", json);
    }

    /**
     * Buscar pessoa por ID
     */
    public static String getById(Long id) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/pessoas/" + id);
    }

    /**
     * Listar pessoas com paginação
     */
    public static String list(int page, int size) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/pessoas?page=" + page + "&size=" + size);
    }

    /**
     * Atualizar pessoa
     */
    public static String update(Long id, Pessoa pessoa) throws Exception {
        String json = toJson(pessoa);
        return HttpClient.put(AppConfig.API_BASE_URL + "/pessoas/" + id, json);
    }

    /**
     * Atualizar parcialmente pessoa
     */
    public static String patch(Long id, Pessoa pessoa) throws Exception {
        String json = toJson(pessoa);
        return HttpClient.patch(AppConfig.API_BASE_URL + "/pessoas/" + id, json);
    }

    /**
     * Excluir pessoa
     */
    public static String delete(Long id) throws Exception {
        return HttpClient.delete(AppConfig.API_BASE_URL + "/pessoas/" + id);
    }

    /**
     * Converte Pessoa para JSON
     */
    private static String toJson(Pessoa pessoa) {
        StringBuilder json = new StringBuilder("{");

        if (pessoa.getNomeCompleto() != null) {
            json.append("\"nomeCompleto\":\"").append(pessoa.getNomeCompleto()).append("\",");
        }
        if (pessoa.getCpfCnpj() != null) {
            json.append("\"cpfCnpj\":\"").append(pessoa.getCpfCnpj()).append("\",");
        }
        if (pessoa.getNumeroCtps() != null) {
            json.append("\"numeroCtps\":").append(pessoa.getNumeroCtps()).append(",");
        }
        if (pessoa.getDataNascimento() != null) {
            json.append("\"dataNascimento\":\"").append(pessoa.getDataNascimento()).append("\",");
        }
        if (pessoa.getTipoPessoa() != null) {
            json.append("\"tipoPessoa\":\"").append(pessoa.getTipoPessoa()).append("\",");
        }

        // Remove última vírgula
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }
}
