package com.piyushgoel.blog.dataTransferObject;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PagingInfo {
	
	private int pageNumber;
	private int pageSize;
	private int totalElements;
	private int totalPages;
	private boolean islastPage;
	
	
}
