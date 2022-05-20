package com.sanjay.mtn.util;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanjay.mtn.dto.InvoiceRequestDTO;
import com.sanjay.mtn.entity.Invoice;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class InVoiceListTestDataSetProvider {
	private final ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, InvoiceRequestDTO> InvoiceRequestDataset;
    private Map<String, Invoice> invoiceDataset;
    private Map<String, List<Invoice>> invoiceModelDataset;
    
    @PostConstruct
    private void init() {
        readInVoiceListRequestDataset();
        readInVoiceModelDataset();
        readInVoiceDataset();
    }

	private void readInVoiceDataset() {
        try {
            File file = ResourceUtils.getFile("classpath:data/invoice-model-dataset.json");
            String content = new String(Files.readAllBytes(file.toPath()));

            invoiceDataset = objectMapper.readValue(content, new TypeReference<>() {
            });
        } catch (Exception ex) {
            log.error("Failed to initialize invoice request dataset");
        }
    }

	private void readInVoiceModelDataset() {
        try {
            File file = ResourceUtils.getFile("classpath:data/invoice-model-dataset.json");
            String content = new String(Files.readAllBytes(file.toPath()));

            invoiceModelDataset = objectMapper.readValue(content, new TypeReference<>() {
            });
        } catch (Exception ex) {
            log.error("Failed to initialize invoice list request dataset");
        }
    }

	private void readInVoiceListRequestDataset() {
		  try {
	            File file = ResourceUtils.getFile("classpath:data/create-invoice-lists-dataset.json");
	            String content = new String(Files.readAllBytes(file.toPath()));
	            TypeReference<Map<String, InvoiceRequestDTO>> typeRef = new TypeReference<Map<String, InvoiceRequestDTO>>() {};
	            InvoiceRequestDataset = objectMapper.readValue(content, typeRef);
	        } catch (Exception ex) {
	            log.error("Failed to initialize InVoice request dataset");
	        }
		
	}
	
	 public Optional<InvoiceRequestDTO> getInvoiceRequestDTODatasetByClient(String name) {
		    InvoiceRequestDTO requestDTO = InvoiceRequestDataset.get(name);
	        return Objects.isNull(requestDTO) ? Optional.empty() : Optional.of(requestDTO);
	    }
	 
	 public Optional<List<Invoice>> getInVoiceModelDTODataset(String id) {
		  List<Invoice> invoiceResponseDTO = invoiceModelDataset.get(id);
	        return Objects.isNull(invoiceResponseDTO) ? Optional.empty() : Optional.of(invoiceResponseDTO);
	    }
	 
	 
	 public Optional<Invoice> getInVoiceModelDTODatasetById(String id) {
		     Invoice invoiceResponse = invoiceDataset.get(id);
	        return Objects.isNull(invoiceResponse) ? Optional.empty() : Optional.of(invoiceResponse);
	    }


}
