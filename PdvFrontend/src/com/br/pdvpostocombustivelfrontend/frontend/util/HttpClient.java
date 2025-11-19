package com.br.pdvpostocombustivelfrontend.frontend.util;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Cliente HTTP para comunicação com o backend
 */
public class HttpClient {

    /**
     * Realiza uma requisição POST
     * @param endpoint URL do endpoint
     * @param jsonBody Corpo da requisição em JSON
     * @return Resposta do servidor em JSON
     */
    public static String post(String endpoint, String jsonBody) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            // Configurar conexão
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(AppConfig.CONNECTION_TIMEOUT);
            connection.setReadTimeout(AppConfig.READ_TIMEOUT);

            // Enviar corpo da requisição
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Ler resposta
            int responseCode = connection.getResponseCode();

            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String body = response.toString();
            if (responseCode < 200 || responseCode >= 300) {
                throw new Exception("HTTP " + responseCode + ": " + body);
            }

            return body;

        } finally {
            connection.disconnect();
        }
    }

    /**
     * Realiza uma requisição GET
     * @param endpoint URL do endpoint
     * @return Resposta do servidor em JSON
     */
    public static String get(String endpoint) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(AppConfig.CONNECTION_TIMEOUT);
            connection.setReadTimeout(AppConfig.READ_TIMEOUT);

            int responseCode = connection.getResponseCode();

            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String body = response.toString();
            if (responseCode < 200 || responseCode >= 300) {
                throw new Exception("HTTP " + responseCode + ": " + body);
            }

            return body;

        } finally {
            connection.disconnect();
        }
    }

    /**
     * Realiza uma requisição PUT
     * @param endpoint URL do endpoint
     * @param jsonBody Corpo da requisição em JSON
     * @return Resposta do servidor em JSON
     */
    public static String put(String endpoint, String jsonBody) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(AppConfig.CONNECTION_TIMEOUT);
            connection.setReadTimeout(AppConfig.READ_TIMEOUT);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String body = response.toString();
            if (responseCode < 200 || responseCode >= 300) {
                throw new Exception("HTTP " + responseCode + ": " + body);
            }

            return body;

        } finally {
            connection.disconnect();
        }
    }

    /**
     * Realiza uma requisição PATCH
     * @param endpoint URL do endpoint
     * @param jsonBody Corpo da requisição em JSON
     * @return Resposta do servidor em JSON
     */
    public static String patch(String endpoint, String jsonBody) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("PATCH");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(AppConfig.CONNECTION_TIMEOUT);
            connection.setReadTimeout(AppConfig.READ_TIMEOUT);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String body = response.toString();
            if (responseCode < 200 || responseCode >= 300) {
                throw new Exception("HTTP " + responseCode + ": " + body);
            }

            return body;

        } finally {
            connection.disconnect();
        }
    }

    /**
     * Realiza uma requisição DELETE
     * @param endpoint URL do endpoint
     * @return Resposta do servidor em JSON
     */
    public static String delete(String endpoint) throws Exception {
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(AppConfig.CONNECTION_TIMEOUT);
            connection.setReadTimeout(AppConfig.READ_TIMEOUT);

            int responseCode = connection.getResponseCode();

            BufferedReader reader;
            if (responseCode >= 200 && responseCode < 300) {
                if (connection.getInputStream() != null) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
                } else {
                    return "{\"sucesso\": true}";
                }
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8));
            }

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String body = response.toString();
            if (responseCode < 200 || responseCode >= 300) {
                throw new Exception("HTTP " + responseCode + ": " + body);
            }

            return body;

        } finally {
            connection.disconnect();
        }
    }
}
