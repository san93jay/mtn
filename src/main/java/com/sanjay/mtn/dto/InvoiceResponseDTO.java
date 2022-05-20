package com.sanjay.mtn.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponseDTO {
	private long id;
	private String client;
	private int vatRate;
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	private List<LineItemDTO> lineItem;
	private float vat;
	private float total;
	private float subTotal;

}
