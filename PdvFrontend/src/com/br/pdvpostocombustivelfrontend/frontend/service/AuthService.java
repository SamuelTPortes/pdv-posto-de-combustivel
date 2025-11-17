package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginRequest;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginResponse;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;
import com.br.pdvpostocombustivelfrontend.frontend.util.JsonParser;

/**
 * Serviço de autenticação
 */
public class AuthService {

    /**
     * Realiza o login no sistema
     * @param usuario Nome de usuário
     * @param senha Senha do usuário
     * @return LoginResponse com o resultado do login
     */
    public LoginResponse login(String usuario, String senha) {
        try {
            // Validar entrada
            if (usuario == null || usuario.trim().isEmpty()) {
                return new LoginResponse(false, "Usuário não pode estar vazio", null, null);
            }

            if (senha == null || senha.trim().isEmpty()) {
                return new LoginResponse(false, "Senha não pode estar vazia", null, null);
            }

            // Criar requisição
            LoginRequest request = new LoginRequest(usuario, senha);
            String jsonRequest = JsonParser.toJson(request);

            // Fazer requisição HTTP
            String jsonResponse = HttpClient.post(AppConfig.LOGIN_ENDPOINT, jsonRequest);

            // Parsear resposta
            return JsonParser.parseLoginResponse(jsonResponse);

        } catch (Exception e) {
            return new LoginResponse(false,
                    "Erro ao conectar com o servidor: " + e.getMessage(),
                    null, null);
        }
    }
}

