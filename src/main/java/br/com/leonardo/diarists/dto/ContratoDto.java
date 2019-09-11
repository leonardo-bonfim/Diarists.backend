package br.com.leonardo.diarists.dto;

import java.util.List;

public class ContratoDto {
	
	private String descricao;
	private List<UsuarioDto> usuarios;
	private String distancia;
	
	public String getDistancia() {
		return distancia;
	}
	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<UsuarioDto> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(List<UsuarioDto> usuarios) {
		this.usuarios = usuarios;
	}
	
}
