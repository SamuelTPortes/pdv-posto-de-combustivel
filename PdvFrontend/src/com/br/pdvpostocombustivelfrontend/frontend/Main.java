package com.br.pdvpostocombustivelfrontend.frontend;

import com.br.pdvpostocombustivelfrontend.frontend.ui.LoginFrame;

import javax.swing.*;

/**
 * Classe principal da aplicação Frontend do PDV Posto de Combustível
 */
public class Main {

    public static void main(String[] args) {
        // Configurar look and feel do sistema operacional
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Iniciar a aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}

