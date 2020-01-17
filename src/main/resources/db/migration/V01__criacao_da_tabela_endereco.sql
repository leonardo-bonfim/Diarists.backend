create table endereco (
	id bigint not null auto_increment,
	bairro varchar(20),
	cep varchar(10),
	cidade varchar(20),
	complemento varchar(50),
	logradouro varchar(50),
	numero varchar(10),
	uf varchar(5),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
