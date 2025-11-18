package com.br.pdvpostocombustivelfrontend.frontend.util;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Classe utilitária para gerar boletos em formato texto
 */
public class BoletoGerador {

    // Dados do beneficiário (PDV Posto)
    private static final String BANCO_NUMERO = "001"; // Banco do Brasil
    private static final String AGENCIA = "0001";
    private static final String CONTA = "123456";
    private static final String BENEFICIARIO = "PDV POSTO DE COMBUSTIVEL";
    private static final String CNPJ_BENEFICIARIO = "12.345.678/0001-90";

    /**
     * Gera um boleto simples para a venda
     */
    public static String gerarBoleto(String vendaId, String descricao, String valor,
                                     String dataVenda, String horaVenda, String cliente) {
        try {
            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataAtual = LocalDate.now();
            LocalDate dataVencimento = dataAtual.plusDays(5); // Vencimento em 5 dias

            // Gerar número sequencial para o boleto
            String nossoNumero = gerarNossoNumero(vendaId);
            String codigoBarras = gerarCodigoBarras(nossoNumero, valor);

            // Montar conteúdo do boleto
            StringBuilder boleto = new StringBuilder();
            boleto.append("═══════════════════════════════════════════════════════════════════════════════════\n");
            boleto.append("                              BOLETO PARA PAGAMENTO\n");
            boleto.append("═══════════════════════════════════════════════════════════════════════════════════\n\n");

            boleto.append("Banco: ").append(BANCO_NUMERO).append(" - BANCO DO BRASIL\n\n");

            // Dados do Beneficiário
            boleto.append("┌─ BENEFICIÁRIO ────────────────────────────────────────────────────────────────────┐\n");
            boleto.append("│ ").append(BENEFICIARIO).append("\n");
            boleto.append("│ CNPJ: ").append(CNPJ_BENEFICIARIO).append("\n");
            boleto.append("│ Agência: ").append(AGENCIA).append(" | Conta: ").append(CONTA).append("\n");
            boleto.append("└────────────────────────────────────────────────────────────────────────────────────┘\n\n");

            // Dados da Venda
            boleto.append("┌─ VENDA/REFERÊNCIA ────────────────────────────────────────────────────────────────┐\n");
            boleto.append("│ ID da Venda: ").append(vendaId).append("\n");
            boleto.append("│ Descrição: ").append(descricao).append("\n");
            boleto.append("│ Data: ").append(dataVenda).append(" | Hora: ").append(horaVenda).append("\n");
            boleto.append("└────────────────────────────────────────────────────────────────────────────────────┘\n\n");

            // Dados de Pagamento
            boleto.append("┌─ PAGADOR ──────────────────────────────────────────────────────────────────────────┐\n");
            boleto.append("│ Cliente: ").append(cliente).append("\n");
            boleto.append("└────────────────────────────────────────────────────────────────────────────────────┘\n\n");

            // Informações Financeiras
            boleto.append("┌─ INFORMAÇÕES FINANCEIRAS ──────────────────────────────────────────────────────────┐\n");
            boleto.append("│ Valor: R$ ").append(formatarValor(valor)).append("\n");
            boleto.append("│ Data de Emissão: ").append(df.format(dataAtual)).append("\n");
            boleto.append("│ Data de Vencimento: ").append(df.format(dataVencimento)).append("\n");
            boleto.append("│ Dias para Vencer: ").append(5).append(" dias\n");
            boleto.append("└────────────────────────────────────────────────────────────────────────────────────┘\n\n");

            // Código de Barras
            boleto.append("┌─ CÓDIGO DE BARRAS ─────────────────────────────────────────────────────────────────┐\n");
            boleto.append("│ ").append(codigoBarras).append("\n");
            boleto.append("│ Nosso Número: ").append(nossoNumero).append("\n");
            boleto.append("└────────────────────────────────────────────────────────────────────────────────────┘\n\n");

            // Instruções
            boleto.append("┌─ INSTRUÇÕES DE PAGAMENTO ──────────────────────────────────────────────────────────┐\n");
            boleto.append("│ 1. Apresente este boleto em qualquer agência do Banco do Brasil\n");
            boleto.append("│ 2. Você pode pagar em caixa eletrônico\n");
            boleto.append("│ 3. Aceitamos pagamento via PIX (chave: cnpj@postocombustivel.br)\n");
            boleto.append("│ 4. Pague até a data de vencimento para evitar multa\n");
            boleto.append("│ 5. Após o vencimento, juros de 0,33% ao dia\n");
            boleto.append("│ 6. Multa de 2% após 30 dias de atraso\n");
            boleto.append("└────────────────────────────────────────────────────────────────────────────────────┘\n\n");

            boleto.append("═══════════════════════════════════════════════════════════════════════════════════\n");
            boleto.append("OBSERVAÇÕES: Este é um comprovante de venda. Guarde-o para efeito de garantia.\n");
            boleto.append("═══════════════════════════════════════════════════════════════════════════════════\n");

            return boleto.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao gerar boleto: " + e.getMessage();
        }
    }

    /**
     * Gera um "nosso número" para o boleto
     */
    private static String gerarNossoNumero(String vendaId) {
        long id = Long.parseLong(vendaId);
        String nossoNumero = String.format("%010d", id);
        // Adicionar dígito verificador simples
        int digito = calcularDigitoVerificador(nossoNumero);
        return nossoNumero + digito;
    }

    /**
     * Calcula dígito verificador usando módulo 11
     */
    private static int calcularDigitoVerificador(String numero) {
        int soma = 0;
        int multiplicador = 2;

        for (int i = numero.length() - 1; i >= 0; i--) {
            soma += Character.getNumericValue(numero.charAt(i)) * multiplicador;
            multiplicador++;
            if (multiplicador > 9) multiplicador = 2;
        }

        int resto = soma % 11;
        return resto == 0 || resto == 1 ? 0 : 11 - resto;
    }

    /**
     * Gera um código de barras simples (sequência numérica)
     */
    private static String gerarCodigoBarras(String nossoNumero, String valor) {
        // Formato simplificado: BANCO(3) + AGENCIA(4) + NOSSO_NUM(11) + VALOR(10)
        String valorLimpo = valor.replaceAll("[^0-9]", "");
        while (valorLimpo.length() < 10) {
            valorLimpo = "0" + valorLimpo;
        }
        return BANCO_NUMERO + AGENCIA + nossoNumero + valorLimpo;
    }

    /**
     * Formata valor para exibição
     */
    private static String formatarValor(String valor) {
        try {
            Double d = Double.parseDouble(valor.replaceAll("[^0-9.,]", "").replace(",", "."));
            return String.format("%.2f", d);
        } catch (Exception e) {
            return valor;
        }
    }

    /**
     * Salva boleto em arquivo de texto
     */
    public static boolean salvarBoleto(String caminho, String conteudo) {
        try (FileOutputStream fos = new FileOutputStream(caminho)) {
            fos.write(conteudo.getBytes("UTF-8"));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

