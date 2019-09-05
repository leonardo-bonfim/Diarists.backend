CREATE TABLE usuario (
	id BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	sobrenome VARCHAR(100) NOT NULL,
	sexo CHAR(1) NOT NULL,
	cpf VARCHAR(14) NOT NULL,
	logradouro VARCHAR(100),
    numero VARCHAR(10),
    complemento VARCHAR(20),
    bairro VARCHAR(20),
    cep VARCHAR(10),
    cidade VARCHAR(20),
    uf VARCHAR(20),
    foto BLOB,
	email VARCHAR(50),
	senha VARCHAR(150)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE permissao (
	id BIGINT(20) PRIMARY KEY,
	descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_permissao (
	id_usuario BIGINT(20) NOT NULL,
	id_permissao BIGINT(20) NOT NULL,
	PRIMARY KEY (id_usuario, id_permissao),
	FOREIGN KEY (id_usuario) REFERENCES usuario(id),
	FOREIGN KEY (id_permissao) REFERENCES permissao(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO usuario (id, nome, sobrenome, sexo, cpf, email, senha) values (1, 'Cadastro', '0', '0', '0', 'cadastro', '$2a$10$sWMoGSl6NoYEjKE1fhR3fucswZxmwOdSlBMCEJzkCjx45R.ElaYFm');
INSERT INTO usuario (id, nome, sobrenome, sexo, cpf, email, senha) values (2, 'Administrador', '1', 'M', '23115115123', 'admin', '$2a$10$v6INs3LnW3SPyK6CXSNAS.Fm6skO0K99dRRGPB68Wdkpcm.Xp0ohq');

-- PERMISSÕES

INSERT INTO permissao (id, descricao) values (1, 'ROLE_CADASTRO');
INSERT INTO permissao (id, descricao) values (2, 'ROLE_USUARIO');

-- INSERIR PERMISSÕES AOS USUARIOS

INSERT INTO usuario_permissao (id_usuario, id_permissao) values (1, 1);
INSERT INTO usuario_permissao (id_usuario, id_permissao) values (2, 2);