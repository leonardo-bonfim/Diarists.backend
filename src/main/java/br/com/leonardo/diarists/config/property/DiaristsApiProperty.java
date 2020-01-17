package br.com.leonardo.diarists.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("diarists")
public class DiaristsApiProperty {
	
	private boolean enableHttps = false;
	private String originPermitida = "*";
	
	public String getOriginPermitida() {
		return originPermitida;
	}

	public void setOriginPermitida(String originPermitida) {
		this.originPermitida = originPermitida;
	}

	public boolean isEnableHttps() {
		return enableHttps;
	}

	public void setEnableHttps(boolean enableHttps) {
		this.enableHttps = enableHttps;
	}
	
}
