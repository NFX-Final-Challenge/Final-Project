package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Invoice;
import com.company.FinalProject.repositories.InvoiceRepository;
import com.company.FinalProject.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoiceController {

    //TO DO
    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice generateInvoice(@RequestBody InvoiceViewModel invoice) {

        return null;
    }


}


