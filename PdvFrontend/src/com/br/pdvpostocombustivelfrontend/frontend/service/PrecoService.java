package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Preco;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;

public class PrecoService {
    public static String create(Preco p) throws Exception {
        String json = toJson(p);
        System.out.println("DEBUG PrecoService -> POST /precos payload: " + json);
        String resp = HttpClient.post(AppConfig.API_BASE_URL + "/precos", json);
        System.out.println("DEBUG PrecoService -> response: " + resp);
        return resp;
    }
    public static String list(int page, int size) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/precos?page=" + page + "&size=" + size);
    }
    public static String getById(Long id) throws Exception { return HttpClient.get(AppConfig.API_BASE_URL + "/precos/" + id); }
    public static String update(Long id, Preco p) throws Exception { return HttpClient.put(AppConfig.API_BASE_URL + "/precos/" + id, toJson(p)); }
    public static String delete(Long id) throws Exception { return HttpClient.delete(AppConfig.API_BASE_URL + "/precos/" + id); }

    private static String toJson(Preco p) {
        StringBuilder json = new StringBuilder("{");
        if (p.getValor() != null && !p.getValor().trim().isEmpty()) {
            // try to send numeric value
            String v = p.getValor().trim();
            try {
                // normalize comma to dot
                v = v.replace(',', '.');
                new java.math.BigDecimal(v); // validate
                json.append("\"valor\":").append(v).append(",");
            } catch (Exception ex) {
                // fallback: send as string
                json.append("\"valor\":\"").append(p.getValor()).append("\",");
            }
        }

        if (p.getDataAlteracao() != null && !p.getDataAlteracao().trim().isEmpty()) {
            json.append("\"dataAlteracao\":\"").append(p.getDataAlteracao()).append("\",");
        }

        // Convert horaAlteracao to ISO datetime if possible so Jackson can parse into java.util.Date
        if (p.getHoraAlteracao() != null && !p.getHoraAlteracao().trim().isEmpty()) {
            String hora = p.getHoraAlteracao().trim();
            String data = p.getDataAlteracao();
            String isoDatetime = toIsoDatetime(data, hora);
            json.append("\"horaAlteracao\":\"").append(isoDatetime).append("\",");
        }

        if (p.getTipoPreco() != null) json.append("\"tipoPreco\":\"").append(p.getTipoPreco()).append("\",");
        if (json.length() > 1) json.deleteCharAt(json.length()-1);
        json.append("}");
        return json.toString();
    }

    private static String toIsoDatetime(String datePart, String timePart) {
        // datePart expected yyyy-MM-dd; timePart expected HH:mm or HH:mm:ss
        String date = (datePart != null && !datePart.trim().isEmpty()) ? datePart.trim() : "1970-01-01";
        String time = timePart.trim();
        // If time is like HH:mm, append :00
        if (time.matches("^\\d{1,2}:\\d{2}$")) {
            time = time + ":00";
        }
        // If time has only hours, like HH, append :00:00
        if (time.matches("^\\d{1,2}$")) {
            time = time + ":00:00";
        }
        // Ensure milliseconds .SSS present
        if (!time.contains(".")) {
            time = time + ".000";
        }
        // Return ISO-like datetime: yyyy-MM-ddTHH:mm:ss.SSS
        return date + "T" + time;
    }
}
