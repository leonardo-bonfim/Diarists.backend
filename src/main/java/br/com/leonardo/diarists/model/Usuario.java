package br.com.leonardo.diarists.model;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import br.com.leonardo.diarists.model.group.Alteracao;
import br.com.leonardo.diarists.model.group.Criacao;

@Entity
@Table
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message="Nome não pode ser nulo!", groups = {Criacao.class})
	private String nome;
	
	private String sobrenome;
	
	@Pattern(regexp="([M]{1}|[F]{1})", message="O sexo deve ser M ou F", groups = {Alteracao.class, Criacao.class})
	@Length(min=1, max=1, message="O CPF deve ter apenas 1 caractere", groups = {Alteracao.class, Criacao.class})
	@NotNull(message="Sexo não pode ser nulo!", groups = {Criacao.class})
	private String sexo;
	
	@CPF(groups = {Alteracao.class, Criacao.class})
	@Pattern(regexp="(\\d{3}[\\.]\\d{3}[\\.]\\d{3}[\\-]\\d{2})", message = "Formato de CPF inválido!", groups = {Alteracao.class, Criacao.class})
	@NotNull(message="Cpf não pode ser nulo!", groups = {Criacao.class})
	private String cpf;
	
	@Lob
	private byte[] foto;
	
	@Email(groups = {Alteracao.class, Criacao.class})
	@NotNull(message="Email não pode ser nulo!", groups = Criacao.class)
	private String email;
	
	@Length(min = 6, message="A senha deve ter 6 ou mais caracteres", groups = {Alteracao.class, Criacao.class})
	@NotNull(message="Senha não pode ser nulo!", groups = {Criacao.class})
	private String senha;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "id_usuario"),
			inverseJoinColumns = @JoinColumn(name = "id_permissao"))
	private List<Permissao> permissoes;
	
	@ManyToMany
	@JoinTable(
		name="usuario_contrato",
		joinColumns= @JoinColumn(name = "id_usuario"),
		inverseJoinColumns = @JoinColumn(name="id_contrato")
	)
	private List<Contrato> contratos;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="id_endereco")
	private Endereco endereco;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public List<Permissao> getPermissoes() {
		return permissoes;
	}

	public void setPermissoes(List<Permissao> permissoes) {
		this.permissoes = permissoes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contratos == null) ? 0 : contratos.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result + Arrays.hashCode(foto);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((permissoes == null) ? 0 : permissoes.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((sexo == null) ? 0 : sexo.hashCode());
		result = prime * result + ((sobrenome == null) ? 0 : sobrenome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (contratos == null) {
			if (other.contratos != null)
				return false;
		} else if (!contratos.equals(other.contratos))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (!Arrays.equals(foto, other.foto))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (permissoes == null) {
			if (other.permissoes != null)
				return false;
		} else if (!permissoes.equals(other.permissoes))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (sexo == null) {
			if (other.sexo != null)
				return false;
		} else if (!sexo.equals(other.sexo))
			return false;
		if (sobrenome == null) {
			if (other.sobrenome != null)
				return false;
		} else if (!sobrenome.equals(other.sobrenome))
			return false;
		return true;
	}
	
}
