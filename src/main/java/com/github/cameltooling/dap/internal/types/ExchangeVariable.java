package com.github.cameltooling.dap.internal.types;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "exchangeVariable")
public class ExchangeVariable implements Serializable {

	private static final long serialVersionUID = 4693511937508953820L;

	private String key;
	private String type;
	private String value;

	@XmlAttribute(name = "key")
	public String getKey() {
		return this.key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@XmlAttribute(name = "type")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlValue
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
