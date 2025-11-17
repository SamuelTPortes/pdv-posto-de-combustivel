package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Custo;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;

public class CustoService {
    public static String create(Custo c) throws Exception { String json = toJson(c); System.out.println("DEBUG CustoService -> POST /custos payload: " + json); String resp = HttpClient.post(AppConfig.API_BASE_URL + "/custos", json); System.out.println("DEBUG CustoService -> response: " + resp); return resp; }
    public static String list(int page, int size) throws Exception { return HttpClient.get(AppConfig.API_BASE_URL + "/custos?page=" + page + "&size=" + size); }
    public static String getById(Long id) throws Exception { return HttpClient.get(AppConfig.API_BASE_URL + "/custos/" + id); }
    public static String update(Long id, Custo c) throws Exception { String json = toJson(c); System.out.println("DEBUG CustoService -> PUT /custos/"+id+" payload: " + json); String resp = HttpClient.put(AppConfig.API_BASE_URL + "/custos/" + id, json); System.out.println("DEBUG CustoService -> response: " + resp); return resp; }
    public static String delete(Long id) throws Exception { return HttpClient.delete(AppConfig.API_BASE_URL + "/custos/" + id); }

    private static String toJson(Custo c) {
        StringBuilder json = new StringBuilder("{");
        if (c.getImposto() != null && !c.getImposto().trim().isEmpty()) {
            try { String v = c.getImposto().trim().replace(',', '.'); Double.parseDouble(v); json.append("\"imposto\":").append(v).append(","); } catch(Exception ex){ System.out.println("WARN CustoService.toJson: imposto inválido, omitindo: '"+c.getImposto()+"'"); }
        }
        if (c.getFrete() != null && !c.getFrete().trim().isEmpty()) {
            try { String v = c.getFrete().trim().replace(',', '.'); Double.parseDouble(v); json.append("\"frete\":").append(v).append(","); } catch(Exception ex){ System.out.println("WARN CustoService.toJson: frete inválido, omitindo: '"+c.getFrete()+"'"); }
        }
        if (c.getSeguro() != null && !c.getSeguro().trim().isEmpty()) {
            try { String v = c.getSeguro().trim().replace(',', '.'); Double.parseDouble(v); json.append("\"seguro\":").append(v).append(","); } catch(Exception ex){ System.out.println("WARN CustoService.toJson: seguro inválido, omitindo: '"+c.getSeguro()+"'"); }
        }
        if (c.getCustoVariavel() != null && !c.getCustoVariavel().trim().isEmpty()) {
            try { String v = c.getCustoVariavel().trim().replace(',', '.'); Double.parseDouble(v); json.append("\"custoVariavel\":").append(v).append(","); } catch(Exception ex){ System.out.println("WARN CustoService.toJson: custoVariavel inválido, omitindo: '"+c.getCustoVariavel()+"'"); }
        }
        if (c.getCustoFixo() != null && !c.getCustoFixo().trim().isEmpty()) {
            try { String v = c.getCustoFixo().trim().replace(',', '.'); Double.parseDouble(v); json.append("\"custoFixo\":").append(v).append(","); } catch(Exception ex){ System.out.println("WARN CustoService.toJson: custoFixo inválido, omitindo: '"+c.getCustoFixo()+"'"); }
        }
        if (c.getMargemLucro() != null && !c.getMargemLucro().trim().isEmpty()) {
            try { String v = c.getMargemLucro().trim().replace(',', '.'); Double.parseDouble(v); json.append("\"margemLucro\":").append(v).append(","); } catch(Exception ex){ System.out.println("WARN CustoService.toJson: margemLucro inválido, omitindo: '"+c.getMargemLucro()+"'"); }
        }
        if (c.getTipoCusto() != null) json.append("\"tipoCusto\":\"").append(c.getTipoCusto()).append("\",");
        if (json.length() > 1) json.deleteCharAt(json.length()-1);
        json.append("}");
        return json.toString();
    }
}
