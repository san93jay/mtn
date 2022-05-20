package com.sanjay.mtn;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sanjay.mtn.dto.InvoiceRequestDTO;
import com.sanjay.mtn.dto.InvoiceResponseDTO;

public class InVoiceSetUp {
	private final static ObjectMapper objectMapper = new ObjectMapper();
	private static InvoiceRequestDTO invoiceRequest;
	private static InvoiceResponseDTO invoiceResponse;
	private static List<InvoiceResponseDTO> invoiceResponseDTO;
	private static ResponseEntity<InvoiceResponseDTO> invoiceListResponseDTODetails = new ResponseEntity<InvoiceResponseDTO>(
			invoiceResponse, HttpStatus.OK);
	private static ResponseEntity<List<InvoiceResponseDTO>> allInvoiceListResponseDTODetails = new ResponseEntity<List<InvoiceResponseDTO>>(
			invoiceResponseDTO, HttpStatus.OK);

	@BeforeAll
	public static void initCreateInVoiceList() {
		try {
			File file = ResourceUtils.getFile("classpath:data/create-invoice-lists-request.json");
			String content = new String(Files.readAllBytes(file.toPath()));
			invoiceRequest = new ObjectMapper().readValue(content, new TypeReference<InvoiceRequestDTO>() {
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static InvoiceRequestDTO getShoppingListItemRequestDTOs() {
		return invoiceRequest;
	}

	@BeforeAll
	public static void initGetAllInvoiceByInvoiceId() {
		try {
			if (Objects.isNull(invoiceResponse)) {
				initCreateInVoiceList();
			}

			File file = ResourceUtils.getFile("classpath:data/invoice-response-dataset-by-invoiceId.json");
			String content = new String(Files.readAllBytes(file.toPath()));
			objectMapper.registerModule(new JavaTimeModule());
			invoiceResponse = objectMapper.readValue(content, InvoiceResponseDTO.class);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ResponseEntity<InvoiceResponseDTO> getAllInvoiceByInvoiceId() {
		return invoiceListResponseDTODetails;
	}

	@BeforeAll
	public static void initGetAllInvoice() {
		try {

			File file = ResourceUtils.getFile("classpath:data/invoice-response-all-dataset.json");
			String content = new String(Files.readAllBytes(file.toPath()));
			objectMapper.registerModule(new JavaTimeModule());
			invoiceResponseDTO = objectMapper.readValue(content, new TypeReference<>() {
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static ResponseEntity<List<InvoiceResponseDTO>> getAllInvoice() {
		return allInvoiceListResponseDTODetails;
	}

}
