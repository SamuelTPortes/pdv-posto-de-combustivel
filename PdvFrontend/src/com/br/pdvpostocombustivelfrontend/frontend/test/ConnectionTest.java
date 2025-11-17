package com.br.pdvpostocombustivelfrontend.frontend.test;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginRequest;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginResponse;
import com.br.pdvpostocombustivelfrontend.frontend.service.AuthService;

/**
 * Classe de teste para verificar a conexão com o backend
 * Execute esta classe antes de iniciar a interface gráfica
 */
public class ConnectionTest {

    public static void main(String[] args) {
        System.out.println("=====================================");
        System.out.println("  Teste de Conexão com o Backend");
        System.out.println("=====================================");
        System.out.println();

        System.out.println("URL do Backend: " + AppConfig.API_BASE_URL);
        System.out.println("Endpoint de Login: " + AppConfig.LOGIN_ENDPOINT);
        System.out.println();

        // Teste 1: Verificar se o backend está disponível
        System.out.println("[1/3] Testando conexão com o backend...");

        AuthService authService = new AuthService();

        try {
            // Tentativa de login com credenciais inválidas (apenas para testar a conexão)
            LoginResponse response = authService.login("teste", "teste");

            if (response != null) {
                System.out.println("✓ Backend está respondendo!");
                System.out.println("  Mensagem: " + response.getMensagem());
            } else {
                System.out.println("✗ Backend não respondeu!");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("✗ Erro ao conectar com o backend!");
            System.out.println("  Erro: " + e.getMessage());
            System.out.println();
            System.out.println("Verifique se:");
            System.out.println("  1. O backend está rodando na porta 8080");
            System.out.println("  2. O PostgreSQL está ativo");
            System.out.println("  3. O banco de dados 'pdv_posto' foi criado");
            System.exit(1);
        }

        System.out.println();

        // Teste 2: Tentar login com credenciais padrão
        System.out.println("[2/3] Testando login com usuário padrão...");

        try {
            LoginResponse response = authService.login("admin", "admin123");

            if (response.isSucesso()) {
                System.out.println("✓ Login bem-sucedido!");
                System.out.println("  Usuário: " + response.getUsuario());
                System.out.println("  Tipo de Acesso: " + response.getTipoAcesso());
            } else {
                System.out.println("✗ Login falhou!");
                System.out.println("  Mensagem: " + response.getMensagem());
                System.out.println();
                System.out.println("Execute o script data.sql ou crie um usuário manualmente:");
                System.out.println("  INSERT INTO acessos (usuario, senha, tipo_acesso)");
                System.out.println("  VALUES ('admin', 'admin123', 'ADMINISTRADOR');");
            }
        } catch (Exception e) {
            System.out.println("✗ Erro no teste de login!");
            System.out.println("  Erro: " + e.getMessage());
        }

        System.out.println();

        // Teste 3: Resumo
        System.out.println("[3/3] Resumo dos Testes");
        System.out.println("=====================================");
        System.out.println();
        System.out.println("✓ Todos os testes foram executados!");
        System.out.println();
        System.out.println("Você pode iniciar a aplicação com:");
        System.out.println("  java com.br.pdvpostocombustivel.frontend.Main");
        System.out.println();
        System.out.println("Ou execute o script:");
        System.out.println("  run.bat");
        System.out.println();
    }
}

