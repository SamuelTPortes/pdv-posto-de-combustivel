package com.br.pdvpostocombustivelfrontend.frontend.ui;

import com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa;
import com.br.pdvpostocombustivelfrontend.frontend.model.Produto;

import javax.swing.*;
import java.awt.*;

/**
 * Diálogos para operações CRUD
 */
public class CrudDialog {

    // ==================== PESSOA ====================

    /**
     * Dialog para adicionar/editar pessoa
     */
    public static Pessoa showPessoaDialog(JFrame parent, Pessoa pessoa, String title) {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nomeField = new JTextField(pessoa != null ? pessoa.getNomeCompleto() : "");
        JTextField cpfField = new JTextField(pessoa != null ? pessoa.getCpfCnpj() : "");
        JTextField ctpsField = new JTextField(pessoa != null ? (pessoa.getNumeroCtps() != null ? pessoa.getNumeroCtps().toString() : "") : "");
        JTextField dataNascField = new JTextField(pessoa != null ? pessoa.getDataNascimento() : "");
        JTextField emailField = new JTextField(pessoa != null ? pessoa.getEmail() : "");
        JTextField telefoneField = new JTextField(pessoa != null ? pessoa.getTelefone() : "");
        JTextField enderecoField = new JTextField(pessoa != null ? pessoa.getEndereco() : "");

        JComboBox<String> tipoPessoaCombo = new JComboBox<>(new String[]{"FISICA", "JURIDICA"});
        if (pessoa != null && pessoa.getTipoPessoa() != null) {
            tipoPessoaCombo.setSelectedItem(pessoa.getTipoPessoa());
        }

        panel.add(new JLabel("Nome Completo:"));
        panel.add(nomeField);
        panel.add(new JLabel("CPF/CNPJ:"));
        panel.add(cpfField);
        panel.add(new JLabel("Número CTPS:"));
        panel.add(ctpsField);
        panel.add(new JLabel("Data Nascimento (YYYY-MM-DD):"));
        panel.add(dataNascField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Endereço:"));
        panel.add(enderecoField);
        panel.add(new JLabel("Tipo Pessoa:"));
        panel.add(tipoPessoaCombo);

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Pessoa novaPessoa = new Pessoa();
                if (pessoa != null && pessoa.getId() != null) {
                    novaPessoa.setId(pessoa.getId());
                }
                novaPessoa.setNomeCompleto(nomeField.getText());
                novaPessoa.setCpfCnpj(cpfField.getText());
                if (!ctpsField.getText().trim().isEmpty()) {
                    novaPessoa.setNumeroCtps(Long.parseLong(ctpsField.getText().trim()));
                }
                novaPessoa.setDataNascimento(dataNascField.getText());
                novaPessoa.setTipoPessoa((String) tipoPessoaCombo.getSelectedItem());
                novaPessoa.setEmail(emailField.getText());
                novaPessoa.setTelefone(telefoneField.getText());
                novaPessoa.setEndereco(enderecoField.getText());
                return novaPessoa;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(parent, "Erro ao converter número CTPS",
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }

    // ==================== PRODUTO ====================

    /**
     * Dialog para adicionar/editar produto
     */
    public static Produto showProdutoDialog(JFrame parent, Produto produto, String title) {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nomeField = new JTextField(produto != null ? produto.getNome() : "");
        JTextField refField = new JTextField(produto != null ? produto.getReferencia() : "");
        JTextField fornField = new JTextField(produto != null ? produto.getFornecedor() : "");
        JTextField marcaField = new JTextField(produto != null ? produto.getMarca() : "");

        JComboBox<String> tipoCombo = new JComboBox<>(
            new String[]{"COMBUSTIVEL", "LUBRIFICANTE"}
        );
        if (produto != null && produto.getTipoProduto() != null) {
            tipoCombo.setSelectedItem(produto.getTipoProduto());
        }

        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Referência:"));
        panel.add(refField);
        panel.add(new JLabel("Fornecedor:"));
        panel.add(fornField);
        panel.add(new JLabel("Marca:"));
        panel.add(marcaField);
        panel.add(new JLabel("Tipo:"));
        panel.add(tipoCombo);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Produto novoProduto = new Produto();
            if (produto != null && produto.getId() != null) {
                novoProduto.setId(produto.getId());
            }
            novoProduto.setNome(nomeField.getText());
            novoProduto.setReferencia(refField.getText());
            novoProduto.setFornecedor(fornField.getText());
            novoProduto.setMarca(marcaField.getText());
            novoProduto.setTipoProduto((String) tipoCombo.getSelectedItem());
            return novoProduto;
        }
        return null;
    }



    // ==================== PRECO ====================

    /**
     * Dialog para adicionar/editar preço
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Preco showPrecoDialog(JFrame parent, com.br.pdvpostocombustivelfrontend.frontend.model.Preco preco, String title) {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField valorField = new JTextField(preco != null ? preco.getValor() : "");
        JTextField dataField = new JTextField(preco != null ? preco.getDataAlteracao() : "");
        JTextField horaField = new JTextField(preco != null ? preco.getHoraAlteracao() : "");

        JComboBox<String> tipoCombo = new JComboBox<>(new String[] {"UNITARIO", "TOTAL"});
        if (preco != null && preco.getTipoPreco() != null) tipoCombo.setSelectedItem(preco.getTipoPreco());

        panel.add(new JLabel("Valor:"));
        panel.add(valorField);
        panel.add(new JLabel("Data Alteração (YYYY-MM-DD):"));
        panel.add(dataField);
        panel.add(new JLabel("Hora Alteração (HH:MM):"));
        panel.add(horaField);
        panel.add(new JLabel("Tipo Preço:"));
        panel.add(tipoCombo);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            com.br.pdvpostocombustivelfrontend.frontend.model.Preco p = new com.br.pdvpostocombustivelfrontend.frontend.model.Preco();
            if (preco != null && preco.getId() != null) p.setId(preco.getId());
            p.setValor(valorField.getText());
            p.setDataAlteracao(dataField.getText());
            p.setHoraAlteracao(horaField.getText());
            p.setTipoPreco((String) tipoCombo.getSelectedItem());
            return p;
        }
        return null;
    }

    // ==================== ESTOQUE ====================

    /**
     * Dialog para adicionar/editar estoque
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Estoque showEstoqueDialog(JFrame parent, com.br.pdvpostocombustivelfrontend.frontend.model.Estoque estoque, String title) {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField quantidadeField = new JTextField(estoque != null ? estoque.getQuantidade() : "");
        JTextField localTanqueField = new JTextField(estoque != null ? estoque.getLocalTanque() : "");
        JTextField localEnderecoField = new JTextField(estoque != null ? estoque.getLocalEndereco() : "");
        JTextField localFabField = new JTextField(estoque != null ? estoque.getLocalFabricacao() : "");
        JTextField dataValField = new JTextField(estoque != null ? estoque.getDataValidade() : "");

        JComboBox<String> tipoCombo = new JComboBox<>(new String[] {"MINIMO", "VENDAS", "RESERVA"});
        if (estoque != null && estoque.getTipoEstoque() != null) tipoCombo.setSelectedItem(estoque.getTipoEstoque());

        panel.add(new JLabel("Quantidade:"));
        panel.add(quantidadeField);
        panel.add(new JLabel("Local Tanque:"));
        panel.add(localTanqueField);
        panel.add(new JLabel("Local Endereço:"));
        panel.add(localEnderecoField);
        panel.add(new JLabel("Local Fabricação:"));
        panel.add(localFabField);
        panel.add(new JLabel("Data Validade (YYYY-MM-DD):"));
        panel.add(dataValField);
        panel.add(new JLabel("Tipo Estoque:"));
        panel.add(tipoCombo);

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            com.br.pdvpostocombustivelfrontend.frontend.model.Estoque e = new com.br.pdvpostocombustivelfrontend.frontend.model.Estoque();
            if (estoque != null && estoque.getId() != null) e.setId(estoque.getId());
            e.setQuantidade(quantidadeField.getText());
            e.setLocalTanque(localTanqueField.getText());
            e.setLocalEndereco(localEnderecoField.getText());
            e.setLocalFabricacao(localFabField.getText());
            e.setDataValidade(dataValField.getText());
            e.setTipoEstoque((String) tipoCombo.getSelectedItem());
            return e;
        }
        return null;
    }

    // ==================== CUSTO ====================

    /**
     * Dialog para adicionar/editar custo
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Custo showCustoDialog(JFrame parent, com.br.pdvpostocombustivelfrontend.frontend.model.Custo custo, String title) {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField impostoField = new JTextField(custo != null ? custo.getImposto() : "");
        JTextField freteField = new JTextField(custo != null ? custo.getFrete() : "");
        JTextField seguroField = new JTextField(custo != null ? custo.getSeguro() : "");
        JTextField custoVarField = new JTextField(custo != null ? custo.getCustoVariavel() : "");
        JTextField custoFixoField = new JTextField(custo != null ? custo.getCustoFixo() : "");
        JTextField margemField = new JTextField(custo != null ? custo.getMargemLucro() : "");

        JComboBox<String> tipoCombo = new JComboBox<>(new String[] {"CUSTOFIXO", "CUSTOVARIAVEL", "CUSTOFRETE", "IMPOSTO"});
        if (custo != null && custo.getTipoCusto() != null) tipoCombo.setSelectedItem(custo.getTipoCusto());

        panel.add(new JLabel("Imposto:"));
        panel.add(impostoField);
        panel.add(new JLabel("Frete:"));
        panel.add(freteField);
        panel.add(new JLabel("Seguro:"));
        panel.add(seguroField);
        panel.add(new JLabel("Custo Variável:"));
        panel.add(custoVarField);
        panel.add(new JLabel("Custo Fixo:"));
        panel.add(custoFixoField);
        panel.add(new JLabel("Margem Lucro:"));
        panel.add(margemField);
        panel.add(new JLabel("Tipo Custo:"));
        panel.add(tipoCombo);

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            com.br.pdvpostocombustivelfrontend.frontend.model.Custo c = new com.br.pdvpostocombustivelfrontend.frontend.model.Custo();
            if (custo != null && custo.getId() != null) c.setId(custo.getId());
            c.setImposto(impostoField.getText());
            c.setFrete(freteField.getText());
            c.setSeguro(seguroField.getText());
            c.setCustoVariavel(custoVarField.getText());
            c.setCustoFixo(custoFixoField.getText());
            c.setMargemLucro(margemField.getText());
            c.setTipoCusto((String) tipoCombo.getSelectedItem());
            return c;
        }
        return null;
    }

    // ==================== ACESSO (USUARIO) ====================

    /**
     * Dialog para adicionar/editar acesso (usuário)
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Acesso showAcessoDialog(JFrame parent, com.br.pdvpostocombustivelfrontend.frontend.model.Acesso acesso, String title) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField usuarioField = new JTextField(acesso != null ? acesso.getUsuario() : "");
        JPasswordField senhaField = new JPasswordField(acesso != null ? (acesso.getSenha() != null ? acesso.getSenha() : "") : "");

        JComboBox<String> tipoCombo = new JComboBox<>(new String[]{"ADMINISTRADOR", "GESTAO", "FUNCIONARIO"});
        if (acesso != null && acesso.getTipoAcesso() != null) tipoCombo.setSelectedItem(acesso.getTipoAcesso());

        panel.add(new JLabel("Usuário:"));
        panel.add(usuarioField);
        panel.add(new JLabel("Senha:"));
        panel.add(senhaField);
        panel.add(new JLabel("Tipo Acesso:"));
        panel.add(tipoCombo);
        panel.add(new JLabel(""));
        panel.add(new JLabel(""));

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            com.br.pdvpostocombustivelfrontend.frontend.model.Acesso a = new com.br.pdvpostocombustivelfrontend.frontend.model.Acesso();
            if (acesso != null && acesso.getId() != null) a.setId(acesso.getId());
            a.setUsuario(usuarioField.getText());
            a.setSenha(new String(senhaField.getPassword()));
            a.setTipoAcesso((String) tipoCombo.getSelectedItem());
            return a;
        }
        return null;
    }

    /**
     * Dialog de confirmação para deletar
     */
    public static boolean showConfirmDeleteDialog(JFrame parent, String itemName) {
        int result = JOptionPane.showConfirmDialog(parent,
                "Tem certeza que deseja deletar: " + itemName + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    // ==================== VENDA ====================

    /**
     * Dialog para adicionar/editar venda
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Venda showVendaDialog(JFrame parent,
            com.br.pdvpostocombustivelfrontend.frontend.model.Venda venda, String title) {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField descricaoField = new JTextField(venda != null ? venda.getDescricaoProduto() : "");
        JTextField quantidadeField = new JTextField(venda != null ? venda.getQuantidade() : "");
        JTextField valorUnitarioField = new JTextField(venda != null ? venda.getValorUnitario() : "");
        JTextField dataVendaField = new JTextField(venda != null ? venda.getDataVenda() : java.time.LocalDate.now().toString());
        JTextField horaVendaField = new JTextField(venda != null ? venda.getHoraVenda() : java.time.LocalTime.now().toString().substring(0, 5));
        JTextField observacoesField = new JTextField(venda != null ? venda.getObservacoes() : "");

        JComboBox<String> tipoCombustivelCombo = new JComboBox<>(new String[]{"GASOLINA", "DIESEL", "ALCOOL", "OUTROS"});
        if (venda != null && venda.getTipoCombustivel() != null) {
            tipoCombustivelCombo.setSelectedItem(venda.getTipoCombustivel());
        }

        JComboBox<String> tipoPrecoCombo = new JComboBox<>(new String[]{"UNITARIO", "TOTAL"});
        if (venda != null && venda.getTipoPreco() != null) {
            tipoPrecoCombo.setSelectedItem(venda.getTipoPreco());
        }

        panel.add(new JLabel("Descrição Produto:"));
        panel.add(descricaoField);
        panel.add(new JLabel("Quantidade:"));
        panel.add(quantidadeField);
        panel.add(new JLabel("Valor Unitário:"));
        panel.add(valorUnitarioField);
        panel.add(new JLabel("Data Venda (YYYY-MM-DD):"));
        panel.add(dataVendaField);
        panel.add(new JLabel("Hora Venda (HH:MM):"));
        panel.add(horaVendaField);
        panel.add(new JLabel("Tipo Combustível:"));
        panel.add(tipoCombustivelCombo);
        panel.add(new JLabel("Tipo Preço:"));
        panel.add(tipoPrecoCombo);
        panel.add(new JLabel("Observações:"));
        panel.add(observacoesField);

        int result = JOptionPane.showConfirmDialog(parent, panel, title,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                com.br.pdvpostocombustivelfrontend.frontend.model.Venda novaVenda = new com.br.pdvpostocombustivelfrontend.frontend.model.Venda();
                if (venda != null && venda.getId() != null) {
                    novaVenda.setId(venda.getId());
                }
                novaVenda.setDescricaoProduto(descricaoField.getText());
                novaVenda.setQuantidade(quantidadeField.getText());
                novaVenda.setValorUnitario(valorUnitarioField.getText());
                novaVenda.setDataVenda(dataVendaField.getText());
                novaVenda.setHoraVenda(horaVendaField.getText());
                novaVenda.setTipoCombustivel((String) tipoCombustivelCombo.getSelectedItem());
                novaVenda.setTipoPreco((String) tipoPrecoCombo.getSelectedItem());
                novaVenda.setObservacoes(observacoesField.getText());
                return novaVenda;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(parent, "Erro ao processar dados da venda: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
}
