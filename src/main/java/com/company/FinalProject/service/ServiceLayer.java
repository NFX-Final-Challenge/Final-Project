package com.company.FinalProject.service;

import com.company.FinalProject.models.Invoice;
import com.company.FinalProject.models.Tshirt;
import com.company.FinalProject.models.Console;
import com.company.FinalProject.models.Game;
import com.company.FinalProject.repositories.*;
import com.company.FinalProject.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;
    private TshirtRepository tshirtRepository;
    private ProcessingFeeRepository processingFeeRepository;
    private SalesTaxRepository salesTaxRepository;

    @Autowired
    public ServiceLayer(ConsoleRepository consoleRepository, GameRepository gameRepository,
                        TshirtRepository tshirtRepository, ProcessingFeeRepository processingFeeRepository, SalesTaxRepository salesTaxRepository ) {
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tshirtRepository = tshirtRepository;
        this.processingFeeRepository = processingFeeRepository;
        this.salesTaxRepository = salesTaxRepository;
    }

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel viewModel) {


        return null;
    }

    public InvoiceViewModel findInvoice(int id) {

        return null;
    }

    private InvoiceViewModel buildInvoiceViewModel(Invoice invoice) {

        BigDecimal totalPrice;

        //Get the state and qty purchased from invoice
        String state = invoice.getState();
        int quantity = invoice.getQuantity();

        // Get the type of product
        if (invoice.getItem_type() == "game"){
             // Get the associated game
            Optional<Game> item = gameRepository.findById(invoice.getItem_id());
            int inventory = item.get().getQuantity();
            BigDecimal price = item.get().getPrice();
        }

        else if (invoice.getItem_type() == "console"){
            // Get the associated tshirt
            Optional<Console> item = consoleRepository.findById(invoice.getItem_id());
            int inventory = item.get().getQuantity();
            BigDecimal price = item.get().getPrice();}

        else if (invoice.getItem_type() == "tshirt") {
            // Get the associated console
            Optional<Tshirt> item = tshirtRepository.findById(invoice.getItem_id());
            int inventory = item.get().getQuantity();
            BigDecimal price = item.get().getPrice();}

        else {
            //throw error?
        }

        //Look up stateTax
        BigDecimal stateTax = salesTaxRepository.findRateByState(state);



        // Assemble the InvoiceViewModel
        InvoiceViewModel ivm = new InvoiceViewModel();



        // Return the AlbumViewModel
        return ivm;
    }

    public List<InvoiceViewModel> findAllInvoices() {

        return null;

    }

    @Transactional
    public void updateInvoice(InvoiceViewModel viewModel) {

    }

    @Transactional
    public void removeInvoice(int id) {

    }

}