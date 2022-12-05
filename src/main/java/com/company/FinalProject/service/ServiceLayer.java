package com.company.FinalProject.service;

import com.company.FinalProject.models.*;
import com.company.FinalProject.repositories.*;
import com.company.FinalProject.viewModel.InvoiceViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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

        //Quantity must be greater than 0
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity ordered insufficient.");
        }

        //Zipcode must be valid
        String zipcode = invoiceViewModel.getZipcode();
        if (!validateZipCode(zipcode)){
            throw new IllegalArgumentException("Zipcode for this order is invalid.");
        }

        // Get the type of product
        if (invoiceViewModel.getItemType().equals("Game")){
             // Get the associated game
            Optional<Game> item = gameRepository.findById(invoiceViewModel.getItemId());

            if (item.isPresent()) {
                inventory = item.get().getQuantity();
                price = item.get().getPrice();

                if (quantity > inventory) {
                    throw new IllegalArgumentException("Not enough games in stock.");
                }

                Game game = new Game();
                int newInventory = inventory - quantity;
                game.setId(item.get().getId());
                game.setTitle(item.get().getTitle());
                game.setDescription(item.get().getDescription());
                game.setPrice(item.get().getPrice());
                game.setStudio(item.get().getStudio());
                game.setEsrb_rating(item.get().getEsrb_rating());
                game.setQuantity(newInventory);
                game = gameRepository.save(game);

            }

            else {
                throw new IllegalArgumentException("Could not find a game with this ID.");
            }
        }

        else if (invoiceViewModel.getItemType().equals("Console")){
            // Get the associated console
            Optional<Console> item = consoleRepository.findById(invoiceViewModel.getItemId());

            if (item.isPresent()) {
                inventory = item.get().getQuantity();
                price = item.get().getPrice();

                if (quantity > inventory){
                    throw new IllegalArgumentException("Not enough consoles in stock.");
                }

                Console console = new Console();
                int newInventory = inventory - quantity;
                console.setId(item.get().getId());
                console.setModel(item.get().getModel());
                console.setManufacturer(item.get().getManufacturer());
                console.setMemoryAmount(item.get().getMemoryAmount());
                console.setProcessor(item.get().getProcessor());
                console.setPrice(item.get().getPrice());
                console.setQuantity(newInventory);
                console = consoleRepository.save(console);
            }

            else {
                throw new IllegalArgumentException("Could not find a console with this ID.");
            }
        }

        else if (invoiceViewModel.getItemType().equals("T-Shirt")) {
            // Get the associated tshirt
            Optional<Tshirt> item = tshirtRepository.findById(invoiceViewModel.getItemId());

            if (item.isPresent()) {
                inventory = item.get().getQuantity();
                price = item.get().getPrice();

                if (quantity > inventory){
                    throw new IllegalArgumentException("Not enough tshirts in stock.");
                }

                Tshirt tshirt = new Tshirt();
                int newInventory = inventory - quantity;
                tshirt.setT_shirt_id(item.get().getT_shirt_id());
                tshirt.setDescription(item.get().getDescription());
                tshirt.setPrice(item.get().getPrice());
                tshirt.setColor(item.get().getColor());
                tshirt.setSize(item.get().getSize());
                tshirt.setQuantity(newInventory);
                tshirt = tshirtRepository.save(tshirt);
            }

            else {
                throw new IllegalArgumentException("Could not find a T-shirt with this ID.");
            }
        }

        else {
            throw new IllegalArgumentException("Item not recognized:" + invoiceViewModel.getItemType()  );
        }

        //Quantity ordered * price
        BigDecimal subtotal = price.multiply(BigDecimal.valueOf(quantity));

        //Look up stateTax
        SalesTax stateTaxObj = salesTaxRepository.findRateByState(state);
        BigDecimal stateTax = stateTaxObj.getRate();
        //Compute sales tax/stateTax
        BigDecimal stateTaxTotal =  stateTax.multiply(subtotal);

        //Compute processing fee
        ProcessingFee processingFeeForItemObj = processingFeeRepository.findFeeByProductType(invoiceViewModel.getItemType());
        BigDecimal processingFeeForItem = processingFeeForItemObj.getFee();
        BigDecimal extraFee = BigDecimal.valueOf(15.49);
        BigDecimal processingFeeTotal = (quantity > 10 ? (processingFeeForItem.add(extraFee)) : processingFeeForItem);

        //Compute total price
        totalPrice = totalPrice.add(subtotal);
        totalPrice = totalPrice.add(stateTaxTotal);
        totalPrice = totalPrice.add(processingFeeTotal);
        totalPrice = totalPrice.setScale(2, RoundingMode.FLOOR);


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


    private boolean validateZipCode(String zipcode){
        if (zipcode != null) {
            boolean isZipcodeValidLength = (zipcode.length() == 5);
            boolean isZipcodeValidNum = (zipcode.matches("[0-9]+"));

            boolean isZipcodeValid = ( isZipcodeValidLength && isZipcodeValidNum);
            return isZipcodeValid;
        }

        else {
            throw new IllegalArgumentException("Zipcode missing from invoice");
        }

    }

}