package com.helixcry.zcc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.helixcry.zcc.controller.ZccController;
import com.helixcry.zcc.dto.ListContainer;
import com.helixcry.zcc.dto.TicketContainer;
import com.helixcry.zcc.service.ZccService;


@RunWith(MockitoJUnitRunner.class)
class ZccControllerTests {
	
	@InjectMocks
	private ZccController controller = new ZccController();

	@Mock
	private ZccService zccService = mock(ZccService.class);
	
	@BeforeEach
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void fetchAllPositiveTest() {
		when(zccService.fetchAllTickets()).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		final ResponseEntity<ListContainer> response = controller.fetchAll();
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	@Test
	// simulates service not being available
	void fetchAllNegativeTest() {
		when(zccService.fetchAllTickets()).thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
		final ResponseEntity<ListContainer> response = controller.fetchAll();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
	}
	
	@Test
	void fetchOneNegativeTest() {
		when(zccService.fetchTicket(Mockito.anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		final ResponseEntity<TicketContainer> response = controller.fetchOne(null);
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
	}
	
	@Test
	void fetchOnePositiveTest() {
		when(zccService.fetchTicket(Mockito.anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		final ResponseEntity<TicketContainer> response = controller.fetchOne((long) 1);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	
	
	@Test
	void fetchPaginatedPositiveTest() {
		when(zccService.fetchPaginated(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt(),Mockito.anyBoolean(),Mockito.anyBoolean())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		final ResponseEntity<ListContainer> response = controller.fetchPaginated("urldummy", "dummyURI", true, true, 25);
		assertEquals(HttpStatus.OK,response.getStatusCode());
	}
	@Test
	void fetchPaginatedNegativeTest() {
		when(zccService.fetchPaginated(Mockito.anyString(),Mockito.anyString(),Mockito.anyInt(),Mockito.anyBoolean(),Mockito.anyBoolean())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		final ResponseEntity<ListContainer> response = controller.fetchPaginated(null, "dummyURI", true, true, 25);
		final ResponseEntity<ListContainer> responsePrevNull = controller.fetchPaginated("dummyNext", null, true, true, 25);
		assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST,responsePrevNull.getStatusCode());
		
	}

	

}
