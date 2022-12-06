package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Game;
import com.company.FinalProject.models.Invoice;
import com.company.FinalProject.service.ServiceLayer;
import com.company.FinalProject.viewModel.InvoiceViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceController.class)
public class InvoiceControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Invoice invoice;
    private String invoiceJson;

    @Before
    public void setup() throws Exception {
        Game game = new Game();
        game.setId(1);
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("12.99"));
        game.setStudio("Media Molecule");
        game.setQuantity(100);
    }
    @Test
    public void shouldCreateNewInvoiceOnPostRequest() throws Exception
    {
        InvoiceViewModel inputInvoice = new InvoiceViewModel();
        inputInvoice.setName("Customer 1");
        inputInvoice.setStreet("100 Main Street");
        inputInvoice.setCity("Clovis");
        //Tax: ('WY', .04);
        inputInvoice.setState("CA");
        inputInvoice.setZipcode("93612");
        inputInvoice.setItemId(1);
        inputInvoice.setItemType("Game");
        inputInvoice.setQuantity(12);

        String inputJson = mapper.writeValueAsString(inputInvoice);

        InvoiceViewModel returnInvoice = new InvoiceViewModel();
        returnInvoice.setName("Customer 1");
        returnInvoice.setStreet("100 Main Street");
        returnInvoice.setCity("Clovis");
        returnInvoice.setState("CA");
        returnInvoice.setZipcode("93612");
        returnInvoice.setItemId(1);
        returnInvoice.setItemType("Game");
        returnInvoice.setQuantity(12);
        returnInvoice.setUnit_price(new BigDecimal("12.99"));
        returnInvoice.setProcessing_fee(new BigDecimal("16.98"));
        returnInvoice.setSubtotal(new BigDecimal("155.88"));
        returnInvoice.setTax(new BigDecimal("9.35"));
        returnInvoice.setTotal(new BigDecimal("182.21"));

        String returnJson = mapper.writeValueAsString(returnInvoice);
        doReturn(returnInvoice).when(repo).saveInvoice(inputInvoice);

        mockMvc.perform(
                        post("/invoices")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(returnJson));
    }
}
