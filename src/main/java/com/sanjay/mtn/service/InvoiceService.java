package com.sanjay.mtn.service;

import java.util.List;

import com.sanjay.mtn.dto.InvoiceRequestDTO;
import com.sanjay.mtn.dto.InvoiceResponseDTO;
import com.sanjay.mtn.exception.InvoiceNotFoundException;

public interface InvoiceService {
	public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO);

	public List<InvoiceResponseDTO> getAllInvoiceDetails() throws InvoiceNotFoundException;

	public InvoiceResponseDTO getInvoiceDetailByInvoiceId(long invoiceId) throws InvoiceNotFoundException;

}
