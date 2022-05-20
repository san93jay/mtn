package com.sanjay.mtn.dto;

import java.util.List;

import com.sanjay.mtn.entity.LineItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequestDTO {
	
	private String client;
	private int vatRate;
	private List<LineItem> lineItem;

}
