package com.helixcry.zcc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;

import com.helixcry.zcc.dto.ListContainer;
import com.helixcry.zcc.dto.TicketContainer;
import com.helixcry.zcc.service.ZccService;

@RunWith(SpringRunner.class)
@RestClientTest(ZccService.class)
class ZccServiceTests {

//	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
//	private RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
//	@Mock
//	private Environment env = mock(Environment.class);
//	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
//	private RestTemplate restTemplate = mock(RestTemplate.class);
//	 https://zcchelix.zendesk.com
	 @Autowired
	   private MockRestServiceServer server;
	 @Autowired
	 private ZccService service;
//	@InjectMocks
//	private ZccService service = new ZccService(restTemplateBuilder, env);

	
//	@BeforeEach
//	public void initMocks() {
//		MockitoAnnotations.openMocks(this);
//		when(restTemplateBuilder.basicAuthentication(Mockito.anyString(), Mockito.anyString())).thenReturn(restTemplateBuilder);
//		when(restTemplateBuilder.build()).thenReturn(restTemplate);
//		//service = new ZccService(restTemplateBuilder, env);
//		
//	}
	@Test
	void fetchAllTicketsPositive() {
		server.expect(requestTo("/domain/api/v2/tickets.json")).andRespond(withSuccess());
		//when(restTemplate.exchange(Mockito.anyString(),HttpMethod.GET,null,ListContainer.class)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		final ResponseEntity<ListContainer> response = service.fetchAllTickets();
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	@Test
	void fetchAllTicketsNegative() {
		server.expect(requestTo("/domain/api/v2/tickets.json")).andRespond((response) -> { throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED); });
		final ResponseEntity<ListContainer> response = service.fetchAllTickets();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	void fetchPaginatedPositiveEmpty() {
		// case for fresh request
		server.expect(requestTo("domain/api/v2/tickets.json?page[size]=25")).andRespond(withSuccess());
		final ResponseEntity<ListContainer> responseEmptyURI = service.fetchPaginated("","prev",25, true,true);
		assertEquals(HttpStatus.OK,responseEmptyURI.getStatusCode());
		
		
	}
	@Test
	void fetchPaginatedPositiveNext() {
		//assumes next page request is of the type https://tryingnext.com"
		server.expect(requestTo("https://tryingnext.com")).andRespond(withSuccess());
		final ResponseEntity<ListContainer> responseNext = service.fetchPaginated("https://tryingnext.com","prev",25, true,true);
		assertEquals(HttpStatus.OK,responseNext.getStatusCode());	
	}
	@Test
	void fetchPaginatedPositivePrev() {
//		// for prev request https://tryingprev.com
		server.expect(requestTo("https://tryingprev.com")).andRespond(withSuccess());
		final ResponseEntity<ListContainer> responsePrev = service.fetchPaginated("https://tryingnext.com","https://tryingprev.com",25, false,true);
		assertEquals(HttpStatus.OK,responsePrev.getStatusCode());	
	}
	@Test
	void fetchPaginatedNegative() {
		server.expect(requestTo("https://tryingnext.com")).andRespond((response) -> { throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED); });
		final ResponseEntity<ListContainer> response = service.fetchPaginated("https://tryingnext.com","prev",25, true,true);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}
	
	@Test
	void fetchTicketPositive() {
//		// for prev request https://tryingprev.com
		server.expect(requestTo("domain/api/v2/tickets/1")).andRespond(withSuccess());
		final ResponseEntity<TicketContainer> response = service.fetchTicket((long) 1);
		assertEquals(HttpStatus.OK,response.getStatusCode());	
	}
	@Test
	void fetchTicketNegative() {
		server.expect(requestTo("domain/api/v2/tickets/1")).andRespond((response) -> { throw new HttpClientErrorException(HttpStatus.EXPECTATION_FAILED); });
		final ResponseEntity<TicketContainer> response = service.fetchTicket((long) 1);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());	
	}
}
