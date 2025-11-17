package com.br.pdvpostocombustivelfrontend.frontend.service;

import com.br.pdvpostocombustivelfrontend.frontend.config.AppConfig;
import com.br.pdvpostocombustivelfrontend.frontend.model.Produto;
import com.br.pdvpostocombustivelfrontend.frontend.util.HttpClient;

/**
 * Serviço para operações CRUD de Produto
 */
public class CrudService {

    /**
     * Criar novo produto
     */
    public static String createProduto(Produto produto) throws Exception {
        String json = produtoToJson(produto);
        return HttpClient.post(AppConfig.API_BASE_URL + "/produtos", json);
    }

    /**
     * Buscar produto por ID
     */
    public static String getProdutoById(Long id) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/produtos/" + id);
    }

    /**
     * Listar produtos com paginação
     */
    public static String listProdutos(int page, int size) throws Exception {
        return HttpClient.get(AppConfig.API_BASE_URL + "/produtos?page=" + page + "&size=" + size);
    }

    /**
     * Atualizar produto
     */
    public static String updateProduto(Long id, Produto produto) throws Exception {
        String json = produtoToJson(produto);
        return HttpClient.put(AppConfig.API_BASE_URL + "/produtos/" + id, json);
    }

    /**
     * Atualizar parcialmente produto
     */
    public static String patchProduto(Long id, Produto produto) throws Exception {
        String json = produtoToJson(produto);
        return HttpClient.patch(AppConfig.API_BASE_URL + "/produtos/" + id, json);
    }

    /**
     * Excluir produto
     */
    public static String deleteProduto(Long id) throws Exception {
        return HttpClient.delete(AppConfig.API_BASE_URL + "/produtos/" + id);
    }

    /**
     * Converte Produto para JSON
     */
    private static String produtoToJson(Produto produto) {
        StringBuilder json = new StringBuilder("{");

        if (produto.getNome() != null) {
            json.append("\"nome\":\"").append(escapeJson(produto.getNome())).append("\",");
        }
        if (produto.getReferencia() != null) {
            json.append("\"referencia\":\"").append(escapeJson(produto.getReferencia())).append("\",");
        }
        if (produto.getFornecedor() != null) {
            json.append("\"fornecedor\":\"").append(escapeJson(produto.getFornecedor())).append("\",");
        }
        if (produto.getMarca() != null) {
            json.append("\"marca\":\"").append(escapeJson(produto.getMarca())).append("\",");
        }
        if (produto.getTipoProduto() != null) {
            json.append("\"tipoProduto\":\"").append(produto.getTipoProduto()).append("\",");
        }

        // Remove última vírgula
        if (json.length() > 1) {
            json.deleteCharAt(json.length() - 1);
        }

        json.append("}");
        return json.toString();
    }

    /**
     * Escapa caracteres especiais para JSON
     */
    private static String escapeJson(String str) {
        if (str == null) return "";
        return str.replace("\\", "\\\\")
                  .replace("\"", "\\\"")
                  .replace("\n", "\\n")
                  .replace("\r", "\\r")
                  .replace("\t", "\\t");
    }
}

