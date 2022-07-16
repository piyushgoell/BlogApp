package com.piyushgoel.blog.dataTransferObject;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Auth {
	private String access_token;
	private String refresh_token;
	private Date issued_at;
	private Date expires_at;
	private String token_type;
}
