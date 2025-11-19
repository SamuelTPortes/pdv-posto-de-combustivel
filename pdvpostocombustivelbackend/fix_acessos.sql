-- Script de correção da tabela acessos
-- Remove colunas indevidas (herdadas erroneamente de custos) e garante definição esperada.
-- Pode ser executado com: psql -d <database> -f fix_acessos.sql

BEGIN;

-- Remover possíveis colunas de custos que não pertencem à tabela acessos
ALTER TABLE acessos DROP COLUMN IF EXISTS imposto;
ALTER TABLE acessos DROP COLUMN IF EXISTS frete;
ALTER TABLE acessos DROP COLUMN IF EXISTS seguro;
ALTER TABLE acessos DROP COLUMN IF EXISTS custo_variavel;
ALTER TABLE acessos DROP COLUMN IF EXISTS custo_fixo;
ALTER TABLE acessos DROP COLUMN IF EXISTS margem_lucro;
ALTER TABLE acessos DROP COLUMN IF EXISTS tipo_custo;

-- Garantir colunas essenciais com tipos e restrições corretas
-- (Se já estiverem corretas, os ALTERs não causarão problemas)
ALTER TABLE acessos ALTER COLUMN usuario TYPE VARCHAR(30);
ALTER TABLE acessos ALTER COLUMN senha TYPE VARCHAR(30);
ALTER TABLE acessos ALTER COLUMN tipo_acesso TYPE VARCHAR(30);

ALTER TABLE acessos ALTER COLUMN usuario SET NOT NULL;
ALTER TABLE acessos ALTER COLUMN senha SET NOT NULL;
ALTER TABLE acessos ALTER COLUMN tipo_acesso SET NOT NULL;

-- Garantir unicidade do usuário
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_indexes WHERE schemaname = current_schema() AND indexname = 'uk_acessos_usuario'
    ) THEN
        BEGIN
            ALTER TABLE acessos ADD CONSTRAINT uk_acessos_usuario UNIQUE (usuario);
        EXCEPTION WHEN duplicate_object THEN
            NULL; -- já existe
        END;
    END IF;
END$$;

-- Garantir chave estrangeira pessoa_id conforme schema original
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.constraint_column_usage c
        JOIN information_schema.table_constraints t ON c.constraint_name = t.constraint_name
        WHERE c.table_name = 'acessos' AND c.column_name = 'pessoa_id' AND t.constraint_type = 'FOREIGN KEY'
    ) THEN
        BEGIN
            ALTER TABLE acessos ADD CONSTRAINT fk_acessos_pessoa FOREIGN KEY (pessoa_id)
                REFERENCES pessoa(id) ON DELETE SET NULL;
        EXCEPTION WHEN duplicate_object THEN
            NULL; -- já existe
        END;
    END IF;
END$$;

COMMIT;

-- Mensagem final
SELECT 'Correção da tabela acessos aplicada com sucesso' AS mensagem;
