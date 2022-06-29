package com.piyushgoel.blog.exceptions.object;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppException {
	
	private String code;
	private String message;
	private Integer status;
	private LocalDateTime timestamp;
	private List<InvalidParameter> invalidParameters;
	
}
