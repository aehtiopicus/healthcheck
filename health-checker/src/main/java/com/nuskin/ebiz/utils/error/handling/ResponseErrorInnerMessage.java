package com.nuskin.ebiz.utils.error.handling;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResponseErrorInnerMessage {

	@XmlElement
	private String error;
	
	@XmlElement
	private ResponseErrorInnerMessage rootElement;

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ResponseErrorInnerMessage getRootElement() {
		return rootElement;
	}

	public void setRootElement(ResponseErrorInnerMessage rootElement) {
		this.rootElement = rootElement;
	}
	
	
	
	
}
