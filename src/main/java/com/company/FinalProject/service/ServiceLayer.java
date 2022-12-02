package com.company.FinalProject.service;

import com.company.FinalProject.models.*;
import com.company.FinalProject.repositories.*;
import com.company.FinalProject.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        String itemType = "Game";

        //String itemType = invoiceViewModel.getItem_type().toLowerCase();
        // Get the type of product
        if (itemType == "Game"){
             // Get the associated game
            Optional<Game> item = gameRepository.findById(invoiceViewModel.getItemId());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory) {
                throw new IllegalArgumentException("Not enough games in stock.");
            }

            if (item.isPresent()) {

                int newInventory = inventory - quantity;
                item.get().setId(invoiceViewModel.getItemId());
                item.get().setQuantity(newInventory);
                //gameRepository.save(item);
            }
        }

        else if (invoiceViewModel.getItemType() == "Console"){
            // Get the associated console
            Optional<Console> item = consoleRepository.findById(invoiceViewModel.getItemId());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory){
                throw new IllegalArgumentException("Not enough console in stock.");
            }

            if (item.isPresent()) {
                Console updatedItem = new Console();
                int newInventory = inventory - quantity;
                updatedItem.setId(invoiceViewModel.getItemId());
                updatedItem.setQuantity(newInventory);
                updatedItem = consoleRepository.save(updatedItem);
            }
        }

        else if (invoiceViewModel.getItemType() == "T-Shirt") {
            // Get the associated tshirt
            Optional<Tshirt> item = tshirtRepository.findById(invoiceViewModel.getItemId());
            inventory = item.get().getQuantity();
            price = item.get().getPrice();
            if (quantity > inventory){
                throw new IllegalArgumentException("Not enough tshirts in stock.");
            }
            if (item.isPresent()) {
                Tshirt updatedItem = new Tshirt();
                int newInventory = inventory - quantity;
                updatedItem.setT_shirt_id(invoiceViewModel.getItemId());
                updatedItem.setQuantity(newInventory);
                updatedItem = tshirtRepository.save(updatedItem);
            }
        }

        else {
            throw new IllegalArgumentException("Item type not recognized.");
        }

        //Quantity ordered * price
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));

        //Look up stateTax
        SalesTax stateTaxObj = salesTaxRepository.findRateByState(state);
        BigDecimal stateTax = stateTaxObj.getRate();
        //Calculate sales tax/stateTax
        BigDecimal stateTaxTotal =  stateTax.multiply(subtotal);

        //Calculate processing fee
        ProcessingFee processingFeeForItemObj = processingFeeRepository.findFeeByProductType(itemType);
        BigDecimal processingFeeForItem = processingFeeForItemObj.getFee();
        BigDecimal extraFee = BigDecimal.valueOf(15.49);
        BigDecimal processingFeeTotal = (quantity > 10 ? (processingFeeForItem.add(extraFee)) : processingFeeForItem);


        totalPrice = totalPrice.add(subtotal);
        totalPrice = totalPrice.add(stateTaxTotal);
        totalPrice = totalPrice.add(processingFeeTotal);

        // Assemble the Invoice
        Invoice newInvoice = new Invoice();
        newInvoice.setCity(invoiceViewModel.getCity());
        newInvoice.setQuantity(invoiceViewModel.getQuantity());
        newInvoice.setItemId(invoiceViewModel.getItemId());
        newInvoice.setName(invoiceViewModel.getName());
        newInvoice.setState(invoiceViewModel.getState());
        newInvoice.setZipcode(invoiceViewModel.getZipcode());
        newInvoice.setStreet(invoiceViewModel.getStreet());
        newInvoice.setProcessing_fee(processingFeeTotal);
        newInvoice.setItemType(invoiceViewModel.getItemType());
        newInvoice.setUnit_price(price);
        newInvoice.setSubtotal(subtotal);
        newInvoice.setTax(stateTaxTotal);
        newInvoice.setTotal(totalPrice);

        // Return the InvoiceViewModel
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