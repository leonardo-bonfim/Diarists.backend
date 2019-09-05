package br.com.leonardo.diarists.dto;

import java.util.List;

public class ContratoDto {
	
	private String descricao;
	private List<String> envolvidos;
	
	public String getDescricao() {
		return descricao;
	}
	public List<String> getEnvolvidos() {
		return envolvidos;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public void setEnvolvidos(List<String> nomeContratante) {
		this.envolvidos = nomeContratante;
	}
	
}
