-- Criação da tabela pessoa
CREATE TABLE IF NOT EXISTS pessoa (
    id BIGSERIAL PRIMARY KEY,
    nome_completo VARCHAR(200) NOT NULL,
    cpf_cnpj VARCHAR(14) NOT NULL UNIQUE,
    numero_ctps BIGINT,
    data_nascimento DATE NOT NULL,
    tipo_pessoa VARCHAR(15) NOT NULL,
    email VARCHAR(70),
    telefone VARCHAR(20),
    endereco VARCHAR(70)
);

-- Criação da tabela acessos
CREATE TABLE IF NOT EXISTS acessos (
    id BIGSERIAL PRIMARY KEY,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    senha VARCHAR(30) NOT NULL,
    tipo_acesso VARCHAR(30) NOT NULL,
    pessoa_id BIGINT,
    FOREIGN KEY (pessoa_id) REFERENCES pessoa(id) ON DELETE SET NULL
);

-- Criação da tabela produtos
CREATE TABLE IF NOT EXISTS produtos (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(30) NOT NULL,
    referencia VARCHAR(30) NOT NULL,
    fornecedor VARCHAR(30) NOT NULL,
    marca VARCHAR(30) NOT NULL,
    tipo_produto VARCHAR(30) NOT NULL
);

-- Criação da tabela precos
CREATE TABLE IF NOT EXISTS precos (
    id BIGSERIAL PRIMARY KEY,
    valor DECIMAL(10,2),
    data_alteracao DATE,
    hora_alteracao DATE,
    tipo_preco VARCHAR(20) NOT NULL,
    produto_id BIGINT NOT NULL,
    FOREIGN KEY (produto_id) REFERENCES produtos(id) ON DELETE CASCADE
);

-- Criação da tabela estoques
CREATE TABLE IF NOT EXISTS estoques (
    id BIGSERIAL PRIMARY KEY,
    quantidade DECIMAL(10,2),
    local_tanque VARCHAR(10) NOT NULL,
    local_endereco VARCHAR(10) NOT NULL,
    local_fabricacao VARCHAR(10) NOT NULL,
    data_validade DATE,
    tipo_estoque VARCHAR(10) NOT NULL
);

-- Criação da tabela vendas
CREATE TABLE IF NOT EXISTS vendas (
    id BIGSERIAL PRIMARY KEY,
    descricao_produto VARCHAR(100) NOT NULL,
    quantidade DECIMAL(10,2) NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL,
    data_venda DATE NOT NULL,
    hora_venda TIME NOT NULL,
    tipo_preco VARCHAR(30) NOT NULL,
    tipo_combustivel VARCHAR(30) NOT NULL,
    observacoes VARCHAR(500),
    estoque_id BIGINT,
    FOREIGN KEY (estoque_id) REFERENCES estoques(id) ON DELETE SET NULL
);

-- Criação da tabela custos
CREATE TABLE IF NOT EXISTS custos (
    id BIGSERIAL PRIMARY KEY,
    imposto DOUBLE PRECISION,
    frete DOUBLE PRECISION,
    seguro DOUBLE PRECISION,
    custo_variavel DOUBLE PRECISION,
    custo_fixo DOUBLE PRECISION,
    margem_lucro DOUBLE PRECISION,
    tipo_custo VARCHAR(10) NOT NULL
);
