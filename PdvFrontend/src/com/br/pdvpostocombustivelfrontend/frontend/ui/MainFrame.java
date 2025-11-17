package com.br.pdvpostocombustivelfrontend.frontend.ui;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginResponse;
import com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa;
import com.br.pdvpostocombustivelfrontend.frontend.model.Produto;
import com.br.pdvpostocombustivelfrontend.frontend.model.Preco;
import com.br.pdvpostocombustivelfrontend.frontend.model.Estoque;
import com.br.pdvpostocombustivelfrontend.frontend.model.Custo;
import com.br.pdvpostocombustivelfrontend.frontend.service.PessoaService;
import com.br.pdvpostocombustivelfrontend.frontend.service.CrudService;
import com.br.pdvpostocombustivelfrontend.frontend.service.PrecoService;
import com.br.pdvpostocombustivelfrontend.frontend.service.EstoqueService;
import com.br.pdvpostocombustivelfrontend.frontend.service.CustoService;
import com.br.pdvpostocombustivelfrontend.frontend.util.JsonParser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tela principal do sistema após login
 */
public class MainFrame extends JFrame {

    private final LoginResponse loginResponse;
    private JLabel statusLabel;
    private Timer clockTimer;
    private JTabbedPane tabbedPane;

    private JTable pessoasTable;
    private JTable produtosTable;
    private JTable contatosTable;
    private JTable precosTable;
    private JTable estoquesTable;
    private JTable custosTable;
    private DefaultTableModel pessoasModel;
    private DefaultTableModel produtosModel;
    private DefaultTableModel contatosModel;
    private DefaultTableModel precosModel;
    private DefaultTableModel estoquesModel;
    private DefaultTableModel custosModel;

    public MainFrame(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        initComponents();
        startClock();
    }

    private void initComponents() {
        setTitle(AppConfig.APP_TITLE + " - Sistema Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(createTopBar(), BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Pessoas", createPessoasPanel());
        tabbedPane.addTab("Produtos", createProdutosPanel());
        tabbedPane.addTab("Contatos", createContatosPanel());
        tabbedPane.addTab("Preços", createPrecosPanel());
        tabbedPane.addTab("Estoque", createEstoquesPanel());
        tabbedPane.addTab("Custos", createCustosPanel());

        add(tabbedPane, BorderLayout.CENTER);
        add(createBottomBar(), BorderLayout.SOUTH);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(52, 152, 219));
        topBar.setPreferredSize(new Dimension(0, 60));

        JLabel titleLabel = new JLabel("🚗 " + AppConfig.APP_TITLE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        userPanel.setBackground(new Color(52, 152, 219));

        JLabel userLabel = new JLabel("👤 " + loginResponse.getUsuario() + " | " +
                loginResponse.getTipoAcesso().getDescricao());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(Color.WHITE);

        JButton logoutButton = new JButton("Sair");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(e -> logout());

        userPanel.add(userLabel);
        userPanel.add(logoutButton);

        topBar.add(titleLabel, BorderLayout.WEST);
        topBar.add(userPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(236, 240, 241));

        JLabel welcomeLabel = new JLabel("Bem-vindo ao PDV Posto de Combustível!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        panel.add(welcomeLabel);

        return panel;
    }

    private JPanel createPessoasPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(236, 240, 241));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241));

        JButton addButton = new JButton("+ Adicionar");
        addButton.addActionListener(e -> adicionarPessoa());

        JButton editButton = new JButton("✎ Editar");
        editButton.addActionListener(e -> editarPessoa());

        JButton deleteButton = new JButton("🗑 Deletar");
        deleteButton.addActionListener(e -> deletarPessoa());

        JButton refreshButton = new JButton("🔄 Atualizar");
        refreshButton.addActionListener(e -> carregarPessoas());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        pessoasModel = new DefaultTableModel(
                new String[]{"ID", "Nome", "CPF", "CTPS", "Data Nasc."}, 0);

        pessoasTable = new JTable(pessoasModel);
        pessoasTable.setName("pessoasTable");
        JScrollPane scrollPane = new JScrollPane(pessoasTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        carregarPessoas();

        return panel;
    }

    private JPanel createProdutosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(236, 240, 241));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241));

        JButton addButton = new JButton("+ Adicionar");
        addButton.addActionListener(e -> adicionarProduto());

        JButton editButton = new JButton("✎ Editar");
        editButton.addActionListener(e -> editarProduto());

        JButton deleteButton = new JButton("🗑 Deletar");
        deleteButton.addActionListener(e -> deletarProduto());

        JButton refreshButton = new JButton("🔄 Atualizar");
        refreshButton.addActionListener(e -> carregarProdutos());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        produtosModel = new DefaultTableModel(
                new String[]{"ID", "Nome", "Ref", "Fornecedor", "Marca", "Tipo"}, 0);

        produtosTable = new JTable(produtosModel);
        produtosTable.setName("produtosTable");
        JScrollPane scrollPane = new JScrollPane(produtosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        carregarProdutos();

        return panel;
    }

    private JPanel createContatosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(236, 240, 241));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241));

        JButton addButton = new JButton("+ Adicionar");
        addButton.addActionListener(e -> adicionarContato());

        JButton editButton = new JButton("✎ Editar");
        editButton.addActionListener(e -> editarContato());

        JButton deleteButton = new JButton("🗑 Deletar");
        deleteButton.addActionListener(e -> deletarContato());

        JButton refreshButton = new JButton("🔄 Atualizar");
        refreshButton.addActionListener(e -> carregarContatos());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        // Mostrar endereço e tipo explicitamente para facilitar edição
        contatosModel = new DefaultTableModel(
                new String[]{"ID", "Endereço", "Email", "Telefone", "Tipo"}, 0);
        contatosTable = new JTable(contatosModel);
        contatosTable.setName("contatosTable");
        JScrollPane scrollPane = new JScrollPane(contatosTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        carregarContatos();

        return panel;
    }

    private JPanel createPrecosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(236, 240, 241));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241));

        JButton addButton = new JButton("+ Adicionar");
        addButton.addActionListener(e -> adicionarPreco());
        JButton editButton = new JButton("✎ Editar");
        editButton.addActionListener(e -> editarPreco());
        JButton deleteButton = new JButton("🗑 Deletar");
        deleteButton.addActionListener(e -> deletarPreco());
        JButton refreshButton = new JButton("🔄 Atualizar");
        refreshButton.addActionListener(e -> carregarPrecos());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        precosModel = new DefaultTableModel(new String[]{"ID", "Valor", "Data", "Hora", "Tipo"}, 0);
        precosTable = new JTable(precosModel);
        precosTable.setName("precosTable");
        panel.add(new JScrollPane(precosTable), BorderLayout.CENTER);

        carregarPrecos();
        return panel;
    }

    private JPanel createEstoquesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(236, 240, 241));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241));

        JButton addButton = new JButton("+ Adicionar");
        addButton.addActionListener(e -> adicionarEstoque());
        JButton editButton = new JButton("✎ Editar");
        editButton.addActionListener(e -> editarEstoque());
        JButton deleteButton = new JButton("🗑 Deletar");
        deleteButton.addActionListener(e -> deletarEstoque());
        JButton refreshButton = new JButton("🔄 Atualizar");
        refreshButton.addActionListener(e -> carregarEstoques());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        estoquesModel = new DefaultTableModel(new String[]{"ID", "Quantidade", "Tanque", "Endereço", "Fabricação", "Validade", "Tipo"}, 0);
        estoquesTable = new JTable(estoquesModel);
        estoquesTable.setName("estoquesTable");
        panel.add(new JScrollPane(estoquesTable), BorderLayout.CENTER);

        carregarEstoques();
        return panel;
    }

    private JPanel createCustosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(236, 240, 241));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(new Color(236, 240, 241));

        JButton addButton = new JButton("+ Adicionar");
        addButton.addActionListener(e -> adicionarCusto());
        JButton editButton = new JButton("✎ Editar");
        editButton.addActionListener(e -> editarCusto());
        JButton deleteButton = new JButton("🗑 Deletar");
        deleteButton.addActionListener(e -> deletarCusto());
        JButton refreshButton = new JButton("🔄 Atualizar");
        refreshButton.addActionListener(e -> carregarCustos());

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        custosModel = new DefaultTableModel(new String[]{"ID", "Imposto", "Frete", "Seguro", "Custo Var", "Custo Fixo", "Margem", "Tipo"}, 0);
        custosTable = new JTable(custosModel);
        custosTable.setName("custosTable");
        panel.add(new JScrollPane(custosTable), BorderLayout.CENTER);

        carregarCustos();
        return panel;
    }

    private JPanel createBottomBar() {
        JPanel bottomBar = new JPanel(new BorderLayout());
        bottomBar.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        bottomBar.setPreferredSize(new Dimension(0, 30));

        statusLabel = new JLabel("Pronto");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        bottomBar.add(statusLabel, BorderLayout.WEST);

        return bottomBar;
    }

    private void carregarPessoas() {
        pessoasModel.setRowCount(0);

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return PessoaService.list(0, 50);
            }

            @Override
            protected void done() {
                try {
                    String response = get();

                    if (response == null || response.trim().isEmpty()) {
                        statusLabel.setText("Erro: resposta vazia do serviço de pessoas");
                        JOptionPane.showMessageDialog(MainFrame.this, "Resposta vazia ao carregar pessoas. Verifique se o backend está rodando.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Se for uma resposta de erro do backend (padrão Spring), mostrar detalhes
                    if (JsonParser.isError(response)) {
                        String msg = JsonParser.extractJsonValue(response, "message");
                        if (msg == null) msg = JsonParser.extractJsonValue(response, "mensagem");
                        if (msg == null) msg = JsonParser.extractJsonValue(response, "error");
                        statusLabel.setText("Erro ao carregar pessoas");
                        JOptionPane.showMessageDialog(MainFrame.this, "Erro ao carregar pessoas: " + (msg != null ? msg : response), "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Extrai itens do array 'content' quando a resposta é um Page do Spring
                    String[] items = JsonParser.extractJsonArrayItems(response, "content");
                    if (items == null || items.length == 0) {
                        // fallback: se não vier paginado, tentar extrair objetos diretamente
                        items = JsonParser.extractJsonArrayItems(response, null);
                    }

                    // Se ainda vazio, tentar tratar como único objeto JSON
                    if (items == null || items.length == 0) {
                        String trimmed = response.trim();
                        if (trimmed.startsWith("{")) {
                            // é um único objeto
                            items = new String[]{trimmed};
                        }
                    }

                    if (items == null || items.length == 0) {
                        statusLabel.setText("Conectado | Pessoas: 0");
                        return;
                    }

                    for (String item : items) {
                        if (item == null || item.trim().isEmpty()) continue;

                        Pessoa p = JsonParser.parsePessoa(item);
                        if (p == null) continue;

                        Object idObj = p.getId();
                        Object id = null;
                        if (idObj != null) id = idObj;

                        pessoasModel.addRow(new Object[]{
                                normalizeIdForModel(id),
                                p.getNomeCompleto() != null ? p.getNomeCompleto() : "",
                                p.getCpfCnpj() != null ? p.getCpfCnpj() : "",
                                p.getNumeroCtps() != null ? p.getNumeroCtps() : "",
                                p.getDataNascimento() != null ? p.getDataNascimento() : ""
                        });
                    }

                    statusLabel.setText("Conectado | Pessoas: " + pessoasModel.getRowCount());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    System.err.println("Interrupted while loading pessoas: " + ie.getMessage());
                    statusLabel.setText("Erro: operação interrompida");
                    JOptionPane.showMessageDialog(MainFrame.this, "Operação interrompida ao carregar pessoas.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    System.err.println("Erro inesperado ao carregar pessoas: " + e.getMessage());
                    statusLabel.setText("Erro: " + e.getMessage());
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro ao carregar pessoas: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void carregarProdutos() {
        produtosModel.setRowCount(0);

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return CrudService.listProdutos(0, 50);
            }

            @Override
            protected void done() {
                try {
                    String response = get();

                    String[] items = JsonParser.extractJsonArrayItems(response, "content");
                    if (items.length == 0) {
                        items = JsonParser.extractJsonArrayItems(response, null);
                    }

                    for (String item : items) {
                        if (item == null || item.trim().isEmpty()) continue;
                        Produto p = JsonParser.parseProduto(item);
                        if (p != null) {
                            produtosModel.addRow(new Object[]{
                                    normalizeIdForModel(p.getId()),
                                    p.getNome() != null ? p.getNome() : "",
                                    p.getReferencia() != null ? p.getReferencia() : "",
                                    p.getFornecedor() != null ? p.getFornecedor() : "",
                                    p.getMarca() != null ? p.getMarca() : "",
                                    p.getTipoProduto() != null ? p.getTipoProduto() : ""
                            });
                        }
                    }

                    statusLabel.setText("Conectado | Produtos: " + produtosModel.getRowCount());
                } catch (Exception e) {
                    statusLabel.setText("Erro: " + e.getMessage());
                }
            }
        }.execute();
    }

    private void carregarContatos() {
        contatosModel.setRowCount(0);

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.list(0, 50);
            }

            @Override
            protected void done() {
                try {
                    String response = get();
                    String[] items = JsonParser.extractJsonArrayItems(response, "content");
                    if (items.length == 0) items = JsonParser.extractJsonArrayItems(response, null);

                    for (String item : items) {
                        if (item == null || item.trim().isEmpty()) continue;
                        com.br.pdvpostocombustivelfrontend.frontend.model.Contato c = JsonParser.parseContato(item);
                        if (c != null) {
                            contatosModel.addRow(new Object[]{
                                    normalizeIdForModel(c.getId()),
                                    c.getEndereco() != null ? c.getEndereco() : "",
                                    c.getEmail() != null ? c.getEmail() : "",
                                    c.getTelefone() != null ? c.getTelefone() : "",
                                    c.getTipoContato() != null ? c.getTipoContato() : ""
                            });
                        }
                    }

                    statusLabel.setText("Conectado | Contatos: " + contatosModel.getRowCount());
                } catch (Exception e) {
                    statusLabel.setText("Erro: " + e.getMessage());
                }
            }
        }.execute();
    }

    private void carregarPrecos() {
        precosModel.setRowCount(0);
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return PrecoService.list(0, 50);
            }

            @Override
            protected void done() {
                try {
                    String res = get();
                    String[] items = JsonParser.extractJsonArrayItems(res, "content");
                    if (items.length == 0) items = JsonParser.extractJsonArrayItems(res, null);
                    for (String it : items) {
                        if (it == null || it.trim().isEmpty()) continue;
                        Preco p = JsonParser.parsePreco(it);
                        if (p != null) {
                            // If ID missing, try several fallback keys in the raw JSON
                            if (p.getId() == null) {
                                Long tryId = JsonParser.extractLongFallback(it, "id", "precoId", "codigo", "produtoId", "custoId");
                                if (tryId != null) {
                                    p.setId(tryId);
                                    System.out.println("DEBUG carregarPrecos: extraido id fallback=" + tryId + " para item: " + it);
                                } else {
                                    System.err.println("DEBUG carregarPrecos: id ausente e fallback falhou para item: " + it);
                                }
                            }

                            // Normalize valor (accept things like "12m" or "12,00")
                            try {
                                String rawValor = p.getValor();
                                if (rawValor != null) {
                                    rawValor = rawValor.trim();
                                    // replace comma with dot
                                    rawValor = rawValor.replace(',', '.');
                                    // keep digits, dot and minus
                                    rawValor = rawValor.replaceAll("[^0-9.\\-]", "");
                                    p.setValor(rawValor);
                                }
                            } catch (Exception ex) {
                                System.err.println("DEBUG carregarPrecos: falha ao normalizar valor: " + ex.getMessage());
                            }

                            String date = JsonParser.formatDate(p.getDataAlteracao());
                            String time = JsonParser.formatTime(p.getHoraAlteracao());
                            precosModel.addRow(new Object[]{normalizeIdForModel(p.getId()), p.getValor(), date, time, p.getTipoPreco()});
                        } else {
                            System.err.println("DEBUG carregarPrecos: parsePreco retornou null para item: " + it);
                        }
                    }
                    statusLabel.setText("Conectado | Preços: " + precosModel.getRowCount());
                } catch (Exception e) {
                    statusLabel.setText("Erro: " + e.getMessage());
                }

            }
        }.execute();
    }

    private void carregarEstoques() {
        estoquesModel.setRowCount(0);
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return EstoqueService.list(0, 50);
            }

            @Override
            protected void done() {
                try {
                    String res = get();
                    String[] items = JsonParser.extractJsonArrayItems(res, "content");
                    if (items.length == 0) items = JsonParser.extractJsonArrayItems(res, null);
                    for (String it : items) {
                        if (it == null || it.trim().isEmpty()) continue;
                        Estoque e = JsonParser.parseEstoque(it);
                        if (e != null) {
                            String dv = JsonParser.formatDate(e.getDataValidade());
                            estoquesModel.addRow(new Object[]{normalizeIdForModel(e.getId()), e.getQuantidade(), e.getLocalTanque(), e.getLocalEndereco(), e.getLocalFabricacao(), dv, e.getTipoEstoque()});
                        }
                    }
                    statusLabel.setText("Conectado | Estoques: " + estoquesModel.getRowCount());
                } catch (Exception ex) {
                    statusLabel.setText("Erro: " + ex.getMessage());
                }
            }
        }.execute();
    }

    private void carregarCustos() {
        custosModel.setRowCount(0);
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return CustoService.list(0, 50);
            }

            @Override
            protected void done() {
                try {
                    String res = get();
                    String[] items = JsonParser.extractJsonArrayItems(res, "content");
                    if (items == null || items.length == 0) items = JsonParser.extractJsonArrayItems(res, null);
                    if (items != null) {
                        for (String it : items) {
                            if (it == null || it.trim().isEmpty()) continue;
                            Custo c = JsonParser.parseCusto(it);
                            if (c != null) {
                                if (c.getId() == null) {
                                    Long tryId = JsonParser.extractLongFallback(it, "id", "custoId", "codigo", "produtoId");
                                    if (tryId != null) {
                                        c.setId(tryId);
                                        System.out.println("DEBUG carregarCustos: extraido id fallback=" + tryId + " para item: " + it);
                                    } else {
                                        System.err.println("DEBUG carregarCustos: id ausente e fallback falhou para item: " + it);
                                    }
                                }

                                // Normalize numeric fields (imposto, frete, seguro, etc.) to remove stray chars and unify decimal point
                                try {
                                    if (c.getImposto() != null)
                                        c.setImposto(c.getImposto().replace(',', '.').replaceAll("[^0-9.\\-]", ""));
                                    if (c.getFrete() != null)
                                        c.setFrete(c.getFrete().replace(',', '.').replaceAll("[^0-9.\\-]", ""));
                                    if (c.getSeguro() != null)
                                        c.setSeguro(c.getSeguro().replace(',', '.').replaceAll("[^0-9.\\-]", ""));
                                    if (c.getCustoVariavel() != null)
                                        c.setCustoVariavel(c.getCustoVariavel().replace(',', '.').replaceAll("[^0-9.\\-]", ""));
                                    if (c.getCustoFixo() != null)
                                        c.setCustoFixo(c.getCustoFixo().replace(',', '.').replaceAll("[^0-9.\\-]", ""));
                                    if (c.getMargemLucro() != null)
                                        c.setMargemLucro(c.getMargemLucro().replace(',', '.').replaceAll("[^0-9.\\-]", ""));
                                } catch (Exception ex) {
                                    System.err.println("DEBUG carregarCustos: falha ao normalizar numeros: " + ex.getMessage());
                                }

                                custosModel.addRow(new Object[]{normalizeIdForModel(c.getId()), c.getImposto(), c.getFrete(), c.getSeguro(), c.getCustoVariavel(), c.getCustoFixo(), c.getMargemLucro(), c.getTipoCusto()});
                            } else {
                                System.err.println("DEBUG carregarCustos: parseCusto retornou null para item: " + it);
                            }
                        }
                    }

                    statusLabel.setText("Conectado | Custos: " + custosModel.getRowCount());
                } catch (Exception ex) {
                    statusLabel.setText("Erro: " + ex.getMessage());
                }
            }
        }.execute();
    }

    // ---------- Ações (stubs) ----------
    private void logout() {
        int ok = JOptionPane.showConfirmDialog(this, "Deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    private void adicionarPessoa() {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa novo = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showPessoaDialog(this, null, "Adicionar Pessoa");
            if (novo == null) return;
            String resp = PessoaService.create(novo);
            if (JsonParser.isError(resp))
                JOptionPane.showMessageDialog(this, "Erro criando pessoa: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Pessoa criada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar pessoa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            carregarPessoas();
        }
    }

    private void editarPessoa() {
        int sel = pessoasTable.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = pessoasModel.getValueAt(sel, 0);
        Long id = parseLongFromObject(idObj);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Não é possível editar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa orig = new com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa();
        orig.setId(id);
        orig.setNomeCompleto(stringValueForModel(pessoasModel.getValueAt(sel, 1)));
        orig.setCpfCnpj(stringValueForModel(pessoasModel.getValueAt(sel, 2)));
        orig.setNumeroCtps(parseLongFromObject(pessoasModel.getValueAt(sel, 3)));
        orig.setDataNascimento(stringValueForModel(pessoasModel.getValueAt(sel, 4)));

        com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa edited = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showPessoaDialog(this, orig, "Editar Pessoa");
        if (edited == null) return;
        try {
            if (edited.getId() != null) {
                String resp = PessoaService.update(edited.getId(), edited);
                if (JsonParser.isError(resp))
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar pessoa: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "Pessoa atualizada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String resp = PessoaService.create(edited);
                if (JsonParser.isError(resp))
                    JOptionPane.showMessageDialog(this, "Erro ao criar pessoa: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Pessoa criada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar pessoa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            carregarPessoas();
        }
    }

    private void deletarPessoa() {
        int sel = pessoasTable.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = pessoasModel.getValueAt(sel, 0);
        Long id = parseLongFromObject(idObj);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Não é possível deletar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Pessoa id=" + id)) return;
        try {
            String resp = PessoaService.delete(id);
            if (JsonParser.isError(resp))
                JOptionPane.showMessageDialog(this, "Erro ao deletar pessoa: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Pessoa deletada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar pessoa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            carregarPessoas();
        }
    }

    private void adicionarProduto() {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Produto novo = CrudDialog.showProdutoDialog(this, null, "Adicionar Produto");
            if (novo == null) return;
            String resp = CrudService.createProduto(novo);
            if (JsonParser.isError(resp))
                JOptionPane.showMessageDialog(this, "Erro criando produto: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Produto criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            carregarProdutos();
        }
    }

    private void editarProduto() {
        int sel = produtosTable.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = produtosModel.getValueAt(sel, 0);
        Long id = parseLongFromObject(idObj);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Não é possível editar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        com.br.pdvpostocombustivelfrontend.frontend.model.Produto orig = new com.br.pdvpostocombustivelfrontend.frontend.model.Produto();
        orig.setId(id);
        orig.setNome(stringValueForModel(produtosModel.getValueAt(sel, 1)));
        orig.setReferencia(stringValueForModel(produtosModel.getValueAt(sel, 2)));
        orig.setFornecedor(stringValueForModel(produtosModel.getValueAt(sel, 3)));
        orig.setMarca(stringValueForModel(produtosModel.getValueAt(sel, 4)));
        orig.setTipoProduto(stringValueForModel(produtosModel.getValueAt(sel, 5)));

        com.br.pdvpostocombustivelfrontend.frontend.model.Produto edited = CrudDialog.showProdutoDialog(this, orig, "Editar Produto");
        if (edited == null) return;
        try {
            if (edited.getId() != null) {
                String resp = CrudService.updateProduto(edited.getId(), edited);
                if (JsonParser.isError(resp))
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar produto: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else
                    JOptionPane.showMessageDialog(this, "Produto atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String resp = CrudService.createProduto(edited);
                if (JsonParser.isError(resp))
                    JOptionPane.showMessageDialog(this, "Erro ao criar produto: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Produto criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            carregarProdutos();
        }
    }

    private void deletarProduto() {
        int sel = produtosTable.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um produto para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Object idObj = produtosModel.getValueAt(sel, 0);
        Long id = parseLongFromObject(idObj);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Não é possível deletar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Produto id=" + id)) return;
        try {
            String resp = CrudService.deleteProduto(id);
            if (JsonParser.isError(resp))
                JOptionPane.showMessageDialog(this, "Erro ao deletar produto: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Produto deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            carregarProdutos();
        }
    }

    // ====== Contatos CRUD ======
    private void adicionarContato() {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Contato novo = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showContatoDialog(this, null, "Adicionar Contato");
            if (novo == null) return;
            String resp = com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.create(novo);
            if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando contato: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Contato criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar contato: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally { carregarContatos(); }
    }

    private void editarContato() {
        int sel = contatosTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um contato para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = contatosModel.getValueAt(sel, 0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível editar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        com.br.pdvpostocombustivelfrontend.frontend.model.Contato orig = new com.br.pdvpostocombustivelfrontend.frontend.model.Contato();
        orig.setId(id);
        orig.setEndereco(stringValueForModel(contatosModel.getValueAt(sel,1)));
        orig.setEmail(stringValueForModel(contatosModel.getValueAt(sel,2)));
        orig.setTelefone(stringValueForModel(contatosModel.getValueAt(sel,3)));
        orig.setTipoContato(stringValueForModel(contatosModel.getValueAt(sel,4)));

        com.br.pdvpostocombustivelfrontend.frontend.model.Contato edited = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showContatoDialog(this, orig, "Editar Contato");
        if (edited == null) return;
        try {
            if (edited.getId() != null) {
                String resp = com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.update(edited.getId(), edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao atualizar contato: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Contato atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String resp = com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.create(edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao criar contato: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Contato criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar contato: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarContatos(); }
    }

    private void deletarContato() {
        int sel = contatosTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um contato para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = contatosModel.getValueAt(sel,0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível deletar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Contato id=" + id)) return;
        try { String resp = com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao deletar contato: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Contato deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar contato: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarContatos(); }
    }

    // ====== Preços CRUD ======
    private void adicionarPreco() {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Preco novo = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showPrecoDialog(this, null, "Adicionar Preço");
            if (novo == null) return;
            String resp = PrecoService.create(novo);
            if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando preço: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Preço criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar preço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPrecos(); }
    }

    private void editarPreco() {
        int sel = precosTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um preço para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = precosModel.getValueAt(sel,0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível editar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        com.br.pdvpostocombustivelfrontend.frontend.model.Preco orig = new com.br.pdvpostocombustivelfrontend.frontend.model.Preco();
        orig.setId(id);
        orig.setValor(stringValueForModel(precosModel.getValueAt(sel,1)));
        orig.setDataAlteracao(stringValueForModel(precosModel.getValueAt(sel,2)));
        orig.setHoraAlteracao(stringValueForModel(precosModel.getValueAt(sel,3)));
        orig.setTipoPreco(stringValueForModel(precosModel.getValueAt(sel,4)));

        com.br.pdvpostocombustivelfrontend.frontend.model.Preco edited = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showPrecoDialog(this, orig, "Editar Preço");
        if (edited == null) return;
        try {
            if (edited.getId() != null) {
                String resp = PrecoService.update(edited.getId(), edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao atualizar preço: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Preço atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String resp = PrecoService.create(edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao criar preço: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Preço criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar preço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPrecos(); }
    }

    private void deletarPreco() {
        int sel = precosTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um preço para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = precosModel.getValueAt(sel,0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível deletar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Preço id=" + id)) return;
        try { String resp = PrecoService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao deletar preço: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Preço deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar preço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPrecos(); }
    }

    // ====== Estoque CRUD ======
    private void adicionarEstoque() {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Estoque novo = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showEstoqueDialog(this, null, "Adicionar Estoque");
            if (novo == null) return;
            String resp = EstoqueService.create(novo);
            if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando estoque: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Estoque criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarEstoques(); }
    }

    private void editarEstoque() {
        int sel = estoquesTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um estoque para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = estoquesModel.getValueAt(sel,0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível editar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        com.br.pdvpostocombustivelfrontend.frontend.model.Estoque orig = new com.br.pdvpostocombustivelfrontend.frontend.model.Estoque();
        orig.setId(id);
        orig.setQuantidade(stringValueForModel(estoquesModel.getValueAt(sel,1)));
        orig.setLocalTanque(stringValueForModel(estoquesModel.getValueAt(sel,2)));
        orig.setLocalEndereco(stringValueForModel(estoquesModel.getValueAt(sel,3)));
        orig.setLocalFabricacao(stringValueForModel(estoquesModel.getValueAt(sel,4)));
        orig.setDataValidade(stringValueForModel(estoquesModel.getValueAt(sel,5)));
        orig.setTipoEstoque(stringValueForModel(estoquesModel.getValueAt(sel,6)));

        com.br.pdvpostocombustivelfrontend.frontend.model.Estoque edited = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showEstoqueDialog(this, orig, "Editar Estoque");
        if (edited == null) return;
        try {
            if (edited.getId() != null) {
                String resp = EstoqueService.update(edited.getId(), edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao atualizar estoque: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Estoque atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String resp = EstoqueService.create(edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao criar estoque: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Estoque criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarEstoques(); }
    }

    private void deletarEstoque() {
        int sel = estoquesTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um estoque para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = estoquesModel.getValueAt(sel,0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível deletar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Estoque id=" + id)) return;
        try { String resp = EstoqueService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao deletar estoque: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Estoque deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarEstoques(); }
    }

    // ====== Custos CRUD ======
    private void adicionarCusto() {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Custo novo = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showCustoDialog(this, null, "Adicionar Custo");
            if (novo == null) return;
            String resp = CustoService.create(novo);
            if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando custo: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Custo criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar custo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarCustos(); }
    }

    private void editarCusto() {
        int sel = custosTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um custo para editar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = custosModel.getValueAt(sel,0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível editar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        com.br.pdvpostocombustivelfrontend.frontend.model.Custo orig = new com.br.pdvpostocombustivelfrontend.frontend.model.Custo();
        orig.setId(id);
        orig.setImposto(stringValueForModel(custosModel.getValueAt(sel,1)));
        orig.setFrete(stringValueForModel(custosModel.getValueAt(sel,2)));
        orig.setSeguro(stringValueForModel(custosModel.getValueAt(sel,3)));
        orig.setCustoVariavel(stringValueForModel(custosModel.getValueAt(sel,4)));
        orig.setCustoFixo(stringValueForModel(custosModel.getValueAt(sel,5)));
        orig.setMargemLucro(stringValueForModel(custosModel.getValueAt(sel,6)));
        orig.setTipoCusto(stringValueForModel(custosModel.getValueAt(sel,7)));

        com.br.pdvpostocombustivelfrontend.frontend.model.Custo edited = com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog.showCustoDialog(this, orig, "Editar Custo");
        if (edited == null) return;
        try {
            if (edited.getId() != null) {
                String resp = CustoService.update(edited.getId(), edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao atualizar custo: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Custo atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                String resp = CustoService.create(edited);
                if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao criar custo: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Custo criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar custo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarCustos(); }
    }

    private void deletarCusto() {
        int sel = custosTable.getSelectedRow();
        if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um custo para deletar.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = custosModel.getValueAt(sel,0);
        Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Não é possível deletar: item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Custo id=" + id)) return;
        try { String resp = CustoService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao deletar custo: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Custo deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar custo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarCustos(); }
    }

    // Helper to safely convert table cell to String
    private String stringValueForModel(Object cell) {
        if (cell == null) return null;
        return cell.toString();
    }

    // Helper that attempts to extract a Long from various table cell representations
    private Long parseLongFromObject(Object o) {
        if (o == null) return null;
        if (o instanceof Number) return ((Number) o).longValue();
        String s = o.toString().trim();
        if (s.isEmpty()) return null;
        // remove non-digit except leading '-'
        String digits = s.replaceAll("[^0-9\\-]", "");
        if (digits.isEmpty()) return null;
        try { return Long.parseLong(digits); } catch (NumberFormatException ex) {
            try { double d = Double.parseDouble(s.replace(',', '.')); return (long) d; } catch (Exception e) { return null; }
        }
    }

    // ---------- Helpers ----------
    private void startClock() {
        try {
            final SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Timer t = new Timer(1000, e -> {
                try {
                    if (statusLabel != null) {
                        statusLabel.setToolTipText(fmt.format(new Date()));
                    }
                } catch (Exception ignored) {}
            });
            t.setInitialDelay(0);
            t.start();
            this.clockTimer = t;
        } catch (Exception ignored) {}
    }

    private Object normalizeIdForModel(Object id) {
        if (id == null) return "";
        if (id instanceof Number) return ((Number) id).longValue();
        return id.toString();
    }

    private Object normalizeIdForModel(Long id) {
        if (id == null) return "";
        return id;
    }

}
