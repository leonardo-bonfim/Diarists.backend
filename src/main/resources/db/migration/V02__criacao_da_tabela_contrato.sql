create table contrato (
	id bigint not null auto_increment,
	descricao varchar(255) not null,
	bairro varchar(255),
	cep varchar(255),
	cidade varchar(255),
	complemento varchar(255),
	logradouro varchar(255),
	numero varchar(255),
	uf varchar(255),
	latitude varchar(255),
	longitude varchar(255),
	restricao varchar(255),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_contrato (
	usuario_id bigint not null,
	contrato_id bigint not null,
	FOREIGN KEY(usuario_id) REFERENCES usuario(id),
	FOREIGN KEY(contrato_id) REFERENCES contrato(id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;