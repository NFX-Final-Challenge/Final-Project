package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository  extends JpaRepository<Invoice, Integer> {
}
