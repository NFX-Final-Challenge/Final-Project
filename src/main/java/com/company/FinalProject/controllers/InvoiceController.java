package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Invoice;
import com.company.FinalProject.repositories.InvoiceRepository;
import com.company.FinalProject.service.ServiceLayer;
import com.company.FinalProject.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoiceController {

    @Autowired
    ServiceLayer serviceLayer;

    //TO DO
    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceViewModel addInvoice(@RequestBody InvoiceViewModel invoice) {
        return serviceLayer.saveInvoice(invoice);

    }

}


