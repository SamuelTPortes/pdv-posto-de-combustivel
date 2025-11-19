# Correção da tabela acessos

Se o erro `valor nulo na coluna "custo_fixo" da relação "acessos"` aparecer ao registrar usuário, significa que o schema do banco está incorreto e a tabela `acessos` contém colunas indevidas herdadas de `custos`.

## Passos para corrigir

1. Execute o script de correção:

```sql
\i fix_acessos.sql
```

Ou via terminal psql:

```bash
psql -h <HOST> -U <USER> -d <DATABASE> -f fix_acessos.sql
```

2. Se o schema estiver muito divergente, você pode recriar todas as tabelas:

```sql
\i drop_tables.sql
\i src/main/resources/schema.sql
\i src/main/resources/data.sql
```

3. Reinicie a aplicação backend:

No Windows:

```cmd
mvnw.cmd spring-boot:run
```

4. Teste o registro novamente no frontend.

## Observação
Não adicione campos de custo dentro da entidade `Acesso`; custos pertencem à tabela `custos`.


