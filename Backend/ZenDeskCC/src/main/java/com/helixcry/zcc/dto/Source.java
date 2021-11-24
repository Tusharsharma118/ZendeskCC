package com.helixcry.zcc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter 
@NoArgsConstructor
@AllArgsConstructor
public class Source {

	private Object from;
	private Object to;
	private String rel;
}
