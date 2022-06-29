package com.piyushgoel.blog.dataTransferObject;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Response<T> extends PagingInfo {
	private List<T> items;
	
}
