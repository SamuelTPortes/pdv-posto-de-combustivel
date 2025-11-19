package com.br.pdvpostocombustivelfrontend.frontend.ui;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginResponse;
import com.br.pdvpostocombustivelfrontend.frontend.model.TipoAcesso;
import com.br.pdvpostocombustivelfrontend.frontend.service.AuthService;
import com.br.pdvpostocombustivelfrontend.frontend.ui.MainFrame;
import com.br.pdvpostocombustivelfrontend.frontend.ui.FuncionarioFrame;
import com.br.pdvpostocombustivelfrontend.frontend.util.JsonParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Tela de Login do PDV
 */
public class LoginFrame extends JFrame {

    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton loginButton;
    private JButton cancelButton;
    private final AuthService authService;

    public LoginFrame() {
        this.authService = new AuthService();
        initComponents();
    }

    private void initComponents() {
        // Configurações da janela
        setTitle(AppConfig.APP_TITLE + " - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // Painel principal com gradiente
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(52, 152, 219);
                Color color2 = new Color(41, 128, 185);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setLayout(new GridBagLayout());

        // Painel de login (centralizado)
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
                BorderFactory.createEmptyBorder(30, 60, 30, 60)
        ));
        loginPanel.setMaximumSize(new Dimension(500, 400));

        // Logo/Título
        JLabel titleLabel = new JLabel("PDV - Posto de Combustível");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(52, 73, 94));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Sistema de Gerenciamento");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(127, 140, 141));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Painel para usuário (centralizado)
        JPanel usuarioPanel = new JPanel();
        usuarioPanel.setBackground(Color.WHITE);
        usuarioPanel.setLayout(new BoxLayout(usuarioPanel, BoxLayout.Y_AXIS));
        usuarioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        usuarioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        usuarioField = new JTextField(25);
        usuarioField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usuarioField.setMaximumSize(new Dimension(300, 35));
        usuarioField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usuarioField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        usuarioPanel.add(usuarioLabel);
        usuarioPanel.add(Box.createVerticalStrut(5));
        usuarioPanel.add(usuarioField);

        // Painel para senha (centralizado)
        JPanel senhaPanel = new JPanel();
        senhaPanel.setBackground(Color.WHITE);
        senhaPanel.setLayout(new BoxLayout(senhaPanel, BoxLayout.Y_AXIS));
        senhaPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        senhaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        senhaField = new JPasswordField(25);
        senhaField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        senhaField.setMaximumSize(new Dimension(300, 35));
        senhaField.setAlignmentX(Component.CENTER_ALIGNMENT);
        senhaField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        // Enter para fazer login
        senhaField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });

        senhaPanel.add(senhaLabel);
        senhaPanel.add(Box.createVerticalStrut(5));
        senhaPanel.add(senhaField);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        loginButton = new JButton("Entrar");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        loginButton.setBackground(new Color(46, 204, 113));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(100, 35));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.addActionListener(e -> performLogin());

        cancelButton = new JButton("Cancelar");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        // Adicionar componentes ao painel de login
        loginPanel.add(titleLabel);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(subtitleLabel);
        loginPanel.add(Box.createVerticalStrut(40));
        loginPanel.add(usuarioPanel);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(senhaPanel);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(buttonPanel);

        // Adicionar ao painel principal (centralizado)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(loginPanel, gbc);

        // Adicionar à janela
        add(mainPanel);
    }

    /**
     * Executa o processo de login
     */
    private void performLogin() {
        String usuario = usuarioField.getText().trim();
        String senha = new String(senhaField.getPassword());

        // Validação básica
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Por favor, preencha todos os campos.",
                    "Campos Vazios",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Desabilitar botão durante login
        loginButton.setEnabled(false);
        loginButton.setText("Entrando...");

        // Executar login em thread separada
        new SwingWorker<LoginResponse, Void>() {
            @Override
            protected LoginResponse doInBackground() {
                return authService.login(usuario, senha);
            }

            @Override
            protected void done() {
                try {
                    LoginResponse response = get();

                    if (response.isSucesso()) {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                "Bem-vindo, " + response.getUsuario() + "!\n" +
                                        "Tipo de Acesso: " + response.getTipoAcesso().getDescricao(),
                                "Login Bem-sucedido",
                                JOptionPane.INFORMATION_MESSAGE);

                        // Abrir tela principal
                        openMainFrame(response);
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                response.getMensagem(),
                                "Erro no Login",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                            "Erro ao processar login: " + e.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                } finally {
                    loginButton.setEnabled(true);
                    loginButton.setText("Entrar");
                    senhaField.setText("");
                }
            }
        }.execute();
    }

    /**
     * Abre a tela principal após login bem-sucedido
     */
    private void openMainFrame(LoginResponse loginResponse) {
        SwingUtilities.invokeLater(() -> {
            if (loginResponse != null && loginResponse.getTipoAcesso() == TipoAcesso.FUNCIONARIO) {
                FuncionarioFrame f = new FuncionarioFrame(loginResponse);
                f.setVisible(true);
            } else {
                MainFrame mainFrame = new MainFrame(loginResponse);
                mainFrame.setVisible(true);
            }
            dispose();
        });
    }
}
