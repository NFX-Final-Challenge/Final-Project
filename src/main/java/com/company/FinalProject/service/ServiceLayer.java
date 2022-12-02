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
import java.util.Locale;
import java.util.Optional;

@Component
public class ServiceLayer {

    private ConsoleRepository consoleRepository;
    private GameRepository gameRepository;
    private TshirtRepository tshirtRepository;
    private ProcessingFeeRepository processingFeeRepository;
    private SalesTaxRepository salesTaxRepository;
    private InvoiceRepository invoiceRepository;

    @Autowired
    public ServiceLayer(ConsoleRepository consoleRepository, GameRepository gameRepository,
                        TshirtRepository tshirtRepository, ProcessingFeeRepository processingFeeRepository, SalesTaxRepository salesTaxRepository,
                        InvoiceRepository invoiceRepository) {
        this.consoleRepository = consoleRepository;
        this.gameRepository = gameRepository;
        this.tshirtRepository = tshirtRepository;
        this.processingFeeRepository = processingFeeRepository;
        this.salesTaxRepository = salesTaxRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public InvoiceViewModel saveInvoice(InvoiceViewModel invoiceViewModel) {

        BigDecimal totalPrice = new BigDecimal(0);
        int inventory;
        BigDecimal price;

        //Get the state and qty purchased from invoice
        String state = invoiceViewModel.getState();
        int quantity = invoiceViewModel.getQuantity();

        //String itemType = invoiceViewModel.getItem_type().toLowerCase();
        // Get the type of product
        if (invoiceViewModel.getItem_type() == "Game"){
             // Get the associated game
            Optional<Game> item = gameRepository.findById(invoiceViewModel.getItem_id());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory){
                throw new IllegalArgumentException("Not enough games in stock.");
            }
        }

        else if (invoiceViewModel.getItem_type() == "console"){
            // Get the associated tshirt
            Optional<Console> item = consoleRepository.findById(invoiceViewModel.getItem_id());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory){
                throw new IllegalArgumentException("Not enough console in stock.");
            }}

        else if (invoiceViewModel.getItem_type() == "tshirt") {
            // Get the associated console
            Optional<Tshirt> item = tshirtRepository.findById(invoiceViewModel.getItem_id());
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
        BigDecimal processingFeeForItem = processingFeeRepository.findFeeByProductType(invoiceViewModel.getItem_type());
        BigDecimal extraFee = new BigDecimal(15.49);
        BigDecimal processingFeeTotal = (quantity > 10 ? (processingFeeForItem.add(extraFee)) : processingFeeForItem);


        totalPrice = totalPrice.add(subtotal);
        totalPrice = totalPrice.add(stateTaxTotal);
        totalPrice = totalPrice.add(processingFeeTotal);

        // Assemble the Invoice
        Invoice newInvoice = new Invoice();
        newInvoice.setCity(invoiceViewModel.getCity());
        newInvoice.setQuantity(invoiceViewModel.getQuantity());
        newInvoice.setItem_id(invoiceViewModel.getItem_id());
        newInvoice.setName(invoiceViewModel.getName());
        newInvoice.setState(invoiceViewModel.getState());
        newInvoice.setZipcode(invoiceViewModel.getZipcode());
        newInvoice.setStreet(invoiceViewModel.getStreet());
        newInvoice.setProcessing_fee(processingFeeTotal);
        newInvoice.setItem_type(invoiceViewModel.getItem_type());
        newInvoice.setUnit_price(price);
        newInvoice.setSubtotal(subtotal);
        newInvoice.setTax(stateTaxTotal);
        newInvoice.setTotal(totalPrice);

        // Return the AlbumViewModel
        newInvoice = invoiceRepository.save(newInvoice);

        //Complete invoiceViewModel
        invoiceViewModel.setId(newInvoice.getId());
        invoiceViewModel.setProcessing_fee(newInvoice.getProcessing_fee());
        invoiceViewModel.setUnit_price(newInvoice.getUnit_price());
        invoiceViewModel.setSubtotal(newInvoice.getSubtotal());
        invoiceViewModel.setTax(newInvoice.getTax());
        invoiceViewModel.setTotal(newInvoice.getTotal());

        return invoiceViewModel;
    }


}