# 🧪 Guia de Testes - Editar e Deletar Acessos

## 📋 Pré-requisitos
- ✅ Backend compilado e rodando
- ✅ Frontend compilado e rodando
- ✅ Sistema com login realizado
- ✅ Aba "Acessos" está visível

---

## 🧪 Teste 1: Carregar Acessos

### Passos:
1. Faça login no sistema
2. Clique na aba "Acessos"
3. Verifique a tabela

### Resultado Esperado:
```
┌─────┬──────────┬─────────────┬────────────┬──────────┐
│ ID  │ Usuário  │ Data Acesso │ Hora Acesso│ Tipo     │
├─────┼──────────┼─────────────┼────────────┼──────────┤
│ 1   │ admin    │ 2025-01-01  │ 10:30      │ ADMIN    │
│ 2   │ samuel   │ 2025-01-01  │ 11:00      │ USUARIO  │
│ 3   │ asd      │ 2025-01-01  │ 11:30      │ USUARIO  │
└─────┴──────────┴─────────────┴────────────┴──────────┘
```

✅ **Passou se**: Todos os IDs estão preenchidos e visíveis

---

## 🧪 Teste 2: Editar Acesso

### Passos:
1. Na tabela de Acessos, selecione um registro (ex: "admin")
2. Clique no botão "✎ Editar"
3. Na janela que abrir:
   - Modifique o campo "Tipo de Acesso"
   - Clique em "Salvar"

### Resultado Esperado:
```
✅ Mensagem: "Acesso salvo."
✅ Tabela atualiza com novos dados
✅ Nenhum erro no console
```

### Resultado Esperado NO CONSOLE:
```
DEBUG: Resolvendo ID para usuario: admin
DEBUG: ID encontrado via extractLongFallback: 1
DEBUG: Editando acesso com ID: 1
✅ Sucesso!
```

❌ **Falhou se**:
- Mensagem de erro: "Item sem ID válido"
- Mensagem de erro: "id não encontrado"
- Nenhuma mensagem aparece

---

## 🧪 Teste 3: Deletar Acesso

### Passos:
1. Na tabela de Acessos, selecione um registro (ex: "asd")
2. Clique no botão "🗑 Deletar"
3. Confirme a exclusão na janela de confirmação
4. Clique "Sim"

### Resultado Esperado:
```
✅ Mensagem: "Acesso deletado."
✅ Tabela atualiza e o registro desaparece
✅ Nenhum erro no console
```

### Resultado Esperado NO CONSOLE:
```
DEBUG: Deletando acesso com ID: 3
✅ Sucesso!
```

❌ **Falhou se**:
- Mensagem de erro: "Item sem ID válido"
- Mensagem de erro: "id não encontrado"
- Registro não desaparece da tabela

---

## 🧪 Teste 4: Recarregar Acessos

### Passos:
1. Clique em "🔄 Atualizar" na aba de Acessos
2. Aguarde 1-2 segundos

### Resultado Esperado:
```
✅ Tabela atualiza
✅ Todos os IDs continuam preenchidos
✅ Status barra inferior: "Conectado | Acessos: X"
```

---

## 🧪 Teste 5: Verificar Logs de Debug

### Passos:
1. Abra o terminal/console do Java (onde rodou o frontend)
2. Execute um teste de edição
3. Procure por linhas com "DEBUG:"

### Resultado Esperado:
```
DEBUG: Resolvendo ID para usuario: admin
DEBUG: ID encontrado via extractLongFallback: 1
DEBUG: ID encontrado via parseAcesso: 1
DEBUG: Editando acesso...
```

❌ **Falhou se**:
- Nenhuma linha DEBUG aparece
- Aparecer "DEBUG: Não foi possível extrair ID de:"

---

## 📊 Planilha de Resultados

| Teste | Resultado | Observações |
|-------|-----------|------------|
| 1. Carregar Acessos | ✅ / ❌ | IDs preenchidos? |
| 2. Editar Acesso | ✅ / ❌ | Mensagem de sucesso? |
| 3. Deletar Acesso | ✅ / ❌ | Registro desapareceu? |
| 4. Recarregar Acessos | ✅ / ❌ | IDs mantém-se? |
| 5. Logs de Debug | ✅ / ❌ | Debug apareceu? |

---

## 🔍 Troubleshooting

### Se aparecer: "Item sem ID válido"
**Causas possíveis**:
1. Backend não foi recompilado (ainda tem código antigo)
2. API ainda retorna sem ID
3. Conexão com API falhou

**Solução**:
```bash
cd pdvpostocombustivelbackend
mvnw clean package -DskipTests
# Reinicie o backend
```

---

### Se aparecer: "id não encontrado" (erro 404)
**Causas possíveis**:
1. ID não foi extraído corretamente
2. Endpoint /search ainda está conflitando

**Solução**:
```bash
# Verifique no console:
# DEBUG: ID encontrado via ... ?
# Se não, o fallback não funcionou
```

---

### Se a tabela estiver vazia mesmo após recarregar
**Causas possíveis**:
1. API não está retornando dados
2. Parser está falhando
3. Nenhum acesso no banco de dados

**Solução**:
```bash
# No console, procure por:
# "AVISO: Acesso sem ID encontrado"
# Se aparecer, é um problema de API
```

---

## 🎯 Critério de Sucesso

✅ **Todos os testes passaram se**:
- [x] Tabela mostra IDs preenchidos
- [x] Editar funciona sem erro
- [x] Deletar funciona sem erro
- [x] Logs DEBUG aparecem no console
- [x] Nenhuma mensagem de "ID não encontrado"

🎉 **Parabéns! O problema foi resolvido!**


