package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Venda;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;
import java.time.LocalDate;

public class VendaService {

    public static String create(Venda v) throws Exception {
        String json = toJson(v);
        return HttpClient.post(AppConfig.API_BASE_URL + "/vendas", json);
    }

    public static String list(int page, int size) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/vendas?page=" + page + "&size=" + size + "&dir=DESC");
    }

    public static String getById(Long id) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/vendas/" + id);
    }

    public static String update(Long id, Venda v) throws Exception {
        return HttpClient.put(AppConfig.API_BASE_URL + "/vendas/" + id, toJson(v));
    }

    public static String delete(Long id) throws Exception {
        return HttpClient.delete(AppConfig.API_BASE_URL + "/vendas/" + id);
    }

    public static String findByData(LocalDate data) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/vendas/data/" + data);
    }

    private static String toJson(Venda v) {
        StringBuilder json = new StringBuilder("{");
       if (v.getDescricaoProduto() != null && !v.getDescricaoProduto().isEmpty())
            json.append("\"descricaoProduto\":\"").append(escapeJson(v.getDescricaoProduto())).append("\",");
        if (v.getQuantidade() != null && !v.getQuantidade().isEmpty())
            json.append("\"quantidade\":").append(v.getQuantidade()).append(",");
        if (v.getValorUnitario() != null && !v.getValorUnitario().isEmpty())
            json.append("\"valorUnitario\":").append(v.getValorUnitario()).append(",");
        if (v.getDataVenda() != null && !v.getDataVenda().isEmpty())
            json.append("\"dataVenda\":\"").append(v.getDataVenda()).append("\",");
        if (v.getHoraVenda() != null && !v.getHoraVenda().isEmpty())
            json.append("\"horaVenda\":\"").append(v.getHoraVenda()).append("\",");
        if (v.getTipoPreco() != null && !v.getTipoPreco().isEmpty())
            json.append("\"tipoPreco\":\"").append(v.getTipoPreco()).append("\",");
        if (v.getTipoCombustivel() != null && !v.getTipoCombustivel().isEmpty())
            json.append("\"tipoCombustivel\":\"").append(v.getTipoCombustivel()).append("\",");
        if (v.getObservacoes() != null && !v.getObservacoes().isEmpty())
            json.append("\"observacoes\":\"").append(escapeJson(v.getObservacoes())).append("\",");

        if (json.length() > 1) json.deleteCharAt(json.length() - 1);
        json.append("}");
        return json.toString();
    }

    private static String escapeJson(String input) {
        if (input == null) return "";
        return input
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }
}

