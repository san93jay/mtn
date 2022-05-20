package com.sanjay.mtn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sanjay.mtn.dto.InvoiceRequestDTO;
import com.sanjay.mtn.dto.InvoiceResponseDTO;
import com.sanjay.mtn.exception.InvoiceNotFoundException;
import com.sanjay.mtn.service.InvoiceService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class InVoiceController {
	@Autowired
	private InvoiceService invoiceService;
	
	    @Value(value = "${data.exception.invoiceNotFound}")
	    private String invoiceNotFound;
	    @Value(value = "${data.exception.inValidInvoidId}")
	    private String InvalidInvoiceId;

	@PostMapping("/invoices")
	public ResponseEntity<InvoiceResponseDTO> saveInvoiceDetails(@RequestBody InvoiceRequestDTO invoiceRequestDTO) {
		InvoiceResponseDTO invoiceResponseDTO=null; 
		try {
			invoiceResponseDTO= invoiceService.save(invoiceRequestDTO);
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return new ResponseEntity<InvoiceResponseDTO>(invoiceResponseDTO, HttpStatus.OK);
	}

	@GetMapping("/invoices")
	public ResponseEntity<List<InvoiceResponseDTO>> getAllInvoiceDetails() {
		List<InvoiceResponseDTO> invoiceResponseDTO=null;
		try {
			invoiceResponseDTO = invoiceService.getAllInvoiceDetails();
		}catch (InvoiceNotFoundException e) {
			throw new InvoiceNotFoundException();
		}
		return new ResponseEntity<List<InvoiceResponseDTO>>(invoiceResponseDTO, HttpStatus.OK);
	}

	@GetMapping("/invoices/{invoiceId}")
	public ResponseEntity<InvoiceResponseDTO> getInvoiceDetailsById(@PathVariable(value = "invoiceId") long invoiceId) {
		log.info("invoiceId in controller  :" + invoiceId);
		InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
		try {
			invoiceResponseDTO = invoiceService.getInvoiceDetailByInvoiceId(invoiceId);
		} catch (Exception e) {
			throw new InvoiceNotFoundException(InvalidInvoiceId);
		}
		return new ResponseEntity<InvoiceResponseDTO>(invoiceResponseDTO, HttpStatus.OK);
	}

}
