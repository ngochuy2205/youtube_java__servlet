package com.poly.common;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QueryParamemter {
	private String key;
	private Object value;
	private TemporalType temporalType;
	private int posistion;
	public QueryParamemter(String key, Object value) {
		this.key=key;
		this.value=value;
	}
	
	public QueryParamemter(int posistion, Object value) {
		this.posistion = posistion;
		this.value=value;
	}

	public QueryParamemter(String key, Object value, TemporalType temporalType) {
		this.key = key;
		this.value = value;
		this.temporalType = temporalType;
	}
}