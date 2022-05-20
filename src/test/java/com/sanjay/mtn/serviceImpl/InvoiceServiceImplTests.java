package com.sanjay.mtn.serviceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.sanjay.mtn.dto.InvoiceRequestDTO;
import com.sanjay.mtn.dto.InvoiceResponseDTO;
import com.sanjay.mtn.entity.Invoice;
import com.sanjay.mtn.entity.LineItem;
import com.sanjay.mtn.repository.InvoiceRepository;
import com.sanjay.mtn.service.InvoiceService;
import com.sanjay.mtn.util.InVoiceListTestDataSetProvider;





@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DisplayName("JUnit test scenarios for InVoiceService class")
public class InvoiceServiceImplTests  {
	
	@Autowired
	private InvoiceService invoiceService;
	
	@MockBean
	private InvoiceRepository invoiceRepository;
	
	@Autowired
    private InVoiceListTestDataSetProvider inVoiceListTestDataSetProvider;

	    @Test
	    @DisplayName("test case for creating Invoice with Line items giving a valid response")
	    void createInvoice_validRequestWithLineItems() {
		 
        	 Optional<InvoiceRequestDTO> optionalInvoiceList = inVoiceListTestDataSetProvider
	                .getInvoiceRequestDTODatasetByClient("invoice-with-items-1");
	        assertFalse(optionalInvoiceList.isEmpty());
	        Mockito.when(invoiceRepository.save(any())).thenReturn(optionalInvoiceList.get());
	        InvoiceRequestDTO InvoiceRequestDTO=optionalInvoiceList.get();
	        InvoiceResponseDTO response = invoiceService.save(InvoiceRequestDTO);
	        ResponseEntity<InvoiceResponseDTO> responseEntity=new ResponseEntity<InvoiceResponseDTO>(response, HttpStatus.OK);
	        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

	        assertNotNull(responseEntity.getBody());		 
	 }
	 
	    @Test
	    @DisplayName("test case for getting all InVoice list")
	    void GetAllInVoiceList_Get_All_List_shouldGetAllInVoice() {
	    	Optional<List<Invoice>> optionalInvoiceList = inVoiceListTestDataSetProvider
	                .getInVoiceModelDTODataset("invoice-with-items-1");
	        assertFalse(optionalInvoiceList.isEmpty());
	        when(invoiceRepository.findAll()).thenReturn(optionalInvoiceList.get());
	        List<InvoiceResponseDTO> responseDTO = invoiceService.getAllInvoiceDetails();
	        assertEquals(4, responseDTO.size());
	        assertNotNull(responseDTO.get(0).getLineItem().get(0).getQuantity());
	    }
	    
	    @Test
	    @DisplayName("test case for getting all InVoice list by InVoice Id")
	    void GetAllInVoiceListByInVoiceId_Get_All_List() {
	    	long invoiceId=1L;
	        when(invoiceRepository.findById(invoiceId)).thenReturn(getAllInvoiceByInvoiceId());
	        InvoiceResponseDTO responseDTO = invoiceService.getInvoiceDetailByInvoiceId(invoiceId);
	        assertEquals(1, responseDTO.getId());
	        assertNotNull(responseDTO.getLineItem().get(0).getQuantity());
	    }
	    
	    private Optional<Invoice> getAllInvoiceByInvoiceId() {
	        List<LineItem> lineItem=new ArrayList<LineItem>();
	        lineItem.add(new LineItem(1, 12, "line Item", 2000));
	        lineItem.add(new LineItem(2, 10, "line Item 2", 3000));
	    	Invoice invoice = new Invoice();
	    	invoice.setId(1);
	    	invoice.setVatRate(12);
	    	invoice.setClient("MTN");
	    	invoice.setLineItem(lineItem);
	        return Optional.of(invoice);
	    }
}
