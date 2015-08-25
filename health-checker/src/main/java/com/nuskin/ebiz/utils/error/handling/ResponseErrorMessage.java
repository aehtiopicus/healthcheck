package com.nuskin.ebiz.utils.error.handling;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement(name = "serviceErrorResponse")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ResponseErrorMessage {

	private Integer status;
	
	private String error;
	
	@XmlElement
	private List<ResponseErrorInnerMessage> errors;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<ResponseErrorInnerMessage> getErrors() {
		return errors;
	}

	public void setErrors(List<ResponseErrorInnerMessage> errors) {
		this.errors = errors;
	}

	
	
	
}
