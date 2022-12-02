package com.company.FinalProject.controller;

import com.company.FinalProject.controllers.ConsoleController;
import com.company.FinalProject.models.Console;
import com.company.FinalProject.repositories.ConsoleRepository;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ConsoleController.class)
public class ConsoleControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConsoleRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Console console;
    private String consoleJson;
    private List<Console> allConsoles = new ArrayList<>();
    private String allConsolesJson;

    @Before
    public void setup() throws Exception
    {
        Console console = new Console();
        console.setModel("Playstation 5");
        console.setManufacturer("Sony");
        console.setMemoryAmount("825 GB");
        console.setProcessor("AMD Zen 2 CPU");
        console.setPrice(new BigDecimal("693.80"));
        console.setQuantity(2);

        Console console2 = new Console();
        console2.setModel("Playstation 5 Digital Edition");
        console2.setManufacturer("Sony");
        console2.setMemoryAmount("825 GB");
        console2.setProcessor("AMD Zen 2 CPU");
        console2.setPrice(new BigDecimal("569.99"));
        console2.setQuantity(7);

        allConsoles.add(console);
        allConsoles.add(console2);

        allConsolesJson = mapper.writeValueAsString(allConsoles);
    }

    @Test
    public void shouldCreateNewConsoleOnPostRequest() throws Exception
    {
        Console inputConsole = new Console();
        inputConsole.setModel("X Box Series X");
        inputConsole.setManufacturer("Microsoft");
        inputConsole.setMemoryAmount("1 TB");
        inputConsole.setProcessor("AMD Zen 2 CPU");
        inputConsole.setPrice(new BigDecimal("499.00"));
        inputConsole.setQuantity(10);

        String inputJson = mapper.writeValueAsString(inputConsole);

        doReturn(console).when(repo).save(inputConsole);

        mockMvc.perform(
                        post("/consoles")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(consoleJson));
    }

    @Test
    public void shouldReturnConsoleById() throws Exception
    {
        doReturn(Optional.of(console)).when(repo).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/consoles/1"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(consoleJson))
                );
    }

    @Test
    public void shouldReturnConsoleByManufacturer() throws Exception
    {
        doReturn(Optional.of(console)).when(repo).findAllConsolesByManufacturer("Sony");

        ResultActions result = mockMvc.perform(
                        get("/consoles/manufacturer/Sony"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(consoleJson))
                );
    }

    @Test
    public void shouldBStatusOkForNonExistentConsoleId() throws Exception
    {
        doReturn(Optional.empty()).when(repo).findById(3);

        mockMvc.perform(
                        get("/consoles/1234"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void shouldReturnAllConsoles() throws Exception {
        doReturn(allConsoles).when(repo).findAll();

        mockMvc.perform(
                        get("/consoles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allConsolesJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception
    {
        mockMvc.perform(
                        put("/consoles")
                                .content(consoleJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        mockMvc.perform(delete("/consoles/2")).andExpect(status().isOk());
    }

}
