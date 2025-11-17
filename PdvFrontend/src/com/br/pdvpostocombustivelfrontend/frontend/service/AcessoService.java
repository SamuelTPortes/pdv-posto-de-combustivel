package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Acesso;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;
import com.br.pdvpostocombustivelfrontend.frontend.util.JsonParser;

public class AcessoService {

    public static String create(Acesso a) throws Exception {
        String json = toJson(a);
        return HttpClient.post(AppConfig.API_BASE_URL + "/acessos", json);
    }

    public static String list(int page, int size) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/acessos?page=" + page + "&size=" + size);
    }

    public static String update(Long id, Acesso a) throws Exception {
        return HttpClient.put(AppConfig.API_BASE_URL + "/acessos/" + id, toJson(a));
    }

    public static String getById(Long id) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/acessos/" + id);
    }

    // new helper to find acesso by username when list doesn't include id
    public static String findByUsuario(String usuario) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/acessos/search?usuario=" + java.net.URLEncoder.encode(usuario, java.nio.charset.StandardCharsets.UTF_8));
    }

    public static String delete(Long id) throws Exception { return HttpClient.delete(AppConfig.API_BASE_URL + "/acessos/" + id); }

    private static String toJson(Acesso a) {
        StringBuilder json = new StringBuilder("{");
        if (a.getUsuario() != null) json.append("\"usuario\":\"").append(a.getUsuario()).append("\",");
        if (a.getSenha() != null) json.append("\"senha\":\"").append(a.getSenha()).append("\",");
        if (a.getTipoAcesso() != null) json.append("\"tipoAcesso\":\"").append(a.getTipoAcesso()).append("\",");
        if (json.length() > 1) json.deleteCharAt(json.length()-1);
        json.append("}");
        return json.toString();
    }
}
