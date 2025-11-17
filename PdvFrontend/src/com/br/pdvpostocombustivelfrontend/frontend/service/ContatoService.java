package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Contato;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;

public class ContatoService {

    public static String create(Contato contato) throws Exception {
        String json = toJson(contato);
        return HttpClient.post(AppConfig.API_BASE_URL + "/contatos", json);
    }

    public static String getById(Long id) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/contatos/" + id);
    }

    public static String list(int page, int size) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/contatos?page=" + page + "&size=" + size);
    }

    public static String update(Long id, Contato contato) throws Exception {
        String json = toJson(contato);
        return HttpClient.put(AppConfig.API_BASE_URL + "/contatos/" + id, json);
    }

    public static String delete(Long id) throws Exception {
        return HttpClient.delete(AppConfig.API_BASE_URL + "/contatos/" + id);
    }

    private static String toJson(Contato c) {
        StringBuilder json = new StringBuilder("{");
        if (c.getTelefone() != null) json.append("\"telefone\":\"").append(c.getTelefone()).append("\",");
        if (c.getEmail() != null) json.append("\"email\":\"").append(c.getEmail()).append("\",");
        if (c.getEndereco() != null) json.append("\"endereco\":\"").append(c.getEndereco()).append("\",");
        if (c.getTipoContato() != null) json.append("\"tipoContato\":\"").append(c.getTipoContato()).append("\",");
        if (json.length() > 1) json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }
}

