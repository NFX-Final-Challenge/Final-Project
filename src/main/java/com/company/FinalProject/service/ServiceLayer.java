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

    private Invoice buildInvoiceViewModel(Invoice invoice) {

        BigDecimal totalPrice = new BigDecimal(0);
        int inventory;
        BigDecimal price;

        //Get the state and qty purchased from invoice
        String state = invoice.getState();
        int quantity = invoice.getQuantity();

        // Get the type of product
        if (invoice.getItem_type() == "game"){
             // Get the associated game
            Optional<Game> item = gameRepository.findById(invoice.getItem_id());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory){
                throw new IllegalArgumentException("Not enough games in stock.");
            }
        }

        else if (invoice.getItem_type() == "console"){
            // Get the associated tshirt
            Optional<Console> item = consoleRepository.findById(invoice.getItem_id());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory){
                throw new IllegalArgumentException("Not enough console in stock.");
            }}

        else if (invoice.getItem_type() == "tshirt") {
            // Get the associated console
            Optional<Tshirt> item = tshirtRepository.findById(invoice.getItem_id());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory){
                throw new IllegalArgumentException("Not enough tshirts in stock.");
            }}

        else {
            throw new IllegalArgumentException("Item type not recognized.");
        }

        //Quantity ordered * price
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));

        //Look up stateTax
        BigDecimal stateTax = salesTaxRepository.findRateByState(state);
        //Calculate sales tax/stateTax
        BigDecimal stateTaxTotal =  stateTax.multiply(subtotal);

        //Calculate processing fee
        BigDecimal processingFeeForItem = processingFeeRepository.findFeeByProductType(invoice.getItem_type());
        BigDecimal extraFee = new BigDecimal(15.49);
        BigDecimal processingFeeTotal = (quantity > 10 ? (processingFeeForItem.add(extraFee)) : processingFeeForItem);


        totalPrice = totalPrice.add(subtotal);
        totalPrice = totalPrice.add(stateTaxTotal);
        totalPrice = totalPrice.add(processingFeeTotal);

        // Assemble the Invoice, missing id
        Invoice newInvoice = new Invoice();
        newInvoice.setCity(invoice.getCity());
        newInvoice.setQuantity(invoice.getQuantity());
        newInvoice.setItem_id(invoice.getItem_id());
        newInvoice.setName(invoice.getName());
        newInvoice.setState(invoice.getState());
        newInvoice.setZipcode(invoice.getZipcode());
        newInvoice.setStreet(invoice.getStreet());
        newInvoice.setProcessing_fee(processingFeeTotal);
        newInvoice.setItem_type(invoice.getItem_type());
        newInvoice.setUnit_price(price);
        newInvoice.setSubtotal(subtotal);
        newInvoice.setTax(stateTaxTotal);
        newInvoice.setProcessing_fee(processingFeeTotal);
        newInvoice.setTotal(totalPrice);

        // Return the AlbumViewModel

        return newInvoice;
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