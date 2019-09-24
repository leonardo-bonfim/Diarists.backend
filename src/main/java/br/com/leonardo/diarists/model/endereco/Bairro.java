package br.com.leonardo.diarists.model.endereco;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Bairro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@OneToMany(mappedBy="bairro")
	private List<Logradouro> logradouros;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_cidade")
	private Cidade cidade;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	public String getNome() {
		return nome;
	}
	public List<Logradouro> getLogradouros() {
		return logradouros;
	}
	public void setLogradouros(List<Logradouro> logradouros) {
		this.logradouros = logradouros;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
	
}
