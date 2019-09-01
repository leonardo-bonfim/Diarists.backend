package br.com.leonardo.diarists.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("diarists")
public class DiaristsApiProperty {
	
	private String originPermitida = "http://localhost:8100";
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}
	
}
