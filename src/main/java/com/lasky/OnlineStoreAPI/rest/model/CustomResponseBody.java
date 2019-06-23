package com.lasky.OnlineStoreAPI.rest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseBody<Type> {
	
	public CustomResponseBody() { }
	
	public CustomResponseBody(Type body, String message) {
		this.body = body;
		this.message = message;
	}
	
	private Type body;
	private String message;
	
}

