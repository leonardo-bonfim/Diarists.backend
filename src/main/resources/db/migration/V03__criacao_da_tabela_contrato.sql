create table contrato (
	id bigint NOT NULL auto_increment,
	id_endereco BIGINT,
	descricao VARCHAR(255) not null,
	latitude VARCHAR(255),
	longitude VARCHAR(255),
	restricao VARCHAR(255),
	PRIMARY KEY (id),
	FOREIGN KEY (id_endereco) REFERENCES endereco(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE usuario_contrato (
	id_usuario bigint NOT NULL,
	id_contrato bigint NOT NULL,
	FOREIGN KEY(id_usuario) REFERENCES usuario(id),
	FOREIGN KEY(id_contrato) REFERENCES contrato(id) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;