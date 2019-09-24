CREATE TABLE endereco (
	id bigint not null auto_increment,
	id_logradouro BIGINT,
	cep varchar(20),
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE logradouro (
	id bigint not null auto_increment,
	id_bairro BIGINT,
	nome varchar(50),
	numero varchar(10),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE bairro (
	id bigint not null auto_increment,
	id_cidade BIGINT,
	nome varchar(20),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE cidade (
	id bigint not null auto_increment,
	id_uf BIGINT,
	nome varchar(20),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE UF (
	id bigint not null auto_increment,
	sigla varchar(20),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE endereco
ADD CONSTRAINT FK_EnderecoLogradouro
FOREIGN KEY (id_logradouro) REFERENCES logradouro(id);

ALTER TABLE logradouro
ADD CONSTRAINT FK_LogradouroBairro
FOREIGN KEY (id_bairro) REFERENCES bairro(id);

ALTER TABLE bairro
ADD CONSTRAINT FK_BairroCidade
FOREIGN KEY (id_cidade) REFERENCES cidade(id);

ALTER TABLE cidade
ADD CONSTRAINT FK_CidadeUF
FOREIGN KEY (id_uf) REFERENCES UF(id);
