package com.sanjay.mtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineItemDTO {
	private long id;
	private int quantity;
	private String description;
	private float unitPrice;
	private float lineItemTotal;

}
