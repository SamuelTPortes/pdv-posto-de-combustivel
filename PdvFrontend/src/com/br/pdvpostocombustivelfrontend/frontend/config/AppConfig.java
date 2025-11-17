package com.br.pdvpostocombustivelfrontend.frontend.config;

/**
 * Configurações da aplicação Frontend
 */
public class AppConfig {

    // URL base do backend
    public static final String API_BASE_URL = "http://localhost:8080/api/v1";

    // Endpoints
    public static final String ACESSO_ENDPOINT = API_BASE_URL + "/acessos";
    public static final String LOGIN_ENDPOINT = ACESSO_ENDPOINT + "/login";

    // Timeout de conexão (em milissegundos)
    public static final int CONNECTION_TIMEOUT = 5000;
    public static final int READ_TIMEOUT = 5000;

    // Configurações da UI
    public static final String APP_TITLE = "PDV - Posto de Combustível";
    public static final String APP_VERSION = "1.0.0";
}

