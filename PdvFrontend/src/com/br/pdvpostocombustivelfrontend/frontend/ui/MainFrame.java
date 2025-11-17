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

        contatosModel = new DefaultTableModel(
            new String[]{"ID", "Nome", "Email", "Telefone"}, 0);
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

        JButton addButton = new JButton("+ Adicionar"); addButton.addActionListener(e -> adicionarEstoque());
        JButton editButton = new JButton("✎ Editar"); editButton.addActionListener(e -> editarEstoque());
        JButton deleteButton = new JButton("🗑 Deletar"); deleteButton.addActionListener(e -> deletarEstoque());
        JButton refreshButton = new JButton("🔄 Atualizar"); refreshButton.addActionListener(e -> carregarEstoques());

        buttonPanel.add(addButton); buttonPanel.add(editButton); buttonPanel.add(deleteButton); buttonPanel.add(refreshButton);
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

        JButton addButton = new JButton("+ Adicionar"); addButton.addActionListener(e -> adicionarCusto());
        JButton editButton = new JButton("✎ Editar"); editButton.addActionListener(e -> editarCusto());
        JButton deleteButton = new JButton("🗑 Deletar"); deleteButton.addActionListener(e -> deletarCusto());
        JButton refreshButton = new JButton("🔄 Atualizar"); refreshButton.addActionListener(e -> carregarCustos());

        buttonPanel.add(addButton); buttonPanel.add(editButton); buttonPanel.add(deleteButton); buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        custosModel = new DefaultTableModel(new String[]{"ID","Imposto","Frete","Seguro","Custo Var","Custo Fixo","Margem","Tipo"}, 0);
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
                                c.getTelefone() != null ? c.getTelefone() : ""
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
         new SwingWorker<String, Void>(){
             @Override protected String doInBackground() throws Exception { return PrecoService.list(0,50); }
             @Override protected void done(){
                 try {
                     String res = get();
                     String[] items = JsonParser.extractJsonArrayItems(res, "content");
                     if (items.length==0) items = JsonParser.extractJsonArrayItems(res,null);
                     for(String it: items){
                         if (it==null||it.trim().isEmpty()) continue;
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
                                    rawValor = rawValor.replaceAll("[^0-9.\-]", "");
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
                 } catch(Exception e){ statusLabel.setText("Erro: " + e.getMessage()); }
             }
         }.execute();
     }

    private void carregarEstoques(){ estoquesModel.setRowCount(0); new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return EstoqueService.list(0,50);} @Override protected void done(){ try{ String res = get(); String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items.length==0) items = JsonParser.extractJsonArrayItems(res,null); for(String it: items){ if (it==null||it.trim().isEmpty()) continue; Estoque e = JsonParser.parseEstoque(it); if (e!=null) { String dv = JsonParser.formatDate(e.getDataValidade()); estoquesModel.addRow(new Object[]{normalizeIdForModel(e.getId()), e.getQuantidade(), e.getLocalTanque(), e.getLocalEndereco(), e.getLocalFabricacao(), dv, e.getTipoEstoque()}); } } statusLabel.setText("Conectado | Estoques: " + estoquesModel.getRowCount()); } catch(Exception ex){ statusLabel.setText("Erro: " + ex.getMessage()); } } }.execute(); }

    private void carregarCustos(){ custosModel.setRowCount(0); new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return CustoService.list(0,50);} @Override protected void done(){ try{ String res = get(); String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items.length==0) items = JsonParser.extractJsonArrayItems(res,null); for(String it: items){ if (it==null||it.trim().isEmpty()) continue; Custo c = JsonParser.parseCusto(it); if (c != null) {
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
                                    if (c.getImposto() != null) c.setImposto(c.getImposto().replace(',', '.').replaceAll("[^0-9.\-]", ""));
                                    if (c.getFrete() != null) c.setFrete(c.getFrete().replace(',', '.').replaceAll("[^0-9.\-]", ""));
                                    if (c.getSeguro() != null) c.setSeguro(c.getSeguro().replace(',', '.').replaceAll("[^0-9.\-]", ""));
                                    if (c.getCustoVariavel() != null) c.setCustoVariavel(c.getCustoVariavel().replace(',', '.').replaceAll("[^0-9.\-]", ""));
                                    if (c.getCustoFixo() != null) c.setCustoFixo(c.getCustoFixo().replace(',', '.').replaceAll("[^0-9.\-]", ""));
                                    if (c.getMargemLucro() != null) c.setMargemLucro(c.getMargemLucro().replace(',', '.').replaceAll("[^0-9.\-]", ""));
                                } catch (Exception ex) {
                                    System.err.println("DEBUG carregarCustos: falha ao normalizar numeros: " + ex.getMessage());
                                }

                                custosModel.addRow(new Object[]{normalizeIdForModel(c.getId()), c.getImposto(), c.getFrete(), c.getSeguro(), c.getCustoVariavel(), c.getCustoFixo(), c.getMargemLucro(), c.getTipoCusto()});
                            } else {
                                System.err.println("DEBUG carregarCustos: parseCusto retornou null para item: " + it);
                            }
                     }
                     statusLabel.setText("Conectado | Custos: " + custosModel.getRowCount());
                 } catch(Exception ex){ statusLabel.setText("Erro: " + ex.getMessage()); }
