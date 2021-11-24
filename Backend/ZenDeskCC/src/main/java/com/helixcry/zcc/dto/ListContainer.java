package com.helixcry.zcc.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ListContainer {
	
	private List<Ticket> tickets;
	private Meta meta;
	private Links links;
}
