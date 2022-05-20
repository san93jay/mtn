package com.sanjay.mtn.exception;

public class InvoiceNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
    public InvoiceNotFoundException(String message) {
        super(message);
        this.message = message;
    }
    public InvoiceNotFoundException() {
    }

}
