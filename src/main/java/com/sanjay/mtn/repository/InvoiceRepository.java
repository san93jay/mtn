package com.sanjay.mtn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sanjay.mtn.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long>{

}
