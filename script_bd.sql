-- Criação do banco de dados 
CREATE DATABASE tarefas_db
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
	LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

-- ENUM: Status da Tarefa
CREATE TYPE status_tarefa AS ENUM (
    'PENDENTE',
    'EM_ANDAMENTO',
    'CONCLUIDA',
    'CANCELADA'
);

-- TABELA: tarefa
CREATE TABLE IF NOT EXISTS tarefa (
    id              BIGSERIAL       PRIMARY KEY,
    nome            VARCHAR(150)    NOT NULL,
    descricao       TEXT,
    status          VARCHAR(20)     NOT NULL DEFAULT 'PENDENTE',
    observacoes     TEXT,
    data_criacao    TIMESTAMP       NOT NULL DEFAULT NOW(),
    data_atualizacao TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- COMENTÁRIOS DAS COLUNAS
COMMENT ON TABLE  tarefa                    IS 'Tabela de tarefas do sistema';
COMMENT ON COLUMN tarefa.id                 IS 'Identificador único da tarefa';
COMMENT ON COLUMN tarefa.nome               IS 'Nome/título da tarefa';
COMMENT ON COLUMN tarefa.descricao          IS 'Descrição detalhada da tarefa';
COMMENT ON COLUMN tarefa.status             IS 'Status atual da tarefa: PENDENTE | EM_ANDAMENTO | CONCLUIDA | CANCELADA';
COMMENT ON COLUMN tarefa.observacoes        IS 'Observações adicionais sobre a tarefa';
COMMENT ON COLUMN tarefa.data_criacao       IS 'Data e hora de criação do registro';
COMMENT ON COLUMN tarefa.data_atualizacao   IS 'Data e hora da última atualização do registro';

-- ÍNDICES
CREATE INDEX idx_tarefa_status ON tarefa(status);
CREATE INDEX idx_tarefa_nome   ON tarefa(nome);

-- DADOS INICIAIS DE EXEMPLO
INSERT INTO tarefa (nome, descricao, status, observacoes) VALUES
    ('Comprar mantimentos', 'Ir ao mercado comprar os itens da lista', 'PENDENTE', 'Lista no celular'),
    ('Reunião de equipe', 'Reunião semanal de alinhamento do time', 'EM_ANDAMENTO', 'Sala de reunião 2'),
    ('Relatório mensal', 'Elaborar relatório de atividades do mês', 'CONCLUIDA', 'Enviado por e-mail'),
    ('Revisar documentação', 'Revisar e atualizar a documentação do projeto', 'PENDENTE', NULL);

SELECT * FROM tarefa;