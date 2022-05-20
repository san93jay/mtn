package com.sanjay.mtn.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sanjay.mtn.InVoiceSetUp;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Integration test scenarios for Invoice features")
public class InvoiceControllerTests extends InVoiceSetUp {
	private static final String INVOICE_ID = "{invoiceId}";
	
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    
    private final String CREATE_INVOICE_URL_ENDPOINT = "/invoices";
    private final String GET_ALL_INVOICE_BY_INVOICE_ID="/invoices/{invoiceId}";
    private final String GET_ALL_INVOICE="/invoices";
    @PostConstruct
    public void init() {
        // module required to deserialize LocalDateTime
        this.objectMapper.registerModule(new JavaTimeModule());
    }
    
    @Test
    @Order(1)
    @DisplayName("test case for creating invoice")
    void createInvoice_withLineItem_shouldCreatInvoice() throws Exception {
    String request = objectMapper.writeValueAsString(getShoppingListItemRequestDTOs());
    this.mockMvc.perform(post(String.format(CREATE_INVOICE_URL_ENDPOINT))
    		.servletPath(String.format(CREATE_INVOICE_URL_ENDPOINT)).contentType(MediaType.APPLICATION_JSON).content(request))
    		.andExpect(status().is(HttpStatus.OK.value())).andReturn();
  
    }
    
    @Test
    @Order(2)
    @DisplayName("test case for getting all Invoce list belonging InVoiceId")
    void getInvoice_withLineItem_By_InvoiceId() throws Exception {
    	String invoiceId="1";
    	String contentString = objectMapper.writeValueAsString(getAllInvoiceByInvoiceId());
        this.mockMvc.perform(get(GET_ALL_INVOICE_BY_INVOICE_ID, invoiceId).content(contentString)
                .servletPath(GET_ALL_INVOICE_BY_INVOICE_ID.replace(INVOICE_ID, invoiceId))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    @Order(3)
    @DisplayName("test case for getting all Invoce list")
    void getAllInvoice_withLineItem() throws Exception {
    	String contentString = objectMapper.writeValueAsString(getAllInvoice());
        this.mockMvc.perform(get(GET_ALL_INVOICE).content(contentString)
                .servletPath(GET_ALL_INVOICE)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().is(HttpStatus.OK.value()));
    }
}
