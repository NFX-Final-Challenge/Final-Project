package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Invoice;
import com.company.FinalProject.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoiceController {
    @Autowired
    InvoiceRepository repository;

    @GetMapping("/invoice")
    public List<Invoice> getAllInvoices() {
        return repository.findAll();
    }

    @GetMapping("/invoice/{id}")
    public Invoice getInvoiceById(@PathVariable int id) {
        Optional<Invoice> returnVal = repository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }


    //TO DO
    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice generateInvoice(@RequestBody Invoice invoice) {

        return null;
    }

    @PutMapping("/invoice")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateInvoice(@RequestBody Invoice invoice) {
        repository.save(invoice);
    }

    @DeleteMapping("/invoice/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable int id) {
        repository.deleteById(id);
    }
}


