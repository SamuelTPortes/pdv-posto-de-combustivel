package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Estoque;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;

public class EstoqueService {
    public static String create(Estoque e) throws Exception { String json = toJson(e); System.out.println("DEBUG EstoqueService -> POST /estoques payload: " + json); String resp = HttpClient.post(AppConfig.API_BASE_URL + "/estoques", json); System.out.println("DEBUG EstoqueService -> response: " + resp); return resp; }
    public static String list(int page, int size) throws Exception { return HttpClient.get(AppConfig.API_BASE_URL + "/estoques?page=" + page + "&size=" + size); }
    public static String getById(Long id) throws Exception { return HttpClient.get(AppConfig.API_BASE_URL + "/estoques/" + id); }
    public static String update(Long id, Estoque e) throws Exception { String json = toJson(e); System.out.println("DEBUG EstoqueService -> PUT /estoques/"+id+" payload: " + json); String resp = HttpClient.put(AppConfig.API_BASE_URL + "/estoques/" + id, json); System.out.println("DEBUG EstoqueService -> response: " + resp); return resp; }
    public static String delete(Long id) throws Exception { return HttpClient.delete(AppConfig.API_BASE_URL + "/estoques/" + id); }

    private static String toJson(Estoque e) {
        StringBuilder json = new StringBuilder("{");
        if (e.getQuantidade() != null && !e.getQuantidade().trim().isEmpty()) {
            String q = e.getQuantidade().trim().replace(',', '.');
            try {
                new java.math.BigDecimal(q);
                json.append("\"quantidade\":").append(q).append(",");
            } catch (Exception ex) {
                // Se não for numérico, omitimos o campo para evitar erro de binding
                System.out.println("WARN EstoqueService.toJson: quantidade inválida, omitindo campo: '" + e.getQuantidade() + "'");
            }
        }
        if (e.getLocalTanque() != null) json.append("\"localTanque\":\"").append(e.getLocalTanque()).append("\",");
        if (e.getLocalEndereco() != null) json.append("\"localEndereco\":\"").append(e.getLocalEndereco()).append("\",");
        if (e.getLocalFabricacao() != null) json.append("\"localFabricacao\":\"").append(e.getLocalFabricacao()).append("\",");
        if (e.getDataValidade() != null) json.append("\"dataValidade\":\"").append(e.getDataValidade()).append("\",");
        if (e.getTipoEstoque() != null) json.append("\"tipoEstoque\":\"").append(e.getTipoEstoque()).append("\",");
        if (json.length() > 1) json.deleteCharAt(json.length()-1);
        json.append("}");
        return json.toString();
    }
}
