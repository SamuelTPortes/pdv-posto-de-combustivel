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

        estoquesModel = new DefaultTableModel(new String[]{"ID", "Quantidade", "Tanque", "Endereço", "Fabricação", "Validade"}, 0);
        estoquesTable = new JTable(estoquesModel);
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

        custosModel = new DefaultTableModel(new String[]{"ID","Imposto","Frete","Seguro","Custo Var","Custo Fixo","Margem"}, 0);
        custosTable = new JTable(custosModel);
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
                            id,
                            p.getNomeCompleto() != null ? p.getNomeCompleto() : "",
                            p.getCpfCnpj() != null ? p.getCpfCnpj() : "",
                            p.getNumeroCtps() != null ? p.getNumeroCtps() : "",
                            p.getDataNascimento() != null ? p.getDataNascimento() : ""
                        });
                    }

                    statusLabel.setText("Conectado | Pessoas: " + pessoasModel.getRowCount());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    statusLabel.setText("Erro: operação interrompida");
                    JOptionPane.showMessageDialog(MainFrame.this, "Operação interrompida ao carregar pessoas.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
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
                                    p.getId(),
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
                                c.getId(),
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
                try { String res = get(); String[] items = JsonParser.extractJsonArrayItems(res, "content"); if (items.length==0) items = JsonParser.extractJsonArrayItems(res,null);
                    for(String it: items){ if (it==null||it.trim().isEmpty()) continue; Preco p = JsonParser.parsePreco(it); if (p!=null){ String date = JsonParser.formatDate(p.getDataAlteracao()); String time = JsonParser.formatTime(p.getHoraAlteracao()); precosModel.addRow(new Object[]{p.getId(), p.getValor(), date, time, p.getTipoPreco()}); }}
                    statusLabel.setText("Conectado | Preços: " + precosModel.getRowCount());
                } catch(Exception e){ statusLabel.setText("Erro: " + e.getMessage()); }
            }
        }.execute();
    }

    private void carregarEstoques(){ estoquesModel.setRowCount(0); new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return EstoqueService.list(0,50);} @Override protected void done(){ try{ String res = get(); String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items.length==0) items = JsonParser.extractJsonArrayItems(res,null); for(String it: items){ if (it==null||it.trim().isEmpty()) continue; Estoque e = JsonParser.parseEstoque(it); if (e!=null) { String dv = JsonParser.formatDate(e.getDataValidade()); estoquesModel.addRow(new Object[]{e.getId(), e.getQuantidade(), e.getLocalTanque(), e.getLocalEndereco(), e.getLocalFabricacao(), dv}); } } statusLabel.setText("Conectado | Estoques: " + estoquesModel.getRowCount()); } catch(Exception ex){ statusLabel.setText("Erro: " + ex.getMessage()); } } }.execute(); }

    private void carregarCustos(){ custosModel.setRowCount(0); new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return CustoService.list(0,50);} @Override protected void done(){ try{ String res = get(); String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items.length==0) items = JsonParser.extractJsonArrayItems(res,null); for(String it: items){ if (it==null||it.trim().isEmpty()) continue; Custo c = JsonParser.parseCusto(it); if (c!=null) custosModel.addRow(new Object[]{c.getId(), c.getImposto(), c.getFrete(), c.getSeguro(), c.getCustoVariavel(), c.getCustoFixo(), c.getMargemLucro()}); } statusLabel.setText("Conectado | Custos: " + custosModel.getRowCount()); } catch(Exception ex){ statusLabel.setText("Erro: " + ex.getMessage()); } } }.execute(); }

    private void adicionarPessoa() {
        Pessoa pessoa = CrudDialog.showPessoaDialog(this, null, "Adicionar Nova Pessoa");
        System.out.println("DEBUG: showPessoaDialog retornou: " + pessoa);

        if (pessoa == null) {
            System.out.println("DEBUG: usuário cancelou ou diálogo retornou null. Abortando.");
            return;
        }

        // opcional: desabilitar botão adicionar aqui, se houver referência
        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                System.out.println("DEBUG: iniciando PessoaService.create...");
                String resp = PessoaService.create(pessoa);
                System.out.println("DEBUG: PessoaService.create retornou: " + resp);
                return resp;
            }

            @Override
            protected void done() {
                try {
                    String response = get(); // pode lançar ExecutionException se doInBackground falhou
                    // Verifique response para decidir sucesso/erro
                    if (response == null || response.isBlank()) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Pessoa adicionada, mas resposta vazia do serviço.",
                                "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Pessoa adicionada com sucesso!",
                                "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    }
                    carregarPessoas();
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    ie.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Operação interrompida.",
                            "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Erro inesperado: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                } finally {
                    // opcional: reabilitar botão adicionar aqui
                }
            }
        }.execute();
    }

    private void editarPessoa() {
        int selectedRow = pessoasTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) pessoasModel.getValueAt(selectedRow, 0);
        String nome = (String) pessoasModel.getValueAt(selectedRow, 1);
        String cpf = (String) pessoasModel.getValueAt(selectedRow, 2);

        Pessoa pessoaAnterior = new Pessoa();
        pessoaAnterior.setId(id);
        pessoaAnterior.setNomeCompleto(nome);
        pessoaAnterior.setCpfCnpj(cpf);

        Pessoa pessoaEditada = CrudDialog.showPessoaDialog(this, pessoaAnterior, "Editar Pessoa");

        if (pessoaEditada != null) {
            new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    return PessoaService.update(id, pessoaEditada);
                }

                @Override
                protected void done() {
                    try {
                        String response = get();
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Pessoa atualizada!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarPessoas();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Erro: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        }
    }

    private void deletarPessoa() {
        int selectedRow = pessoasTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma pessoa.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) pessoasModel.getValueAt(selectedRow, 0);
        String nome = (String) pessoasModel.getValueAt(selectedRow, 1);

        if (CrudDialog.showConfirmDeleteDialog(this, nome)) {
            new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    return PessoaService.delete(id);
                }

                @Override
                protected void done() {
                    try {
                        String response = get();
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Pessoa deletada!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarPessoas();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Erro: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        }
    }

    private void adicionarProduto() {
        Produto produto = CrudDialog.showProdutoDialog(this, null, "Adicionar Novo Produto");

        if (produto != null) {
            new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    return CrudService.createProduto(produto);
                }

                @Override
                protected void done() {
                    try {
                        String response = get();
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Produto adicionado!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarProdutos();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Erro: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        }
    }

    private void editarProduto() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) produtosModel.getValueAt(selectedRow, 0);
        String nome = (String) produtosModel.getValueAt(selectedRow, 1);
        String ref = (String) produtosModel.getValueAt(selectedRow, 2);
        String forn = (String) produtosModel.getValueAt(selectedRow, 3);
        String marca = (String) produtosModel.getValueAt(selectedRow, 4);
        String tipo = (String) produtosModel.getValueAt(selectedRow, 5);

        Produto produtoAnterior = new Produto();
        produtoAnterior.setId(id);
        produtoAnterior.setNome(nome);
        produtoAnterior.setReferencia(ref);
        produtoAnterior.setFornecedor(forn);
        produtoAnterior.setMarca(marca);
        produtoAnterior.setTipoProduto(tipo);

        Produto produtoEditado = CrudDialog.showProdutoDialog(this, produtoAnterior, "Editar Produto");

        if (produtoEditado != null) {
            new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    return CrudService.updateProduto(id, produtoEditado);
                }

                @Override
                protected void done() {
                    try {
                        String response = get();
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Produto atualizado!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarProdutos();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Erro: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        }
    }

    private void deletarProduto() {
        int selectedRow = produtosTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um produto.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Long id = (Long) produtosModel.getValueAt(selectedRow, 0);
        String nome = (String) produtosModel.getValueAt(selectedRow, 1);

        if (CrudDialog.showConfirmDeleteDialog(this, nome)) {
            new SwingWorker<String, Void>() {
                @Override
                protected String doInBackground() throws Exception {
                    return CrudService.deleteProduto(id);
                }

                @Override
                protected void done() {
                    try {
                        String response = get();
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Produto deletado!",
                            "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarProdutos();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Erro: " + e.getMessage(),
                            "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }.execute();
        }
    }

    private void adicionarContato() {
        com.br.pdvpostocombustivelfrontend.frontend.model.Contato contato = CrudDialog.showContatoDialog(this, null, "Adicionar Contato");
        if (contato == null) return;

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.create(contato);
            }

            @Override
            protected void done() {
                try {
                    String res = get();
                    JOptionPane.showMessageDialog(MainFrame.this, "Contato criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarContatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void editarContato() {
        int selectedRow = contatosTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Selecione um contato.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Long id = (Long) contatosModel.getValueAt(selectedRow, 0);
        String endereco = (String) contatosModel.getValueAt(selectedRow, 1);
        String email = (String) contatosModel.getValueAt(selectedRow, 2);
        String telefone = (String) contatosModel.getValueAt(selectedRow, 3);

        com.br.pdvpostocombustivelfrontend.frontend.model.Contato c = new com.br.pdvpostocombustivelfrontend.frontend.model.Contato();
        c.setId(id); c.setEndereco(endereco); c.setEmail(email); c.setTelefone(telefone);

        com.br.pdvpostocombustivelfrontend.frontend.model.Contato updated = CrudDialog.showContatoDialog(this, c, "Editar Contato");
        if (updated == null) return;

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.update(id, updated);
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(MainFrame.this, "Contato atualizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarContatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void deletarContato() {
        int selectedRow = contatosTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Selecione um contato.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Long id = (Long) contatosModel.getValueAt(selectedRow, 0);
        String nome = (String) contatosModel.getValueAt(selectedRow, 1);
        if (!CrudDialog.showConfirmDeleteDialog(this, nome)) return;

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return com.br.pdvpostocombustivelfrontend.frontend.service.ContatoService.delete(id);
            }

            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(MainFrame.this, "Contato deletado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    carregarContatos();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    // ==================== PREÇOS CRUD ====================
    private void adicionarPreco(){ Preco p = CrudDialog.showPrecoDialog(this,null,"Adicionar Preço"); if (p==null) return; new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return PrecoService.create(p);} @Override protected void done(){ try{ String resp = get();
                    if (resp == null || resp.isBlank()) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Resposta vazia do serviço ao criar preço.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else if (JsonParser.isError(resp)) {
                        String msg = JsonParser.extractJsonValue(resp, "message"); if (msg == null) msg = JsonParser.extractJsonValue(resp, "mensagem"); if (msg == null) msg = resp;
                        JOptionPane.showMessageDialog(MainFrame.this, "Erro criando preço: " + msg, "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        com.br.pdvpostocombustivelfrontend.frontend.model.Preco created = JsonParser.parsePreco(resp);
                        if (created != null) {
                            String disp = "Preço criado: " + (created.getValor()!=null?created.getValor():"") + " em " + (JsonParser.formatDate(created.getDataAlteracao())!=null?JsonParser.formatDate(created.getDataAlteracao()):"") + " " + (JsonParser.formatTime(created.getHoraAlteracao())!=null?JsonParser.formatTime(created.getHoraAlteracao()):"");
                            JOptionPane.showMessageDialog(MainFrame.this, disp, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainFrame.this,"Preço criado com sucesso!\nResposta: " + resp,"Sucesso",JOptionPane.INFORMATION_MESSAGE);
                        }
                        carregarPrecos();
                    }
                }catch(Exception e){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} } }.execute(); }

    private void editarPreco() {
        int selectedRow = precosTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um preço.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // read values from table safely
        Object idObj = precosModel.getValueAt(selectedRow, 0);
        final Long id;
        if (idObj instanceof Long) id = (Long) idObj;
        else if (idObj instanceof Number) id = ((Number) idObj).longValue();
        else { try { id = Long.parseLong(String.valueOf(idObj)); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE); return; } }

        String valor = String.valueOf(precosModel.getValueAt(selectedRow, 1));
        String data = String.valueOf(precosModel.getValueAt(selectedRow, 2));
        String hora = String.valueOf(precosModel.getValueAt(selectedRow, 3));
        String tipo = String.valueOf(precosModel.getValueAt(selectedRow, 4));

        Preco prev = new Preco();
        prev.setId(id);
        prev.setValor(valor);
        prev.setDataAlteracao(data);
        prev.setHoraAlteracao(hora);
        prev.setTipoPreco(tipo);

        Preco updated = CrudDialog.showPrecoDialog(this, prev, "Editar Preço");
        if (updated == null) return;

        new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                return PrecoService.update(id, updated);
            }

            @Override
            protected void done() {
                try {
                    get();
                    carregarPrecos();
                    JOptionPane.showMessageDialog(MainFrame.this, "Preço atualizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    private void deletarPreco() {
        int selectedRow = precosTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Selecione um preço.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = precosModel.getValueAt(selectedRow, 0);
        Long id;
        try { if (idObj instanceof Number) id = ((Number)idObj).longValue(); else id = Long.parseLong(String.valueOf(idObj)); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Preço id="+id)) return;
        new SwingWorker<String, Void>(){
            @Override protected String doInBackground() throws Exception { return PrecoService.delete(id); }
            @Override protected void done(){ try{ get(); carregarPrecos(); JOptionPane.showMessageDialog(MainFrame.this,"Preço deletado!","Sucesso",JOptionPane.INFORMATION_MESSAGE);}catch(Exception e){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} }
        }.execute();
    }

    // ==================== ESTOQUES CRUD ====================
    private void adicionarEstoque() {
        Estoque e = CrudDialog.showEstoqueDialog(this,null,"Adicionar Estoque"); if (e==null) return; new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return EstoqueService.create(e);} @Override protected void done(){ try{ String resp = get();
                    if (resp == null || resp.isBlank()) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Resposta vazia do serviço ao criar estoque.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else if (JsonParser.isError(resp)) {
                        String msg = JsonParser.extractJsonValue(resp, "message"); if (msg == null) msg = JsonParser.extractJsonValue(resp, "mensagem"); if (msg == null) msg = resp;
                        JOptionPane.showMessageDialog(MainFrame.this, "Erro criando estoque: " + msg, "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        com.br.pdvpostocombustivelfrontend.frontend.model.Estoque created = JsonParser.parseEstoque(resp);
                        if (created != null) {
                            String disp = "Estoque criado: qtd=" + (created.getQuantidade()!=null?created.getQuantidade():"") + " tanque=" + (created.getLocalTanque()!=null?created.getLocalTanque():"") + " validade=" + (JsonParser.formatDate(created.getDataValidade())!=null?JsonParser.formatDate(created.getDataValidade()):"");
                            JOptionPane.showMessageDialog(MainFrame.this, disp, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainFrame.this,"Estoque criado com sucesso!\nResposta: " + resp,"Sucesso",JOptionPane.INFORMATION_MESSAGE);
                        }
                        carregarEstoques();
                    }
                }catch(Exception ex){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} } }.execute(); }

    private void editarEstoque() {
        int selectedRow = estoquesTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Selecione um estoque.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = estoquesModel.getValueAt(selectedRow, 0);
        final Long id;
        try { if (idObj instanceof Number) id = ((Number)idObj).longValue(); else id = Long.parseLong(String.valueOf(idObj)); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE); return; }

        String quantidade = String.valueOf(estoquesModel.getValueAt(selectedRow,1));
        String tanque = String.valueOf(estoquesModel.getValueAt(selectedRow,2));
        String endereco = String.valueOf(estoquesModel.getValueAt(selectedRow,3));
        String fabrica = String.valueOf(estoquesModel.getValueAt(selectedRow,4));
        String validade = String.valueOf(estoquesModel.getValueAt(selectedRow,5));

        Estoque prev = new Estoque(); prev.setId(id); prev.setQuantidade(quantidade); prev.setLocalTanque(tanque); prev.setLocalEndereco(endereco); prev.setLocalFabricacao(fabrica); prev.setDataValidade(validade);
        Estoque updated = CrudDialog.showEstoqueDialog(this, prev, "Editar Estoque"); if (updated==null) return;

        new SwingWorker<String,Void>(){
            @Override protected String doInBackground() throws Exception { return EstoqueService.update(id, updated); }
            @Override protected void done(){ try{ get(); carregarEstoques(); JOptionPane.showMessageDialog(MainFrame.this,"Estoque atualizado!","Sucesso",JOptionPane.INFORMATION_MESSAGE);}catch(Exception e){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} }
        }.execute();
    }

    private void deletarEstoque() {
        int selectedRow = estoquesTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Selecione um estoque.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = estoquesModel.getValueAt(selectedRow, 0);
        Long id; try { if (idObj instanceof Number) id = ((Number)idObj).longValue(); else id = Long.parseLong(String.valueOf(idObj)); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Estoque id="+id)) return;
        new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return EstoqueService.delete(id);} @Override protected void done(){ try{ get(); carregarEstoques(); JOptionPane.showMessageDialog(MainFrame.this,"Estoque deletado!","Sucesso",JOptionPane.INFORMATION_MESSAGE);}catch(Exception e){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} } }.execute();
    }

    // ==================== CUSTOS CRUD ====================
    private void adicionarCusto(){ Custo c = CrudDialog.showCustoDialog(this,null,"Adicionar Custo"); if (c==null) return; new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return CustoService.create(c);} @Override protected void done(){ try{ String resp = get();
                    if (resp == null || resp.isBlank()) {
                        JOptionPane.showMessageDialog(MainFrame.this, "Resposta vazia do serviço ao criar custo.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    } else if (JsonParser.isError(resp)) {
                        String msg = JsonParser.extractJsonValue(resp, "message"); if (msg == null) msg = JsonParser.extractJsonValue(resp, "mensagem"); if (msg == null) msg = resp;
                        JOptionPane.showMessageDialog(MainFrame.this, "Erro criando custo: " + msg, "Erro", JOptionPane.ERROR_MESSAGE);
                    } else {
                        com.br.pdvpostocombustivelfrontend.frontend.model.Custo created = JsonParser.parseCusto(resp);
                        if (created != null) {
                            String disp = "Custo criado: imposto=" + (created.getImposto()!=null?created.getImposto():"") + " margem=" + (created.getMargemLucro()!=null?created.getMargemLucro():"");
                            JOptionPane.showMessageDialog(MainFrame.this, disp, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(MainFrame.this,"Custo criado com sucesso!\nResposta: " + resp,"Sucesso",JOptionPane.INFORMATION_MESSAGE);
                        }
                        carregarCustos();
                    }
                }catch(Exception ex){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+ex.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} } }.execute(); }

    private void editarCusto() {
        int selectedRow = custosTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Selecione um custo.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = custosModel.getValueAt(selectedRow, 0);
        final Long id; try { if (idObj instanceof Number) id = ((Number)idObj).longValue(); else id = Long.parseLong(String.valueOf(idObj)); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE); return; }

        String imposto = String.valueOf(custosModel.getValueAt(selectedRow,1));
        String frete = String.valueOf(custosModel.getValueAt(selectedRow,2));
        String seguro = String.valueOf(custosModel.getValueAt(selectedRow,3));
        String custoVar = String.valueOf(custosModel.getValueAt(selectedRow,4));
        String custoFixo = String.valueOf(custosModel.getValueAt(selectedRow,5));
        String margem = String.valueOf(custosModel.getValueAt(selectedRow,6));

        com.br.pdvpostocombustivelfrontend.frontend.model.Custo prev = new com.br.pdvpostocombustivelfrontend.frontend.model.Custo();
        prev.setId(id); prev.setImposto(imposto); prev.setFrete(frete); prev.setSeguro(seguro); prev.setCustoVariavel(custoVar); prev.setCustoFixo(custoFixo); prev.setMargemLucro(margem);
        com.br.pdvpostocombustivelfrontend.frontend.model.Custo updated = CrudDialog.showCustoDialog(this, prev, "Editar Custo"); if (updated==null) return;

        new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return CustoService.update(id, updated);} @Override protected void done(){ try{ get(); carregarCustos(); JOptionPane.showMessageDialog(MainFrame.this,"Custo atualizado!","Sucesso",JOptionPane.INFORMATION_MESSAGE);}catch(Exception e){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} } }.execute();
    }

    private void deletarCusto() {
        int selectedRow = custosTable.getSelectedRow();
        if (selectedRow < 0) { JOptionPane.showMessageDialog(this, "Selecione um custo.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        Object idObj = custosModel.getValueAt(selectedRow, 0);
        Long id; try { if (idObj instanceof Number) id = ((Number)idObj).longValue(); else id = Long.parseLong(String.valueOf(idObj)); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID inválido.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Custo id="+id)) return;
        new SwingWorker<String,Void>(){ @Override protected String doInBackground() throws Exception { return CustoService.delete(id);} @Override protected void done(){ try{ get(); carregarCustos(); JOptionPane.showMessageDialog(MainFrame.this,"Custo deletado!","Sucesso",JOptionPane.INFORMATION_MESSAGE);}catch(Exception e){ JOptionPane.showMessageDialog(MainFrame.this,"Erro: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);} } }.execute();
    }

    private void startClock() {
        clockTimer = new Timer(1000, e -> updateClock());
        clockTimer.start();
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        statusLabel.setText("Conectado | " + sdf.format(new Date()));
    }

    private void logout() {
        if (JOptionPane.showConfirmDialog(this,
            "Deseja sair?",
            "Confirmar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            clockTimer.stop();
            dispose();

            SwingUtilities.invokeLater(() -> {
                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            });
        }
    }
}

