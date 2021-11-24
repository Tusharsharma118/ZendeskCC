package com.helixcry.zcc.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaginatedRequest {
	String nextURI;
	String prevURI;
	Boolean next;
	Boolean hasNext;
	Integer pageSize;
}
