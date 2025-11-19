package com.br.pdvpostocombustivelfrontend.frontend.ui;

import com.br.pdvpostocombustivelfrontend.frontend.model.LoginResponse;
import com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa;
import com.br.pdvpostocombustivelfrontend.frontend.model.Venda;
import com.br.pdvpostocombustivelfrontend.frontend.service.PessoaService;
import com.br.pdvpostocombustivelfrontend.frontend.service.VendaService;
import com.br.pdvpostocombustivelfrontend.frontend.util.BoletoGerador;
import com.br.pdvpostocombustivelfrontend.frontend.util.JsonParser;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

/**
 * Tela simples para usuários com acesso de FUNCIONARIO.
 * Inclui abas por bomba para registrar vendas.
 */
public class FuncionarioFrame extends JFrame {

    private final LoginResponse loginResponse;
    private JTabbedPane tabbedPane;

    // preço por litro por tipo de gasolina (exemplo)
    private final Map<String, Double> precoPorTipo = new HashMap<>() {{
        put("COMUM", 5.00);
        put("ADITIVADA", 5.50);
        put("DIESEL", 4.50);
    }};

    // Mantém uma aba aberta por bomba e referência para o botão
    private final Map<String, Component> openTabs = new HashMap<>();
    private final Map<String, JToggleButton> bombaButtons = new HashMap<>();
    private final Map<Component, String> tabToBomba = new HashMap<>();

    public FuncionarioFrame(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        initComponents();
    }

    private void initComponents() {
        setTitle("Área do Funcionário - " + (loginResponse!=null?loginResponse.getUsuario():""));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout(10,10));
        root.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel label = new JLabel("Área do Funcionário");
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        // top header with title and user/logout
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT)); left.setOpaque(false);
        left.add(label);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT)); right.setOpaque(false);
        JLabel userLabel = new JLabel((loginResponse!=null?"👤 "+loginResponse.getUsuario():""));
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JButton logoutBtn = new JButton("Deslogar");
        logoutBtn.setBackground(new Color(231,76,60)); logoutBtn.setForeground(Color.WHITE); logoutBtn.setFocusPainted(false); logoutBtn.setBorderPainted(false);
        logoutBtn.addActionListener(e -> logout());
        right.add(userLabel); right.add(logoutBtn);
        header.add(left, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);
        root.add(header, BorderLayout.NORTH);

        // Top: bombas (colocadas dentro do header para não sobrescrever o header)
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JToggleButton bomba1 = createBombaToggle("Bomba 1");
        JToggleButton bomba2 = createBombaToggle("Bomba 2");
        JToggleButton bomba3 = createBombaToggle("Bomba 3");
        top.add(bomba1); top.add(bomba2); top.add(bomba3);
        // adicionar o painel de bombas dentro do header (abaixo do título e do botão de deslogar)
        header.add(top, BorderLayout.SOUTH);

        // Center: tabbed pane onde as abas de venda serão criadas
        tabbedPane = new JTabbedPane();
        root.add(tabbedPane, BorderLayout.CENTER);

        add(root);
    }

    private JToggleButton createBombaToggle(String label) {
        JToggleButton btn = new JToggleButton(label);
        btn.setPreferredSize(new Dimension(160, 80));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setOpaque(true);

        // registrar botão para controle externo
        bombaButtons.put(label, btn);

        btn.addActionListener(e -> {
            boolean selected = btn.isSelected();
            if (selected) {
                btn.setBackground(new Color(46, 204, 113)); // ligada
                btn.setText(label + " - LIGADA");
                // Se já houver uma aba para esta bomba, apenas seleciona-a
                if (openTabs.containsKey(label)) {
                    Component existing = openTabs.get(label);
                    int idx = tabbedPane.indexOfComponent(existing);
                    if (idx >= 0) tabbedPane.setSelectedIndex(idx);
                    return;
                }
                // Senão, cria uma nova aba
                Component tab = createBombaTab(label);
                openTabs.put(label, tab);
                tabToBomba.put(tab, label);
                tabbedPane.addTab(label + " - Venda", tab);
                tabbedPane.setSelectedComponent(tab);
            } else {
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setText(label + " - DESLIGADA");
                // Fechar aba associada, se existir
                if (openTabs.containsKey(label)) {
                    Component existing = openTabs.remove(label);
                    tabToBomba.remove(existing);
                    int idx = tabbedPane.indexOfComponent(existing);
                    if (idx >= 0) tabbedPane.removeTabAt(idx);
                }
            }
        });
        btn.setSelected(false);
        btn.setText(label + " - DESLIGADA");
        return btn;
    }

    private Component createBombaTab(String bombaLabel) {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Form
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8,8,8,8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0;

        // Litros
        form.add(new JLabel("Quantidade (litros):"), gbc);
        gbc.gridx = 1;
        JTextField litrosField = new JTextField();
        form.add(litrosField, gbc);

        // Reais
        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Quantidade (R$):"), gbc);
        gbc.gridx = 1;
        JTextField reaisField = new JTextField();
        form.add(reaisField, gbc);

        // Cliente (combo) - carregado async
        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Cliente (Pessoa):"), gbc);
        gbc.gridx = 1;
        JComboBox<Pessoa> clienteCombo = new JComboBox<>();
        clienteCombo.setEditable(false);
        clienteCombo.addItem(null); // opção nenhum
        form.add(clienteCombo, gbc);

        // Tipo de gasolina
        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Tipo de Gasolina:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> tipoCombo = new JComboBox<>(precoPorTipo.keySet().toArray(new String[0]));
        form.add(tipoCombo, gbc);

        // Preço por litro (exibido e atualizado)
        gbc.gridx = 0; gbc.gridy++;
        form.add(new JLabel("Preço por litro (R$):"), gbc);
        gbc.gridx = 1;
        JLabel precoLabel = new JLabel();
        precoLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        form.add(precoLabel, gbc);

        // Buttons
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelar = new JButton("Cancelar");
        JButton gerar = new JButton("Gerar Boleto");
        buttons.add(cancelar);
        buttons.add(gerar);

        panel.add(form, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);

        // Inicializar preço exibido
        Runnable updatePrecoLabel = () -> {
            String tipo = (String) tipoCombo.getSelectedItem();
            double price = precoPorTipo.getOrDefault(tipo, 0.0);
            precoLabel.setText(String.format("R$ %.3f", price));
        };
        updatePrecoLabel.run();

        // Carregar lista de pessoas em background
        new SwingWorker<List<Pessoa>, Void>(){
            @Override protected List<Pessoa> doInBackground() throws Exception {
                List<Pessoa> list = new ArrayList<>();
                try {
                    String res = PessoaService.list(0, 200);
                    if (res==null || res.trim().isEmpty()) return list;
                    String[] items = JsonParser.extractJsonArrayItems(res, "content"); if (items==null||items.length==0) items = JsonParser.extractJsonArrayItems(res, null);
                    if (items==null) return list;
                    for (String it: items) {
                        Pessoa p = JsonParser.parsePessoa(it);
                        if (p!=null) list.add(p);
                    }
                } catch (Exception ex) { /* ignore */ }
                return list;
            }
            @Override protected void done() {
                try {
                    List<Pessoa> pessoas = get();
                    clienteCombo.removeAllItems();
                    clienteCombo.addItem(null);
                    for (Pessoa p: pessoas) clienteCombo.addItem(p);
                } catch (Exception ignored) {}
            }
        }.execute();

        // Binding liters <-> reais based on selected fuel price
        DocumentListener litersListener = new DocumentListener() {
            private void updateReais() {
                try {
                    String s = litrosField.getText().trim().replace(',', '.');
                    if (s.isEmpty()) { reaisField.setText(""); return; }
                    double litros = Double.parseDouble(s);
                    String tipo = (String) tipoCombo.getSelectedItem();
                    double price = precoPorTipo.getOrDefault(tipo, 0.0);
                    double total = litros * price;
                    reaisField.setText(String.format("%.2f", total));
                } catch (Exception ignored) {}
            }
            @Override public void insertUpdate(DocumentEvent e) { updateReais(); }
            @Override public void removeUpdate(DocumentEvent e) { updateReais(); }
            @Override public void changedUpdate(DocumentEvent e) { updateReais(); }
        };

        DocumentListener reaisListener = new DocumentListener() {
            private void updateLitros() {
                try {
                    String s = reaisField.getText().trim().replace(',', '.');
                    if (s.isEmpty()) { litrosField.setText(""); return; }
                    double reais = Double.parseDouble(s);
                    String tipo = (String) tipoCombo.getSelectedItem();
                    double price = precoPorTipo.getOrDefault(tipo, 0.0);
                    if (price <= 0) return;
                    double litros = reais / price;
                    litrosField.setText(String.format("%.3f", litros));
                } catch (Exception ignored) {}
            }
            @Override public void insertUpdate(DocumentEvent e) { updateLitros(); }
            @Override public void removeUpdate(DocumentEvent e) { updateLitros(); }
            @Override public void changedUpdate(DocumentEvent e) { updateLitros(); }
        };

        litrosField.getDocument().addDocumentListener(litersListener);
        reaisField.getDocument().addDocumentListener(reaisListener);

        // If fuel type changes, recalc and update price label
        tipoCombo.addActionListener(e -> {
            updatePrecoLabel.run();
            reaisListener.changedUpdate(null);
            litersListener.changedUpdate(null);
        });

        // Cancel: close the tab and toggle off the bomba button
        cancelar.addActionListener(e -> {
            int i = tabbedPane.indexOfComponent(panel);
            if (i >= 0) tabbedPane.removeTabAt(i);
            // localizar bomba associada a essa aba
            String bomba = tabToBomba.remove(panel);
            if (bomba != null) {
                openTabs.remove(bomba);
                JToggleButton tb = bombaButtons.get(bomba);
                if (tb != null) {
                    tb.setSelected(false);
                    tb.setBackground(Color.LIGHT_GRAY);
                    tb.setText(bomba + " - DESLIGADA");
                }
            }
        });

        // Gerar boleto: validate, generate, persist on backend and show boleto dialog
        gerar.addActionListener(e -> {
            try {
                String litrosStr = litrosField.getText().trim().replace(',', '.');
                String reaisStr = reaisField.getText().trim().replace(',', '.');
                if ((litrosStr.isEmpty() || Double.parseDouble(litrosStr) <= 0) && (reaisStr.isEmpty() || Double.parseDouble(reaisStr) <= 0)) {
                    JOptionPane.showMessageDialog(this, "Informe a quantidade em litros ou em reais.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String tipo = (String) tipoCombo.getSelectedItem();
                double price = precoPorTipo.getOrDefault(tipo, 0.0);
                double litros = !litrosStr.isEmpty() ? Double.parseDouble(litrosStr) : (Double.parseDouble(reaisStr) / price);
                double reais = !reaisStr.isEmpty() ? Double.parseDouble(reaisStr) : (litros * price);

                Pessoa cliente = (Pessoa) clienteCombo.getSelectedItem();
                String clienteNome = cliente!=null? (cliente.getNomeCompleto()!=null?cliente.getNomeCompleto():cliente.getCpfCnpj()) : "Consumidor não identificado";

                String vendaId = String.valueOf(System.currentTimeMillis() / 1000L);
                String descricao = bombaLabel + " - " + tipo + " - " + String.format(Locale.US, "%.3f L", litros);
                String data = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));

                // Persistir venda no backend em background usando VendaService
                Venda venda = new Venda();
                venda.setDescricaoProduto(descricao);
                // format numbers with Locale.US to ensure dot decimal and no thousand separator
                venda.setQuantidade(String.format(Locale.US, "%.3f", litros));
                venda.setValorUnitario(String.format(Locale.US, "%.2f", price));
                venda.setValorTotal(String.format(Locale.US, "%.2f", reais));
                venda.setDataVenda(data);
                // enviar hora com segundos
                venda.setHoraVenda(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                venda.setTipoPreco("UNITARIO");
                venda.setTipoCombustivel(mapTipoCombustivelToBackend(tipo));
                String obs = "Funcionario: " + (loginResponse!=null?loginResponse.getUsuario():"?") + "; Cliente: " + clienteNome + (cliente!=null?" (id="+cliente.getId()+")":"");
                venda.setObservacoes(obs);

                new SwingWorker<String, Void>(){
                    @Override protected String doInBackground() throws Exception {
                        try { return VendaService.create(venda); } catch (Exception ex) { return "__ERR__" + ex.getMessage(); }
                    }
                    @Override protected void done() {
                        try {
                            String resp = get();
                            // Mostrar resposta completa para debug
                            if (resp == null) {
                                JOptionPane.showMessageDialog(FuncionarioFrame.this, "Venda enviada, resposta vazia.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                            // exibir o conteúdo da resposta no console e em diálogo
                            System.out.println("Resposta backend venda: " + resp);
                            if (resp.startsWith("__ERR__")) {
                                JOptionPane.showMessageDialog(FuncionarioFrame.this, "Erro ao enviar venda: " + resp.substring(7) + "\nResposta: " + resp, "Erro", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            if (JsonParser.isError(resp)) {
                                JOptionPane.showMessageDialog(FuncionarioFrame.this, "Erro ao registrar venda: " + JsonParser.extractJsonValue(resp, "message") + "\nResposta: " + resp, "Erro", JOptionPane.ERROR_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(FuncionarioFrame.this, "Venda registrada com sucesso. Resposta: " + resp, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(FuncionarioFrame.this, "Erro ao processar resposta da venda: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }.execute();

                String boleto = BoletoGerador.gerarBoleto(vendaId, descricao, String.format(Locale.US, "%.2f", reais), data, hora, clienteNome);

                // Show boleto dialog with save/copy
                JTextArea textArea = new JTextArea(boleto);
                textArea.setEditable(false);
                textArea.setFont(new Font("Courier New", Font.PLAIN, 11));
                textArea.setLineWrap(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(900,600));

                int opcao = JOptionPane.showOptionDialog(this, scrollPane, "Boleto - " + vendaId, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Salvar","Copiar","Fechar"}, "Salvar");
                if (opcao == 0) {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setSelectedFile(new java.io.File("boleto_venda_" + vendaId + ".txt"));
                    int r = fileChooser.showSaveDialog(this);
                    if (r == JFileChooser.APPROVE_OPTION) {
                        String path = fileChooser.getSelectedFile().getAbsolutePath();
                        if (BoletoGerador.salvarBoleto(path, boleto)) JOptionPane.showMessageDialog(this, "Boleto salvo: " + path, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        else JOptionPane.showMessageDialog(this, "Erro ao salvar boleto.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else if (opcao == 1) {
                    java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new java.awt.datatransfer.StringSelection(boleto), null);
                    JOptionPane.showMessageDialog(this, "Boleto copiado para a área de transferência.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Valor inválido: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar boleto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // track mapping
        tabToBomba.put(panel, bombaLabel);

        return panel;
    }

    // Helper to map UI fuel types to backend enum names
    private String mapTipoCombustivelToBackend(String tipo) {
        if (tipo == null) return "OUTROS";
        tipo = tipo.toUpperCase();
        return switch (tipo) {
            case "COMUM", "ADITIVADA" -> "GASOLINA";
            case "DIESEL" -> "DIESEL";
            case "ALCOOL" -> "ALCOOL";
            default -> "OUTROS";
        };
    }

    private void logout() {
        int ok = JOptionPane.showConfirmDialog(this, "Deseja deslogar e voltar para a tela de login?", "Deslogar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                try {
                    LoginFrame login = new LoginFrame();
                    login.setVisible(true);
                } catch (Exception ex) {
                    System.err.println("Erro ao abrir LoginFrame após logout: " + ex.getMessage());
                }
            });
        }
    }
}
