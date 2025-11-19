package com.br.pdvpostocombustivelfrontend.frontend.ui;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Acesso;
import com.br.pdvpostocombustivelfrontend.frontend.model.Custo;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginResponse;
import com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa;
import com.br.pdvpostocombustivelfrontend.frontend.model.Preco;
import com.br.pdvpostocombustivelfrontend.frontend.model.Produto;
import com.br.pdvpostocombustivelfrontend.frontend.model.Estoque;
import com.br.pdvpostocombustivelfrontend.frontend.model.Venda;
import com.br.pdvpostocombustivelfrontend.frontend.service.*;
import com.br.pdvpostocombustivelfrontend.frontend.util.BoletoGerador;
import com.br.pdvpostocombustivelfrontend.frontend.util.JsonParser;
import com.br.pdvpostocombustivelfrontend.frontend.ui.CrudDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MainFrame consolidado e corrigido.
 */
public class MainFrame extends JFrame {
    private final LoginResponse loginResponse;
    private JLabel statusLabel;
    private JTabbedPane tabbedPane;

    // models / tables as instance fields to be reused
    private DefaultTableModel pessoasModel;
    private DefaultTableModel produtosModel;
    private DefaultTableModel precosModel;
    private DefaultTableModel estoquesModel;
    private DefaultTableModel custosModel;
    private DefaultTableModel acessosModel;
    private DefaultTableModel vendasModel;

    public MainFrame(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
        initComponents();
        startClock();
        carregarAcessos();
    }

    // ---------------- UI creation ----------------
    private void initComponents() {
        setTitle(AppConfig.APP_TITLE + " - Sistema Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        add(createTopBar(), BorderLayout.NORTH);

        // inicializa tabbedPane antes de adicionar abas
        tabbedPane = new JTabbedPane();

        // usar nomes de métodos conforme implementados (plural)
        tabbedPane.addTab("Pessoas", createPessoasPanel());
        tabbedPane.addTab("Produtos", createProdutosPanel());
        tabbedPane.addTab("Vendas", createVendasPanel());
        tabbedPane.addTab("Acessos", createAcessosPanel());
        tabbedPane.addTab("Preços", createPrecosPanel());
        tabbedPane.addTab("Custos", createCustosPanel());
        tabbedPane.addTab("Estoque", createEstoquesPanel());

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

        JLabel userLabel = new JLabel("👤 " + loginResponse.getUsuario() + " | " + loginResponse.getTipoAcesso().getDescricao());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(Color.WHITE);

        JButton logoutButton = new JButton("Sair");
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setText("Deslogar");
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
        JButton addButton = new JButton("+ Adicionar"); addButton.addActionListener(e -> adicionarPessoa());
        JButton editButton = new JButton("✎ Editar"); editButton.addActionListener(e -> editarPessoa());
        JButton deleteButton = new JButton("🗑 Deletar"); deleteButton.addActionListener(e -> deletarPessoa());
        JButton refreshButton = new JButton("🔄 Atualizar"); refreshButton.addActionListener(e -> carregarPessoas());
        buttonPanel.add(addButton); buttonPanel.add(editButton); buttonPanel.add(deleteButton); buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.NORTH);

        pessoasModel = new DefaultTableModel(new String[]{"ID","Nome","CPF","CTPS","Data Nasc."},0);
        JTable tabela = new JTable(pessoasModel); tabela.setName("pessoasTable");
        panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregarPessoas();
        return panel;
    }

    private JPanel createProdutosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10)); panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); panel.setBackground(new Color(236,240,241));
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("+ Adicionar"); add.addActionListener(e -> adicionarProduto());
        JButton edit = new JButton("✎ Editar"); edit.addActionListener(e -> editarProduto());
        JButton del = new JButton("🗑 Deletar"); del.addActionListener(e -> deletarProduto());
        JButton ref = new JButton("🔄 Atualizar"); ref.addActionListener(e -> carregarProdutos());
        bp.add(add); bp.add(edit); bp.add(del); bp.add(ref);
        panel.add(bp, BorderLayout.NORTH);
        produtosModel = new DefaultTableModel(new String[]{"ID","Nome","Ref","Fornecedor","Marca","Tipo"},0);
        JTable tabela = new JTable(produtosModel); tabela.setName("produtosTable"); panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregarProdutos(); return panel;
    }

    private JPanel createPrecosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10)); panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); panel.setBackground(new Color(236,240,241));
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("+ Adicionar"); add.addActionListener(e -> adicionarPreco());
        JButton edit = new JButton("✎ Editar"); edit.addActionListener(e -> editarPreco());
        JButton del = new JButton("🗑 Deletar"); del.addActionListener(e -> deletarPreco());
        JButton ref = new JButton("🔄 Atualizar"); ref.addActionListener(e -> carregarPrecos());
        bp.add(add); bp.add(edit); bp.add(del); bp.add(ref);
        panel.add(bp, BorderLayout.NORTH);
        precosModel = new DefaultTableModel(new String[]{"ID","Valor","Data","Hora","Tipo"},0);
        JTable tabela = new JTable(precosModel); tabela.setName("precosTable"); panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregarPrecos(); return panel;
    }

    private JPanel createEstoquesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10)); panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); panel.setBackground(new Color(236,240,241));
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("+ Adicionar"); add.addActionListener(e -> adicionarEstoque());
        JButton edit = new JButton("✎ Editar"); edit.addActionListener(e -> editarEstoque());
        JButton del = new JButton("🗑 Deletar"); del.addActionListener(e -> deletarEstoque());
        JButton ref = new JButton("🔄 Atualizar"); ref.addActionListener(e -> carregarEstoques());
        bp.add(add); bp.add(edit); bp.add(del); bp.add(ref);
        panel.add(bp, BorderLayout.NORTH);
        estoquesModel = new DefaultTableModel(new String[]{"ID","Quantidade","Tanque","Endereço","Fabricação","Validade","Tipo"},0);
        JTable tabela = new JTable(estoquesModel); tabela.setName("estoquesTable"); panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregarEstoques(); return panel;
    }

    private JPanel createCustosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10)); panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); panel.setBackground(new Color(236,240,241));
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("+ Adicionar"); add.addActionListener(e -> adicionarCusto());
        JButton edit = new JButton("✎ Editar"); edit.addActionListener(e -> editarCusto());
        JButton del = new JButton("🗑 Deletar"); del.addActionListener(e -> deletarCusto());
        JButton ref = new JButton("🔄 Atualizar"); ref.addActionListener(e -> carregarCustos());
        bp.add(add); bp.add(edit); bp.add(del); bp.add(ref);
        panel.add(bp, BorderLayout.NORTH);
        custosModel = new DefaultTableModel(new String[]{"ID","Imposto","Frete","Seguro","Custo Var","Custo Fixo","Margem","Tipo"},0);
        JTable tabela = new JTable(custosModel); tabela.setName("custosTable"); panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregarCustos(); return panel;
    }

    private JPanel createVendasPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10)); panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); panel.setBackground(new Color(236,240,241));
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("+ Adicionar"); add.addActionListener(e -> adicionarVenda());
        JButton edit = new JButton("✎ Editar"); edit.addActionListener(e -> editarVenda());
        JButton del = new JButton("🗑 Deletar"); del.addActionListener(e -> deletarVenda());
        JButton boleto = new JButton("📄 Gerar Boleto"); boleto.addActionListener(e -> gerarBoleto());
        JButton ref = new JButton("🔄 Atualizar"); ref.addActionListener(e -> carregarVendas());
        bp.add(add); bp.add(edit); bp.add(del); bp.add(boleto); bp.add(ref);
        panel.add(bp, BorderLayout.NORTH);
        vendasModel = new DefaultTableModel(new String[]{"ID","Descrição","Quantidade","V. Unitário","V. Total","Data","Hora","Combustível","Tipo Preço"},0);
        JTable tabela = new JTable(vendasModel); tabela.setName("tabelaVendas"); panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregarVendas(); return panel;
    }

    private JPanel createAcessosPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10)); panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10)); panel.setBackground(new Color(236,240,241));
        JPanel bp = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("+ Adicionar"); add.addActionListener(e -> adicionarAcesso());
        JButton edit = new JButton("✎ Editar"); edit.addActionListener(e -> editarAcesso());
        JButton del = new JButton("🗑 Deletar"); del.addActionListener(e -> deletarAcesso());
        JButton ref = new JButton("🔄 Atualizar"); ref.addActionListener(e -> carregarAcessos());
        bp.add(add); bp.add(edit); bp.add(del); bp.add(ref);
        panel.add(bp, BorderLayout.NORTH);
        acessosModel = new DefaultTableModel(new String[]{"ID","Usuário","Data Acesso","Hora Acesso","Tipo Acesso"},0);
        JTable tabela = new JTable(acessosModel); tabela.setName("tabelaAcessos"); panel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        carregarAcessos(); return panel;
    }

    private JPanel createBottomBar() {
        JPanel bottom = new JPanel(new BorderLayout()); bottom.setBorder(BorderFactory.createLineBorder(new Color(189,195,199))); bottom.setPreferredSize(new Dimension(0,30));
        statusLabel = new JLabel("Pronto"); statusLabel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10)); bottom.add(statusLabel, BorderLayout.WEST);
        return bottom;
    }

    // ----------------- CRUD handlers -----------------
    private void adicionarPessoa() {
        try {
            Pessoa novo = CrudDialog.showPessoaDialog(this, null, "Adicionar Pessoa");
            if (novo == null) return;
            String resp = PessoaService.create(novo);
            if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando pessoa: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Pessoa criada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar pessoa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally { carregarPessoas(); }
    }

    private void editarPessoa() {
        JTable t = findTableByName("pessoasTable"); if (t == null) return;
        int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione uma pessoa.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel);
        Object idObj = pessoasModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj);
        if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        Pessoa orig = new Pessoa(); orig.setId(id);
        orig.setNomeCompleto(stringValueForModel(pessoasModel.getValueAt(msel,1)));
        Pessoa edited = CrudDialog.showPessoaDialog(this, orig, "Editar Pessoa"); if (edited == null) return;
        try { String resp = PessoaService.update(edited.getId(), edited); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Pessoa atualizada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar pessoa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPessoas(); }
    }

    private void deletarPessoa() {
        JTable t = findTableByName("pessoasTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione uma pessoa.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = pessoasModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Pessoa id="+id)) return; try { String resp = PessoaService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Pessoa deletada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar pessoa: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPessoas(); }
    }

    private void adicionarProduto() {
        try { Produto novo = CrudDialog.showProdutoDialog(this, null, "Adicionar Produto"); if (novo == null) return; String resp = CrudService.createProduto(novo); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando produto: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Produto criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarProdutos(); }
    }

    private void editarProduto() {
        JTable t = findTableByName("produtosTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um produto.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = produtosModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        Produto orig = new Produto(); orig.setId(id); orig.setNome(stringValueForModel(produtosModel.getValueAt(msel,1)));
        Produto edited = CrudDialog.showProdutoDialog(this, orig, "Editar Produto"); if (edited == null) return; try { String resp = CrudService.updateProduto(edited.getId(), edited); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Produto atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarProdutos(); }
    }

    private void deletarProduto() {
        JTable t = findTableByName("produtosTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um produto.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = produtosModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Produto id="+id)) return; try { String resp = CrudService.deleteProduto(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Produto deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar produto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarProdutos(); }
    }


    private void adicionarPreco() {
        try { Preco novo = CrudDialog.showPrecoDialog(this, null, "Adicionar Preço"); if (novo == null) return; String resp = PrecoService.create(novo); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando preço: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Preço criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar preço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPrecos(); }
    }

    private void editarPreco() {
        JTable t = findTableByName("precosTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um preço.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = precosModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        Preco orig = new Preco(); orig.setId(id); Preco edited = CrudDialog.showPrecoDialog(this, orig, "Editar Preço"); if (edited == null) return; try { String resp = PrecoService.update(edited.getId(), edited); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Preço atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar preço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPrecos(); }
    }

    private void deletarPreco() {
        JTable t = findTableByName("precosTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um preço.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = precosModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Preço id="+id)) return; try { String resp = PrecoService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Preço deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar preço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarPrecos(); }
    }

    private void adicionarEstoque() {
        try { Estoque novo = CrudDialog.showEstoqueDialog(this, null, "Adicionar Estoque"); if (novo == null) return; String resp = EstoqueService.create(novo); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando estoque: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Estoque criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarEstoques(); }
    }

    private void editarEstoque() {
        JTable t = findTableByName("estoquesTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um estoque.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = estoquesModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        Estoque orig = new Estoque(); orig.setId(id); Estoque edited = CrudDialog.showEstoqueDialog(this, orig, "Editar Estoque"); if (edited == null) return; try { String resp = EstoqueService.update(edited.getId(), edited); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Estoque atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarEstoques(); }
    }

    private void deletarEstoque() {
        JTable t = findTableByName("estoquesTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um estoque.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = estoquesModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Estoque id="+id)) return; try { String resp = EstoqueService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Estoque deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar estoque: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarEstoques(); }
    }

    private void adicionarCusto() {
        try { Custo novo = CrudDialog.showCustoDialog(this, null, "Adicionar Custo"); if (novo == null) return; String resp = CustoService.create(novo); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando custo: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Custo criado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar custo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarCustos(); }
    }

    private void editarCusto() {
        JTable t = findTableByName("custosTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um custo.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = custosModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        Custo orig = new Custo(); orig.setId(id); Custo edited = CrudDialog.showCustoDialog(this, orig, "Editar Custo"); if (edited == null) return; try { String resp = CustoService.update(edited.getId(), edited); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Custo atualizado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar custo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarCustos(); }
    }

    private void deletarCusto() {
        JTable t = findTableByName("custosTable"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione um custo.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = custosModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Custo id="+id)) return; try { String resp = CustoService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Custo deletado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar custo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarCustos(); }
    }

    // ----------------- Acesso handlers -----------------
    private void carregarAcessos() {
        if (acessosModel == null) return;
        acessosModel.setRowCount(0);
        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() throws Exception { return AcessoService.list(0,50); }
            @Override protected void done() {
                try {
                    String res = get(); if (res == null || res.trim().isEmpty()) return;
                    String[] items = JsonParser.extractJsonArrayItems(res, "content"); if (items == null || items.length==0) items = JsonParser.extractJsonArrayItems(res, null); if (items==null) return;
                    for (String it: items) {
                        if (it==null||it.trim().isEmpty()) continue;
                        Acesso a = JsonParser.parseAcesso(it);
                        String usuario = (a!=null)?a.getUsuario():JsonParser.extractJsonValue(it,"usuario");
                        Long id = (a!=null && a.getId()!=null)?a.getId():JsonParser.extractLongFallback(it,"id","acessoId","acesso_id","codigo","usuarioId");
                        if (id==null) { Long r = resolveAcessoIdByUsuario(usuario); if (r!=null) id=r; }
                        // Garante que o ID nunca fica vazio na tabela
                        if (id==null) { System.err.println("AVISO: Acesso sem ID encontrado: usuario="+usuario); continue; }
                        String date = JsonParser.formatDate(JsonParser.extractJsonValue(it,"dataAcesso"));
                        String time = JsonParser.formatTime(JsonParser.extractJsonValue(it,"horaAcesso"));
                        acessosModel.addRow(new Object[]{normalizeIdForModel(id), usuario, date, time, (a!=null?a.getTipoAcesso():null)});
                    }
                    if (statusLabel!=null) statusLabel.setText("Conectado | Acessos: "+acessosModel.getRowCount());
                } catch (Exception e) { System.err.println("Erro ao carregar acessos: "+e.getMessage()); e.printStackTrace(); }
            }
        }.execute();
    }

    private void adicionarAcesso() {
        Acesso novo = CrudDialog.showAcessoDialog(this, null, "Adicionar Acesso"); if (novo==null) return; try { String resp = AcessoService.create(novo); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando acesso: "+JsonParser.extractJsonValue(resp,"message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this,"Acesso criado.","Sucesso",JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this,"Erro ao criar acesso: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE); } finally { carregarAcessos(); }
    }

    private void editarAcesso() {
        JTable t = findTableByName("tabelaAcessos"); if (t==null) return; int sel = t.getSelectedRow(); if (sel==-1) { JOptionPane.showMessageDialog(this,"Selecione um acesso.","Aviso",JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel);
        Object idObj = acessosModel.getValueAt(msel,0);
        Long id = parseLongFromObject(idObj);

        if (id==null) {
            Object usuarioObj = acessosModel.getValueAt(msel,1);
            String usuario = usuarioObj!=null?usuarioObj.toString():null;
            id = resolveAcessoIdByUsuario(usuario);
            if (id!=null) acessosModel.setValueAt(normalizeIdForModel(id), msel,0);
        }

        if (id==null) {
            JOptionPane.showMessageDialog(this,"Erro: Item sem ID válido. Tente recarregar a lista.","Erro",JOptionPane.ERROR_MESSAGE);
            return;
        }

        final Long idFinal = id;
        new SwingWorker<String,Void>(){
            @Override protected String doInBackground() throws Exception { return AcessoService.getById(idFinal); }
            @Override protected void done() {
                try {
                    String json = get();
                    Acesso orig = null;
                    if (json!=null && !json.trim().isEmpty() && !JsonParser.isError(json)) orig = JsonParser.parseAcesso(json);
                    if (orig==null) orig=new Acesso();
                    if (orig.getId()==null) orig.setId(idFinal);
                    openEditDialogAndUpdate(orig, idFinal);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(MainFrame.this,"Erro ao carregar acesso: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
                    carregarAcessos();
                }
            }
        }.execute();
    }

    private void deletarAcesso() {
        JTable t = findTableByName("tabelaAcessos"); if (t==null) return; int sel = t.getSelectedRow(); if (sel==-1) { JOptionPane.showMessageDialog(this,"Selecione um acesso.","Aviso",JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel);
        Object idObj = acessosModel.getValueAt(msel,0);
        Long id = parseLongFromObject(idObj);

        if (id==null) {
            Object usuarioObj = acessosModel.getValueAt(msel,1);
            String usuario = usuarioObj!=null?usuarioObj.toString():null;
            id = resolveAcessoIdByUsuario(usuario);
            if (id!=null) acessosModel.setValueAt(normalizeIdForModel(id), msel,0);
        }

        if (id==null) {
            JOptionPane.showMessageDialog(this,"Erro: Item sem ID válido. Tente recarregar a lista.","Erro",JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!CrudDialog.showConfirmDeleteDialog(this,"Acesso id="+id)) return;
        try {
            String resp = AcessoService.delete(id);
            if (JsonParser.isError(resp))
                JOptionPane.showMessageDialog(this,"Erro ao deletar acesso: "+JsonParser.extractJsonValue(resp,"message"),"Erro",JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(this,"Acesso deletado.","Sucesso",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Erro ao deletar acesso: "+e.getMessage(),"Erro",JOptionPane.ERROR_MESSAGE);
        } finally {
            carregarAcessos();
        }
    }

    // ----------------- Helpers -----------------
    private JTable findTableByName(String name) {
        if (name==null) return null; if (tabbedPane==null) return null;
        for (Component c: tabbedPane.getComponents()) { JTable r = findTableRecursive(c, name); if (r!=null) return r; }
        return null;
    }
    private JTable findTableRecursive(Component comp, String name) {
        if (comp instanceof JTable) { JTable t=(JTable)comp; if (name.equals(t.getName())) return t; }
        if (comp instanceof Container) {
            for (Component c: ((Container)comp).getComponents()) { JTable r = findTableRecursive(c,name); if (r!=null) return r; }
        }
        return null;
    }

    private Long resolveAcessoIdByUsuario(String usuario) {
        if (usuario==null || usuario.trim().isEmpty()) return null;
        try {
            System.out.println("DEBUG: Resolvendo ID para usuario: " + usuario);
            String found = AcessoService.findByUsuario(usuario);
            if (found==null || found.trim().isEmpty()) {
                System.err.println("DEBUG: Nenhum resultado encontrado para usuario: " + usuario);
                return null;
            }
            if (JsonParser.isError(found)) {
                System.err.println("DEBUG: Erro na resposta: " + found);
                return null;
            }
            Long id = JsonParser.extractLongFallback(found, "id","acessoId","acesso_id","codigo");
            if (id!=null) {
                System.out.println("DEBUG: ID encontrado via extractLongFallback: " + id);
                return id;
            }
            Acesso p = JsonParser.parseAcesso(found);
            if (p!=null && p.getId()!=null) {
                System.out.println("DEBUG: ID encontrado via parseAcesso: " + p.getId());
                return p.getId();
            }
            Matcher m = Pattern.compile("\\\"id\\\"\\s*:\\s*(\\d+)").matcher(found);
            if (m.find()) {
                try { Long result = Long.parseLong(m.group(1)); return result; } catch (Exception ignored) {}
            }
            System.err.println("DEBUG: Não foi possível extrair ID de: " + found);
        } catch (Exception e) { System.err.println("DEBUG resolveAcessoIdByUsuario: "+e.getMessage()); e.printStackTrace(); }
        return null;
    }

    private void openEditDialogAndUpdate(Acesso original, Long id) {
        Acesso edited = CrudDialog.showAcessoDialog(this, original, original.getId()==null?"Adicionar Acesso":"Editar Acesso");
        if (edited==null) return;
        try {
            String resp;
            if (edited.getId()!=null) resp = AcessoService.update(edited.getId(), edited);
            else if (id!=null) { edited.setId(id); resp = AcessoService.update(id, edited); }
            else resp = AcessoService.create(edited);
            if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro ao salvar acesso: "+JsonParser.extractJsonValue(resp,"message"), "Erro", JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "Acesso salvo.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar acesso: "+e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); }
        finally { carregarAcessos(); }
    }

    private String stringValueForModel(Object cell) { return cell==null?null:cell.toString(); }
    private Long parseLongFromObject(Object o) {
        if (o==null) return null; if (o instanceof Number) return ((Number)o).longValue();
        String s=o.toString().trim(); if (s.isEmpty()) return null; String digits = s.replaceAll("[^0-9\\-]",""); if (digits.isEmpty()) return null;
        try { return Long.parseLong(digits); } catch (NumberFormatException ex) { try { double d = Double.parseDouble(s.replace(',','.')); return (long)d; } catch (Exception e) { return null; } }
    }
    private Object normalizeIdForModel(Object id) { if (id==null) return ""; if (id instanceof Number) return ((Number)id).longValue(); return id.toString(); }

    private void startClock() {
        try { final SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); Timer t = new Timer(1000, e -> { try { if (statusLabel!=null) statusLabel.setToolTipText(fmt.format(new Date())); } catch (Exception ignored) {} }); t.setInitialDelay(0); t.start(); } catch (Exception ignored) {}
    }

    private void carregarPessoas() {
        if (pessoasModel == null) return;
        pessoasModel.setRowCount(0);
        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() throws Exception { return PessoaService.list(0,50); }
            @Override protected void done() {
                try {
                    String response = get();
                    if (response == null || response.trim().isEmpty()) return;
                    if (JsonParser.isError(response)) return;
                    String[] items = JsonParser.extractJsonArrayItems(response, "content");
                    if (items == null || items.length==0) items = JsonParser.extractJsonArrayItems(response, null);
                    if (items == null) return;
                    for (String it: items) {
                        if (it==null || it.trim().isEmpty()) continue;
                        Pessoa p = JsonParser.parsePessoa(it);
                        if (p==null) continue;
                        pessoasModel.addRow(new Object[]{ normalizeIdForModel(p.getId()), p.getNomeCompleto()!=null?p.getNomeCompleto():"", p.getCpfCnpj()!=null?p.getCpfCnpj():"", p.getNumeroCtps()!=null?p.getNumeroCtps():"", p.getDataNascimento()!=null?p.getDataNascimento():"" });
                    }
                    if (statusLabel!=null) statusLabel.setText("Conectado | Pessoas: "+pessoasModel.getRowCount());
                } catch (Exception e) { System.err.println("Erro carregarPessoas: "+e.getMessage()); }
            }
        }.execute();
    }

    private void carregarProdutos() {
        if (produtosModel == null) return;
        produtosModel.setRowCount(0);
        new SwingWorker<String, Void>() {
            @Override protected String doInBackground() throws Exception { return CrudService.listProdutos(0,50); }
            @Override protected void done() {
                try {
                    String res = get(); if (res==null) return;
                    String[] items = JsonParser.extractJsonArrayItems(res, "content"); if (items==null||items.length==0) items = JsonParser.extractJsonArrayItems(res, null);
                    if (items==null) return;
                    for (String it: items) {
                        if (it==null||it.trim().isEmpty()) continue;
                        Produto p = JsonParser.parseProduto(it); if (p==null) continue;
                        produtosModel.addRow(new Object[]{normalizeIdForModel(p.getId()), p.getNome(), p.getReferencia(), p.getFornecedor(), p.getMarca(), p.getTipoProduto()});
                    }
                    if (statusLabel!=null) statusLabel.setText("Conectado | Produtos: "+produtosModel.getRowCount());
                } catch (Exception e) { System.err.println("Erro carregarProdutos: "+e.getMessage()); }
            }
        }.execute();
    }

    private void carregarPrecos() {
        if (precosModel==null) return; precosModel.setRowCount(0);
        new SwingWorker<String,Void>(){
            @Override protected String doInBackground() throws Exception { return PrecoService.list(0,50); }
            @Override protected void done() {
                try {
                    String res = get(); if (res==null) return;
                    String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items==null||items.length==0) items = JsonParser.extractJsonArrayItems(res, null);
                    if (items==null) return;
                    for (String it: items) {
                        if (it==null||it.trim().isEmpty()) continue;
                        Preco p = JsonParser.parsePreco(it); if (p==null) continue;
                        if (p.getId()==null) { Long tryId = JsonParser.extractLongFallback(it,"id","precoId","codigo","produtoId"); if (tryId!=null) p.setId(tryId); }
                        String date = JsonParser.formatDate(p.getDataAlteracao()); String time = JsonParser.formatTime(p.getHoraAlteracao());
                        precosModel.addRow(new Object[]{normalizeIdForModel(p.getId()), p.getValor(), date, time, p.getTipoPreco()});
                    }
                    if (statusLabel!=null) statusLabel.setText("Conectado | Preços: "+precosModel.getRowCount());
                } catch (Exception e) { System.err.println("Erro carregarPrecos: "+e.getMessage()); }
            }
        }.execute();
    }

    private void carregarEstoques() {
        if (estoquesModel==null) return; estoquesModel.setRowCount(0);
        new SwingWorker<String,Void>(){
            @Override protected String doInBackground() throws Exception { return EstoqueService.list(0,50); }
            @Override protected void done() {
                try { String res = get(); if (res==null) return; String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items==null||items.length==0) items = JsonParser.extractJsonArrayItems(res,null); if (items==null) return; for (String it: items) { if (it==null||it.trim().isEmpty()) continue; Estoque e = JsonParser.parseEstoque(it); if (e==null) continue; estoquesModel.addRow(new Object[]{normalizeIdForModel(e.getId()), e.getQuantidade(), e.getLocalTanque(), e.getLocalEndereco(), e.getLocalFabricacao(), JsonParser.formatDate(e.getDataValidade()), e.getTipoEstoque()}); } if (statusLabel!=null) statusLabel.setText("Conectado | Estoques: "+estoquesModel.getRowCount()); } catch (Exception ex) { System.err.println("Erro carregarEstoques: "+ex.getMessage()); }
            }
        }.execute();
    }

    private void carregarCustos() {
        if (custosModel==null) return; custosModel.setRowCount(0);
        new SwingWorker<String,Void>(){
            @Override protected String doInBackground() throws Exception { return CustoService.list(0,50); }
            @Override protected void done() {
                try { String res = get(); if (res==null) return; String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items==null||items.length==0) items = JsonParser.extractJsonArrayItems(res,null); if (items==null) return; for (String it: items) { if (it==null||it.trim().isEmpty()) continue; Custo c = JsonParser.parseCusto(it); if (c==null) continue; custosModel.addRow(new Object[]{normalizeIdForModel(c.getId()), c.getImposto(), c.getFrete(), c.getSeguro(), c.getCustoVariavel(), c.getCustoFixo(), c.getMargemLucro(), c.getTipoCusto()}); } if (statusLabel!=null) statusLabel.setText("Conectado | Custos: "+custosModel.getRowCount()); } catch (Exception ex) { System.err.println("Erro carregarCustos: "+ex.getMessage()); }
            }
        }.execute();
    }

    private void carregarVendas() {
        if (vendasModel==null) return; vendasModel.setRowCount(0);
        new SwingWorker<String,Void>(){
            @Override protected String doInBackground() throws Exception { return VendaService.list(0,50); }
            @Override protected void done() {
                try { String res = get(); if (res==null) return; String[] items = JsonParser.extractJsonArrayItems(res,"content"); if (items==null||items.length==0) items = JsonParser.extractJsonArrayItems(res,null); if (items==null) return; for (String it: items) { if (it==null||it.trim().isEmpty()) continue; Venda v = JsonParser.parseVenda(it); if (v==null) continue; vendasModel.addRow(new Object[]{normalizeIdForModel(v.getId()), v.getDescricaoProduto(), v.getQuantidade(), v.getValorUnitario(), v.getValorTotal(), v.getDataVenda(), v.getHoraVenda(), v.getTipoCombustivel(), v.getTipoPreco()}); } if (statusLabel!=null) statusLabel.setText("Conectado | Vendas: "+vendasModel.getRowCount()); } catch (Exception ex) { System.err.println("Erro carregarVendas: "+ex.getMessage()); }
            }
        }.execute();
    }

    private void adicionarVenda() {
        try { Venda novo = CrudDialog.showVendaDialog(this, null, "Adicionar Venda"); if (novo == null) return; String resp = VendaService.create(novo); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro criando venda: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Venda criada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao criar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarVendas(); }
    }

    private void editarVenda() {
        JTable t = findTableByName("tabelaVendas"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione uma venda.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = vendasModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        Venda orig = new Venda(); orig.setId(id); orig.setDescricaoProduto(stringValueForModel(vendasModel.getValueAt(msel,1)));
        Venda edited = CrudDialog.showVendaDialog(this, orig, "Editar Venda"); if (edited == null) return; try { String resp = VendaService.update(edited.getId(), edited); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Venda atualizada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao salvar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarVendas(); }
    }

    private void deletarVenda() {
        JTable t = findTableByName("tabelaVendas"); if (t == null) return; int sel = t.getSelectedRow(); if (sel == -1) { JOptionPane.showMessageDialog(this, "Selecione uma venda.", "Aviso", JOptionPane.WARNING_MESSAGE); return; }
        int msel = t.convertRowIndexToModel(sel); Object idObj = vendasModel.getValueAt(msel,0); Long id = parseLongFromObject(idObj); if (id == null) { JOptionPane.showMessageDialog(this, "Item sem ID.", "Erro", JOptionPane.ERROR_MESSAGE); return; }
        if (!CrudDialog.showConfirmDeleteDialog(this, "Venda id="+id)) return; try { String resp = VendaService.delete(id); if (JsonParser.isError(resp)) JOptionPane.showMessageDialog(this, "Erro: " + JsonParser.extractJsonValue(resp, "message"), "Erro", JOptionPane.ERROR_MESSAGE); else JOptionPane.showMessageDialog(this, "Venda deletada.", "Sucesso", JOptionPane.INFORMATION_MESSAGE); } catch (Exception e) { JOptionPane.showMessageDialog(this, "Erro ao deletar venda: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); } finally { carregarVendas(); }
    }

    private void gerarBoleto() {
        JTable t = findTableByName("tabelaVendas");
        if (t == null) return;
        int sel = t.getSelectedRow();
        if (sel == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma venda.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int msel = t.convertRowIndexToModel(sel);
        Object idObj = vendasModel.getValueAt(msel, 0);
        Long id = parseLongFromObject(idObj);
        if (id == null) {
            JOptionPane.showMessageDialog(this, "Venda sem ID.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Capturar dados da venda
        String descricao = stringValueForModel(vendasModel.getValueAt(msel, 1));
        String quantidade = stringValueForModel(vendasModel.getValueAt(msel, 2));
        String valorUnitario = stringValueForModel(vendasModel.getValueAt(msel, 3));
        String valorTotal = stringValueForModel(vendasModel.getValueAt(msel, 4));
        String data = stringValueForModel(vendasModel.getValueAt(msel, 5));
        String hora = stringValueForModel(vendasModel.getValueAt(msel, 6));

        // Solicitar nome do cliente
        String cliente = JOptionPane.showInputDialog(this,
            "Digite o nome do cliente para o boleto:",
            "Gerador de Boleto",
            JOptionPane.PLAIN_MESSAGE);

        if (cliente == null || cliente.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do cliente é obrigatório.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Gerar conteúdo do boleto
            String conteudoBoleto = com.br.pdvpostocombustivelfrontend.frontend.util.BoletoGerador.gerarBoleto(
                id.toString(),
                descricao,
                valorTotal,
                data,
                hora,
                cliente
            );

            // Mostrar em diálogo
            JTextArea textArea = new JTextArea(conteudoBoleto);
            textArea.setEditable(false);
            textArea.setFont(new java.awt.Font("Courier New", java.awt.Font.PLAIN, 11));
            textArea.setLineWrap(false);

            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(900, 600));

            int opcao = JOptionPane.showOptionDialog(
                this,
                scrollPane,
                "Boleto - Venda #" + id,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Salvar", "Copiar", "Cancelar"},
                "Salvar"
            );

            if (opcao == 0) {
                // Salvar em arquivo
                javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                fileChooser.setSelectedFile(new java.io.File("boleto_venda_" + id + ".txt"));
                int result = fileChooser.showSaveDialog(this);

                if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                    String caminho = fileChooser.getSelectedFile().getAbsolutePath();
                    if (com.br.pdvpostocombustivelfrontend.frontend.util.BoletoGerador.salvarBoleto(caminho, conteudoBoleto)) {
                        JOptionPane.showMessageDialog(this, "Boleto salvo com sucesso!\n" + caminho, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao salvar o boleto.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else if (opcao == 1) {
                // Copiar para clipboard
                java.awt.Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                    new java.awt.datatransfer.StringSelection(conteudoBoleto),
                    null
                );
                JOptionPane.showMessageDialog(this, "Boleto copiado para a área de transferência!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar boleto: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void logout() {
        int ok = JOptionPane.showConfirmDialog(this, "Deseja deslogar e voltar para a tela de login?", "Deslogar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            // Fecha a janela atual e abre a tela de login
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
