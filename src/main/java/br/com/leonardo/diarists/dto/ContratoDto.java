package br.com.leonardo.diarists.dto;

import java.util.List;

public class ContratoDto {
	
	private String descricao;
	private List<UsuarioDto> usuarios;
	
	public String getDescricao() {
		return descricao;
	}
	public List<UsuarioDto> getUsuarios() {
		return usuarios;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setUsuarios(List<UsuarioDto> usuarios) {
		this.usuarios = usuarios;
	}
	
}
