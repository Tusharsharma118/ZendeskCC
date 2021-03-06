package com.helixcry.zcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helixcry.zcc.dto.ListContainer;
import com.helixcry.zcc.dto.TicketContainer;
import com.helixcry.zcc.service.ZccService;

@CrossOrigin
@RestController
public class ZccController {
	
	@Autowired
	private ZccService service;
	
	// for local testing
	@GetMapping("/all")
	public ResponseEntity<ListContainer> fetchAll(){
		return service.fetchAllTickets();
	}
	//can be extended further for on demand ticket requests
	@GetMapping("/ticket/{id}")
	public ResponseEntity<TicketContainer> fetchOne(@PathVariable Long id){
		if(id == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return service.fetchTicket(id);
	}
	
	@GetMapping("/tickets")
	public ResponseEntity<ListContainer> fetchPaginated(@RequestParam(required = false) String nextURI,@RequestParam(required = false) String prevURI,@RequestParam(required = false) boolean next, @RequestParam boolean hasNext, @RequestParam int pageSize){
		// null check/validate all input should be usually done in a separate method 
		// doing it here for brevity
		if(nextURI == null || prevURI == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		ResponseEntity<ListContainer> response = service.fetchPaginated(nextURI, prevURI,pageSize,next,hasNext);
		return response;
	}
}
