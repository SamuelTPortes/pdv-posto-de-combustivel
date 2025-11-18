package com.br.pdvpostocombustivelfrontend.frontend.util;

import com.br.pdvpostocombustivelfrontend.frontend.model.LoginRequest;
import com.br.pdvpostocombustivelfrontend.frontend.model.LoginResponse;
import com.br.pdvpostocombustivelfrontend.frontend.model.TipoAcesso;
import com.br.pdvpostocombustivelfrontend.frontend.model.Pessoa;
import com.br.pdvpostocombustivelfrontend.frontend.model.Produto;

/**
 * Utilitário para parser simples de JSON (sem dependências externas)
 */
public class JsonParser {

    /**
     * Converte LoginRequest para JSON
     */
    public static String toJson(LoginRequest request) {
        return String.format("{\"usuario\":\"%s\",\"senha\":\"%s\"}",
                request.getUsuario(), request.getSenha());
    }

    /**
     * Converte JSON para LoginResponse
     */
    public static LoginResponse parseLoginResponse(String json) {
        try {
            LoginResponse response = new LoginResponse();

            // Parse sucesso
            String sucessoValue = extractValue(json, "sucesso");
            response.setSucesso(Boolean.parseBoolean(sucessoValue));

            // Parse mensagem
            String mensagem = extractValue(json, "mensagem");
            response.setMensagem(mensagem);

            // Parse usuario
            String usuario = extractValue(json, "usuario");
            response.setUsuario(usuario);

            // Parse tipoAcesso
            String tipoAcessoStr = extractValue(json, "tipoAcesso");
            if (tipoAcessoStr != null && !tipoAcessoStr.equals("null")) {
                response.setTipoAcesso(TipoAcesso.valueOf(tipoAcessoStr));
            }

            return response;
        } catch (Exception e) {
            LoginResponse errorResponse = new LoginResponse();
            errorResponse.setSucesso(false);
            errorResponse.setMensagem("Erro ao processar resposta: " + e.getMessage());
            return errorResponse;
        }
    }

    /**
     * Converte JSON para Pessoa
     */
    public static Pessoa parsePessoa(String json) {
        try {
            Pessoa pessoa = new Pessoa();

            String id = extractValue(json, "id");
            if (id != null && !id.isEmpty()) {
                pessoa.setId(Long.parseLong(id));
            }

            String nomeCompleto = extractValue(json, "nomeCompleto");
            pessoa.setNomeCompleto(nomeCompleto);

            String cpfCnpj = extractValue(json, "cpfCnpj");
            pessoa.setCpfCnpj(cpfCnpj);

            String numeroCtps = extractValue(json, "numeroCtps");
            if (numeroCtps != null && !numeroCtps.isEmpty()) {
                pessoa.setNumeroCtps(Long.parseLong(numeroCtps));
            }

            String dataNascimento = extractValue(json, "dataNascimento");
            pessoa.setDataNascimento(dataNascimento);

            return pessoa;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converte JSON para Produto
     */
    public static Produto parseProduto(String json) {
        try {
            Produto produto = new Produto();

            String id = extractValue(json, "id");
            if (id != null && !id.isEmpty()) {
                produto.setId(Long.parseLong(id));
            }

            String nome = extractValue(json, "nome");
            produto.setNome(nome);

            String referencia = extractValue(json, "referencia");
            produto.setReferencia(referencia);

            String fornecedor = extractValue(json, "fornecedor");
            produto.setFornecedor(fornecedor);

            String marca = extractValue(json, "marca");
            produto.setMarca(marca);

            String tipoProduto = extractValue(json, "tipoProduto");
            produto.setTipoProduto(tipoProduto);

            return produto;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converte JSON para Contato
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Contato parseContato(String json) {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Contato c = new com.br.pdvpostocombustivelfrontend.frontend.model.Contato();
            String telefone = extractValue(json, "telefone");
            String email = extractValue(json, "email");
            String endereco = extractValue(json, "endereco");
            String tipo = extractValue(json, "tipoContato");
            c.setTelefone(telefone);
            c.setEmail(email);
            c.setEndereco(endereco);
            c.setTipoContato(tipo);
            String id = extractValue(json, "id");
            if (id != null && !id.isEmpty()) {
                try { c.setId(Long.parseLong(id)); } catch (Exception ex) {}
            }
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converte JSON para Preco
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Preco parsePreco(String json) {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Preco p = new com.br.pdvpostocombustivelfrontend.frontend.model.Preco();
            // try robust id extraction first
            Long idL = extractLongFallback(json, "id", "precoId", "codigo");
            if (idL == null) {
                String idStr = extractValue(json, "id");
                if (idStr != null && !idStr.isEmpty()) {
                    try { idL = Long.parseLong(idStr.trim()); } catch (Exception ignored) {}
                }
            }
            if (idL != null) p.setId(idL);

            p.setValor(extractValue(json, "valor"));

            String rawData = extractValue(json, "dataAlteracao");
            if (rawData == null || rawData.isEmpty()) rawData = extractValueFallback(json, "dataAlteracao", "data_alteracao");
            if (rawData != null && !rawData.isEmpty()) {
                p.setDataAlteracao(normalizeDateOnly(rawData));
            }

            String rawHora = extractValue(json, "horaAlteracao");
            if (rawHora == null || rawHora.isEmpty()) rawHora = extractValueFallback(json, "horaAlteracao", "hora_alteracao");
            if (rawHora != null && !rawHora.isEmpty()) {
                p.setHoraAlteracao(normalizeTimeOnly(rawHora));
            }

            // tipoPreco can come as tipoPreco or tipo_preco
            String tipo = extractValueFallback(json, "tipoPreco", "tipo_preco", "tipoPrecoEnum");
            p.setTipoPreco(tipo);
            return p;
        } catch (Exception e) { return null; }
    }

    /**
     * Converte JSON para Estoque
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Estoque parseEstoque(String json) {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Estoque e = new com.br.pdvpostocombustivelfrontend.frontend.model.Estoque();
            Long idL = extractLongFallback(json, "id", "estoqueId", "codigo");
            if (idL == null) {
                String id = extractValue(json, "id");
                if (id != null && !id.isEmpty()) {
                    try { idL = Long.parseLong(id.trim()); } catch (Exception ignored) {}
                }
            }
            if (idL != null) e.setId(idL);
            e.setQuantidade(extractValue(json, "quantidade"));
            e.setLocalTanque(extractValue(json, "localTanque"));
            e.setLocalEndereco(extractValue(json, "localEndereco"));
            e.setLocalFabricacao(extractValue(json, "localFabricacao"));

            String rawVal = extractValue(json, "dataValidade");
            if (rawVal == null || rawVal.isEmpty()) rawVal = extractValueFallback(json, "dataValidade", "data_validade");
            if (rawVal != null && !rawVal.isEmpty()) {
                e.setDataValidade(normalizeDateOnly(rawVal));
            }

            String tipo = extractValueFallback(json, "tipoEstoque", "tipo_estoque");
            e.setTipoEstoque(tipo);
            return e;
        } catch (Exception ex) { return null; }
    }

    /**
     * Converte JSON para Custo
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Custo parseCusto(String json) {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Custo c = new com.br.pdvpostocombustivelfrontend.frontend.model.Custo();
            String id = extractValue(json, "id"); if (id != null && !id.isEmpty()) try { c.setId(Long.parseLong(id)); } catch (Exception ex) { Long idL = extractLongFallback(json, "id"); if (idL!=null) c.setId(idL); }
            c.setImposto(extractValue(json, "imposto"));
            c.setFrete(extractValue(json, "frete"));
            c.setSeguro(extractValue(json, "seguro"));
            c.setCustoVariavel(extractValue(json, "custoVariavel"));
            c.setCustoFixo(extractValue(json, "custoFixo"));
            c.setMargemLucro(extractValue(json, "margemLucro"));
            String tipo = extractValueFallback(json, "tipoCusto", "tipo_custo");
            c.setTipoCusto(tipo);
            return c;
        } catch (Exception e) { return null; }
    }

    /** Public helpers for UI formatting */
    public static String formatDate(String raw) {
        return normalizeDateOnly(raw);
    }

    public static String formatTime(String raw) {
        return normalizeTimeOnly(raw);
    }

    // Helpers to normalize ISO-like date/time strings returned by backend
    private static String normalizeDateOnly(String raw) {
        try {
            if (raw == null) return null;
            raw = raw.trim();
            // Try to find yyyy-MM-dd using regex
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{4}-\\d{2}-\\d{2})").matcher(raw);
            if (m.find()) return m.group(1);
            // fallback: previous strategy
            int t = raw.indexOf('T');
            String datePart = t >= 0 ? raw.substring(0, t) : raw;
            datePart = datePart.trim();
            if (datePart.length() >= 10) return datePart.substring(0, 10);
            return datePart;
        } catch (Exception e) { return raw; }
    }

    private static String normalizeTimeOnly(String raw) {
        try {
            if (raw == null) return null;
            raw = raw.trim();
            // Try to find HH:mm using regex
            java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d{1,2}:\\d{2})").matcher(raw);
            if (m.find()) {
                String hhmm = m.group(1);
                // pad hour to 2 digits
                String[] parts = hhmm.split(":");
                String hh = parts[0]; if (hh.length()==1) hh = "0"+hh;
                String mm = parts[1]; if (mm.length()==1) mm = "0"+mm;
                return hh+":"+mm;
            }
            // fallback: previous strategy
            String timePart = raw;
            int t = raw.indexOf('T');
            if (t >= 0) timePart = raw.substring(t + 1);
            int plus = timePart.indexOf('+');
            if (plus > 0) timePart = timePart.substring(0, plus);
            int minus = timePart.indexOf('-');
            if (minus > 0) timePart = timePart.substring(0, minus);
            String[] parts = timePart.split(":");
            if (parts.length >= 2) {
                String hh = parts[0].trim();
                String mm = parts[1].trim();
                if (hh.length() == 1) hh = "0" + hh;
                if (mm.length() == 1) mm = "0" + mm;
                return hh + ":" + mm;
            }
            return timePart.trim();
        } catch (Exception e) { return raw; }
    }

    /**
     * Verifica se a resposta contém erro
     */
    public static boolean isError(String json) {
        try {
            String status = extractValue(json, "status");
            String error = extractValue(json, "error");
            return status != null && (status.equals("500") || status.equals("400") || status.equals("404")) && error != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Extrai valor de uma chave JSON (parser simples)
     */
    private static String extractValue(String json, String key) {
        try {
            String searchKey = "\"" + key + "\":";
            int startIndex = json.indexOf(searchKey);
            if (startIndex == -1) {
                return null;
            }

            startIndex += searchKey.length();

            // Pular espaços
            while (startIndex < json.length() && Character.isWhitespace(json.charAt(startIndex))) {
                startIndex++;
            }

            // Se o valor é uma string (começa com ")
            if (json.charAt(startIndex) == '"') {
                startIndex++;
                int endIndex = json.indexOf('"', startIndex);
                return json.substring(startIndex, endIndex);
            }
            // Se o valor é booleano ou número
            else {
                int endIndex = startIndex;
                while (endIndex < json.length() &&
                       json.charAt(endIndex) != ',' &&
                       json.charAt(endIndex) != '}' &&
                       json.charAt(endIndex) != ']') {
                    endIndex++;
                }
                return json.substring(startIndex, endIndex).trim();
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método público para extrair valor JSON (usado externamente)
     */
    public static String extractJsonValue(String json, String key) {
        return extractValue(json, key);
    }

    /**
     * Extrai itens de um array JSON (por exemplo o 'content' de uma Page do Spring)
     * Retorna um array de strings, cada string é um objeto JSON (incluindo chaves {}).
     * Se não encontrar o array, tenta extrair objetos individuais em nível superior.
     */
    public static String[] extractJsonArrayItems(String json, String arrayKey) {
        try {
            String searchKey = "\"" + arrayKey + "\":";
            int idx = json.indexOf(searchKey);
            String arrayContent;
            if (idx != -1) {
                idx += searchKey.length();
                // pular espaços
                while (idx < json.length() && Character.isWhitespace(json.charAt(idx))) idx++;
                if (idx >= json.length() || json.charAt(idx) != '[') return new String[0];
                int start = idx + 1;
                int brace = 0;
                int end = start;
                // encontrar fim do array, considerando objetos internos
                for (int i = start; i < json.length(); i++) {
                    char c = json.charAt(i);
                    if (c == '[') brace++;
                    else if (c == ']') {
                        if (brace == 0) { end = i; break; }
                        else brace--;
                    }
                }
                arrayContent = json.substring(start, end);
            } else {
                // tenta encontrar um array principal (primeiro '[' ']' no JSON)
                int firstBracket = json.indexOf('[');
                int lastBracket = json.lastIndexOf(']');
                if (firstBracket == -1 || lastBracket == -1 || lastBracket <= firstBracket) return new String[0];
                arrayContent = json.substring(firstBracket + 1, lastBracket);
            }

            // agora separar itens (objetos JSON) respeitando chaves
            java.util.List<String> items = new java.util.ArrayList<>();
            int len = arrayContent.length();
            int depth = 0;
            int itemStart = -1;
            for (int i = 0; i < len; i++) {
                char c = arrayContent.charAt(i);
                if (c == '{') {
                    if (depth == 0) itemStart = i;
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0 && itemStart != -1) {
                        items.add(arrayContent.substring(itemStart, i + 1));
                        itemStart = -1;
                    }
                }
            }
            return items.toArray(new String[0]);
        } catch (Exception e) {
            return new String[0];
        }
    }

    /**
     * Tenta extrair um valor string por várias chaves alternativas, e faz fallback por regex se necessário.
     */
    private static String extractValueFallback(String json, String key, String... altKeys) {
        try {
            String v = extractValue(json, key);
            if (v != null) return v;
            if (altKeys != null) {
                for (String k : altKeys) {
                    v = extractValue(json, k);
                    if (v != null) return v;
                }
            }
            // fallback regex to capture quoted string: "key"\s*:\s*"([^"]*)"
            String regex = "\\\"" + java.util.regex.Pattern.quote(key) + "\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(regex);
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) return m.group(1);
            // try alternative keys by regex
            if (altKeys != null) {
                for (String k : altKeys) {
                    String regexAlt = "\\\"" + java.util.regex.Pattern.quote(k) + "\\\"\\s*:\\s*\\\"([^\\\"]*)\\\"";
                    p = java.util.regex.Pattern.compile(regexAlt);
                    m = p.matcher(json);
                    if (m.find()) return m.group(1);
                }
            }
            return null;
        } catch (Exception e) { return null; }
    }

    /**
     * Extrai um número inteiro (Long) de um JSON, tentando várias estratégias.
     */
    public static Long extractLongFallback(String json, String key, String... altKeys) {
        try {
            String v = extractValue(json, key);
            if (v != null && !v.trim().isEmpty()) {
                try { return Long.parseLong(v.trim()); } catch (Exception ex) { /* continue to regex */ }
            }
            if (altKeys != null) {
                for (String k : altKeys) {
                    v = extractValue(json, k);
                    if (v != null && !v.trim().isEmpty()) {
                        try { return Long.parseLong(v.trim()); } catch (Exception ex) { }
                    }
                }
            }
            // regex to find unquoted numeric value: "key"\s*:\s*(\d+)
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("\"" + java.util.regex.Pattern.quote(key) + "\"\\s*:\\s*(\\d+)");
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) return Long.parseLong(m.group(1));
            if (altKeys != null) {
                for (String k : altKeys) {
                    p = java.util.regex.Pattern.compile("\"" + java.util.regex.Pattern.quote(k) + "\"\\s*:\\s*(\\d+)");
                    m = p.matcher(json);
                    if (m.find()) return Long.parseLong(m.group(1));
                }
            }
            return null;
        } catch (Exception e) { return null; }
    }

    /**
     * Converte JSON para Acesso (usuário)
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Acesso parseAcesso(String json) {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Acesso a = new com.br.pdvpostocombustivelfrontend.frontend.model.Acesso();
            String id = extractValue(json, "id");
            if (id != null && !id.isEmpty()) {
                try { a.setId(Long.parseLong(id)); } catch (Exception ex) { /* ignore */ }
            }
            a.setUsuario(extractValue(json, "usuario"));
            a.setSenha(extractValue(json, "senha"));
            String tipo = extractValueFallback(json, "tipoAcesso", "tipo_acesso", "tipoAcessoEnum");
            a.setTipoAcesso(tipo);
            return a;
        } catch (Exception e) { return null; }
    }

    /**
     * Converte JSON para Venda
     */
    public static com.br.pdvpostocombustivelfrontend.frontend.model.Venda parseVenda(String json) {
        try {
            com.br.pdvpostocombustivelfrontend.frontend.model.Venda v = new com.br.pdvpostocombustivelfrontend.frontend.model.Venda();

            String id = extractValue(json, "id");
            if (id != null && !id.isEmpty()) {
                try { v.setId(Long.parseLong(id)); } catch (Exception ex) { /* ignore */ }
            }

            v.setDescricaoProduto(extractValue(json, "descricaoProduto"));
            v.setQuantidade(extractValue(json, "quantidade"));
            v.setValorUnitario(extractValue(json, "valorUnitario"));
            v.setValorTotal(extractValue(json, "valorTotal"));
            v.setDataVenda(extractValue(json, "dataVenda"));
            v.setHoraVenda(extractValue(json, "horaVenda"));
            v.setTipoPreco(extractValueFallback(json, "tipoPreco", "tipo_preco"));
            v.setTipoCombustivel(extractValueFallback(json, "tipoCombustivel", "tipo_combustivel"));
            v.setObservacoes(extractValue(json, "observacoes"));

            return v;
        } catch (Exception e) { return null; }
    }
}

