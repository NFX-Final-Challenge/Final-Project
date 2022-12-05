package com.company.FinalProject.repositories;


import com.company.FinalProject.models.Console;
import com.company.FinalProject.models.Game;
import com.company.FinalProject.models.Invoice;
import com.company.FinalProject.models.Tshirt;
import com.company.FinalProject.service.ServiceLayer;
import com.company.FinalProject.viewModel.InvoiceViewModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class InvoiceRepositoryTest {

    @Autowired
    GameRepository gameRepository;
    @Autowired
    ConsoleRepository consoleRepository;
    @Autowired
    TshirtRepository tshirtRepository;
    @Autowired
    ProcessingFeeRepository processingFeeRepository;
    @Autowired
    SalesTaxRepository salesTaxRepository;
    @Autowired
    InvoiceRepository invoiceRepository;
    @Autowired
    ServiceLayer serviceLayer;


    @Before
    public void setUp() throws Exception {
        gameRepository.deleteAll();
        consoleRepository.deleteAll();
        tshirtRepository.deleteAll();
        invoiceRepository.deleteAll();
    }

    @Test
    public void getNewInvoiceGame(){

        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("12.99"));
        game.setStudio("Media Molecule");
        game.setQuantity(100);

        game = gameRepository.save(game);

        int gameID = game.getId();

        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setName("Customer 1");
        invoice.setStreet("100 Main Street");
        invoice.setCity("Clovis");
        invoice.setState("CA");
        invoice.setZipcode("93612");
        invoice.setItemId(gameID);
        invoice.setItemType("Game");
        invoice.setQuantity(12);

        invoice = serviceLayer.saveInvoice(invoice);

        assertEquals(BigDecimal.valueOf(182.21), invoice.getTotal());
        assertEquals(BigDecimal.valueOf(16.98), invoice.getProcessing_fee());
        assertEquals(BigDecimal.valueOf(155.88), invoice.getSubtotal());

        Optional<Game> newGame = gameRepository.findById(game.getId());
        int gameQuantity = newGame.get().getQuantity();
        int updatedQuantity = 100-12;
        assertEquals(updatedQuantity, gameQuantity);
    }

    @Test
    public void getNewInvoiceConsole() {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825 GB");
        console.setProcessor("AMD Zen 2 CPU");
        console.setPrice(new BigDecimal("693.81"));
        console.setQuantity(20);

        console = consoleRepository.save(console);

        int consoleID = console.getId();

        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setName("Customer 1");
        invoice.setStreet("100 Main Street");
        invoice.setCity("Clovis");
        //Tax: ('AL', .05),
        invoice.setState("AL");
        invoice.setZipcode("93612");
        invoice.setItemId(consoleID);
        invoice.setItemType("Console");
        invoice.setQuantity(1);

        invoice = serviceLayer.saveInvoice(invoice);

        assertEquals(BigDecimal.valueOf(743.49), invoice.getTotal());
        assertEquals(BigDecimal.valueOf(14.99), invoice.getProcessing_fee());
        assertEquals(BigDecimal.valueOf(693.81), invoice.getSubtotal());

        Optional<Console> newConsole = consoleRepository.findById(console.getId());
        int consoleQuantity = newConsole.get().getQuantity();
        int updatedQuantity = 19;
        assertEquals(updatedQuantity, consoleQuantity);
    }

    @Test
    public void getNewInvoiceTshirt() {

        Tshirt tshirt= new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("Super Mario Graphic Tee");
        tshirt.setPrice(new BigDecimal(15.45));
        tshirt.setQuantity(150);

        tshirt = tshirtRepository.save(tshirt);

        int tshirtID = tshirt.getT_shirt_id();

        InvoiceViewModel invoice = new InvoiceViewModel();
        invoice.setName("Customer 1");
        invoice.setStreet("100 Main Street");
        invoice.setCity("Clovis");
        //Tax: ('WY', .04);
        invoice.setState("WY");
        invoice.setZipcode("93612");
        invoice.setItemId(tshirtID);
        invoice.setItemType("T-Shirt");
        invoice.setQuantity(9);

        invoice = serviceLayer.saveInvoice(invoice);

        assertEquals(BigDecimal.valueOf(146.59), invoice.getTotal());
        assertEquals(BigDecimal.valueOf(1.98), invoice.getProcessing_fee());
        assertEquals(BigDecimal.valueOf(139.05), invoice.getSubtotal());

        Optional<Tshirt> newTshirt = tshirtRepository.findById(tshirt.getT_shirt_id());
        int tshirtQuantity = newTshirt.get().getQuantity();
        int updatedQuantity = 150-9;
        assertEquals(updatedQuantity, tshirtQuantity);
    }
}
