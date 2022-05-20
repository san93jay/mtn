package com.sanjay.mtn.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sanjay.mtn.dto.InvoiceRequestDTO;
import com.sanjay.mtn.dto.InvoiceResponseDTO;
import com.sanjay.mtn.dto.LineItemDTO;
import com.sanjay.mtn.entity.Invoice;
import com.sanjay.mtn.entity.LineItem;
import com.sanjay.mtn.exception.InValidInvoiceId;
import com.sanjay.mtn.exception.InvoiceNotFoundException;
import com.sanjay.mtn.repository.InvoiceRepository;
import com.sanjay.mtn.service.InvoiceService;
import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class InvoiceServiceImpl implements InvoiceService {
	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public InvoiceResponseDTO save(InvoiceRequestDTO invoiceRequestDTO) {
		List<LineItemDTO> LineItemDTOList = new ArrayList<LineItemDTO>();
		float vat = 0f;
		float total = 0f;
		float subTotal = 0f;
		InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
		try {
		Invoice invoice = modelMapper.map(invoiceRequestDTO, Invoice.class);
		invoice.setInvoiceDate(new Timestamp(System.currentTimeMillis()));
		Invoice invoice_details = invoiceRepository.save(invoice);
		List<LineItem> lineItem = invoiceRequestDTO.getLineItem();
		for (LineItem item : lineItem) {
			LineItemDTO lineItemDTO = allCalculation(item);
			vat = vat + lineItemDTO.getQuantity() * lineItemDTO.getUnitPrice();
			total = total + lineItemDTO.getLineItemTotal() + lineItemDTO.getUnitPrice();
			subTotal = subTotal + lineItemDTO.getLineItemTotal();
			LineItemDTOList.add(lineItemDTO);
		}
		invoiceResponseDTO = modelMapper.map(invoice_details, InvoiceResponseDTO.class);
		invoiceResponseDTO.setVat(vat);
		invoiceResponseDTO.setTotal(total);
		invoiceResponseDTO.setSubTotal(subTotal);
		invoiceResponseDTO.setLineItem(LineItemDTOList);
		}catch (Exception e) {
			log.error("error"+ e.getMessage());
		}
		return invoiceResponseDTO;
	}

	private LineItemDTO allCalculation(LineItem item) {
		LineItemDTO lineItemDTO = new LineItemDTO();
		try {
			float lineItemTotal = item.getUnitPrice() * item.getQuantity();
			lineItemDTO = modelMapper.map(item, LineItemDTO.class);
			lineItemDTO.setLineItemTotal(lineItemTotal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineItemDTO;

	}

	@Override
	public List<InvoiceResponseDTO> getAllInvoiceDetails() {
		List<InvoiceResponseDTO> invoiceResponseDTOList = new ArrayList<>();

		try {
			List<Invoice> invoice = invoiceRepository.findAll();
			calculateAllPrize(invoiceResponseDTOList, invoice);
			return invoiceResponseDTOList;

		} catch (InvoiceNotFoundException e) {
			log.error("in catch bloack : ", e.getMessage());
			throw new InvoiceNotFoundException("No Invoice found");
		}
	}

	private List<InvoiceResponseDTO> calculateAllPrize(List<InvoiceResponseDTO> invoiceResponseDTOList,
			List<Invoice> invoice) {
		InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
		try {
			for (Invoice invoiceDetails : invoice) {
				float vat = 0f;
				float total = 0f;
				float subTotal = 0f;
				List<LineItem> lineItem = invoiceDetails.getLineItem();
				List<LineItemDTO> LineItemDTOList = new ArrayList<LineItemDTO>();
				for (LineItem item : lineItem) {
					LineItemDTO lineItemDTO = allCalculation(item);
					vat = vat + lineItemDTO.getQuantity() * lineItemDTO.getUnitPrice();
					total = total + lineItemDTO.getLineItemTotal() + lineItemDTO.getUnitPrice();
					subTotal = subTotal + lineItemDTO.getLineItemTotal();
					LineItemDTOList.add(lineItemDTO);
				}
				invoiceResponseDTO = modelMapper.map(invoiceDetails, InvoiceResponseDTO.class);
				invoiceResponseDTO.setVat(vat);
				invoiceResponseDTO.setTotal(total);
				invoiceResponseDTO.setSubTotal(subTotal);
				invoiceResponseDTO.setLineItem(LineItemDTOList);
				invoiceResponseDTOList.add(invoiceResponseDTO);
			}

		} catch (InValidInvoiceId e) {
			log.error("in catch bloack : ", e.getMessage());
			throw new InValidInvoiceId("SomeThing went wrong!");
		}
		return invoiceResponseDTOList;
	}

	@Override
	public InvoiceResponseDTO getInvoiceDetailByInvoiceId(long invoiceId) {
		List<LineItemDTO> LineItemDTOList = new ArrayList<LineItemDTO>();
		float vat = 0f;
		float total = 0f;
		float subTotal = 0f;
		InvoiceResponseDTO invoiceResponseDTO = new InvoiceResponseDTO();
		try {
			Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
			if (invoice.isPresent()) {
				Invoice invoiceDetails = invoice.get();
				List<LineItem> lineItem = invoiceDetails.getLineItem();
				for (LineItem item : lineItem) {
					LineItemDTO lineItemDTO = allCalculation(item);
					vat = vat + lineItemDTO.getQuantity() * lineItemDTO.getUnitPrice();
					total = total + lineItemDTO.getLineItemTotal() + lineItemDTO.getUnitPrice();
					subTotal = subTotal + lineItemDTO.getLineItemTotal();
					LineItemDTOList.add(lineItemDTO);
				}
				invoiceResponseDTO = modelMapper.map(invoiceDetails, InvoiceResponseDTO.class);
				invoiceResponseDTO.setVat(vat);
				invoiceResponseDTO.setTotal(total);
				invoiceResponseDTO.setSubTotal(subTotal);
				invoiceResponseDTO.setLineItem(LineItemDTOList);
			}

		} catch (InValidInvoiceId e) {

			log.error("in catch bloack : ", e.getMessage());
			throw new InValidInvoiceId("Invalid Invoice Id");
		}catch (Exception e) {
			log.error("in catch bloack : ", e.getMessage());
		}
		return invoiceResponseDTO;
	}

}
