package br.com.leonardo.diarists.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("diarists")
public class DiaristsApiProperty {
	
	private String originPermitida = "*";
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}
	
}
