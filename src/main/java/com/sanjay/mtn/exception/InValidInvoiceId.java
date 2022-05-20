package com.sanjay.mtn.exception;

public class InValidInvoiceId extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
    
	public InValidInvoiceId(String message){
		super(message);
		this.message = message;
	}
	public InValidInvoiceId() {
		
	}
	
}
