-- Drop tables in correct order (reverse of foreign key dependencies)
DROP TABLE IF EXISTS vendas CASCADE;
DROP TABLE IF EXISTS precos CASCADE;
DROP TABLE IF EXISTS estoques CASCADE;
DROP TABLE IF EXISTS custos CASCADE;
DROP TABLE IF EXISTS contato CASCADE;
DROP TABLE IF EXISTS acessos CASCADE;
DROP TABLE IF EXISTS produtos CASCADE;
DROP TABLE IF EXISTS pessoa CASCADE;

-- Confirm deletion
SELECT 'All tables dropped successfully' as message;

