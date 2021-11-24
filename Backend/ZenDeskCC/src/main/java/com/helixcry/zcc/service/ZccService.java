package com.helixcry.zcc.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.helixcry.zcc.dto.ListContainer;
import com.helixcry.zcc.dto.TicketContainer;

@Service
@PropertySource("classpath:application.properties")
public class ZccService {

	private final RestTemplate restTemplate;
	
	@Value("${domain}")
	private String subDomain;
	@Value("${fetchAll}")
	private String fetchAllUri;
	@Value("${fetchOne}")
	private String fetchTicket;
	@Value("${paginationKey}")
	private String pagination;
	
	public ZccService(RestTemplateBuilder restTemplateBuilder,Environment env) {
		this.restTemplate = restTemplateBuilder.basicAuthentication(env.getProperty("user"),env.getProperty("pass")).build();
	}
	
	public ResponseEntity<ListContainer> fetchAllTickets() {
		String builtURI = subDomain + fetchAllUri;
		ResponseEntity<ListContainer> ticketsResponse =  restTemplate.exchange(builtURI, HttpMethod.GET, null, ListContainer.class);	
		return ticketsResponse;
	}
	
	public ResponseEntity<ListContainer> fetchPaginated(String nextURI,String prevURI,Integer pageSize,boolean next,boolean hasNext) {
		StringBuilder url = new StringBuilder();
		if(nextURI.length() == 0) {
			url.append(subDomain).append(fetchAllUri).append(pagination).append(pageSize);
		}else {
			url.append(next ? nextURI:prevURI);
		}
		ResponseEntity<ListContainer> ticketsResponse =  restTemplate.getForEntity(URI.create(url.toString()),ListContainer.class);
		return ticketsResponse;
	}
	
	public ResponseEntity<TicketContainer> fetchTicket(Long id) {
		String builtURI = subDomain + fetchTicket + id;
		ResponseEntity<TicketContainer> ticketsResponse =  restTemplate.exchange(URI.create(builtURI), HttpMethod.GET, null, TicketContainer.class);
		return ticketsResponse;
	}
	
	
	
}
