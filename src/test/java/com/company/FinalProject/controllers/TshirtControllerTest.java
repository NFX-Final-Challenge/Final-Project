package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Tshirt;
import com.company.FinalProject.repositories.TshirtRepository;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TshirtController.class)
public class TshirtControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TshirtRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Tshirt tshirt;
    private String tshirtJson;
    private List<Tshirt> allTshirts = new ArrayList<>();
    private String allTshirtsJson;

    @Before
    public void setup() throws Exception
    {
        Tshirt tshirt= new Tshirt();
        tshirt.setSize("Large");
        tshirt.setColor("Black");
        tshirt.setDescription("Super Mario Graphic Tee");
        tshirt.setPrice(new BigDecimal(15.99));
        tshirt.setQuantity(15);

        Tshirt tshirt2= new Tshirt();
        tshirt2.setSize("Medium");
        tshirt2.setColor("Black");
        tshirt2.setDescription("Super Mario Graphic Tee");
        tshirt2.setPrice(new BigDecimal(15.99));
        tshirt2.setQuantity(23);
       
        allTshirts.add(tshirt);
        allTshirts.add(tshirt2);
    }

    @Test
    public void shouldCreateNewTshirtOnPostRequest() throws Exception
    {
        Tshirt inputTshirt = new Tshirt();
        inputTshirt.setSize("Small");
        inputTshirt.setColor("Blue");
        inputTshirt.setDescription("Super Mario Graphic Tee");
        inputTshirt.setPrice(new BigDecimal(15.99));
        inputTshirt.setQuantity(7);

        String inputJson = mapper.writeValueAsString(inputTshirt);

        doReturn(tshirt).when(repo).save(inputTshirt);

        mockMvc.perform(
                        post("/tshirts")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(tshirtJson));
    }

    @Test
    public void shouldReturnTshirtById() throws Exception
    {
        doReturn(Optional.of(tshirt)).when(repo).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/tshirts/1"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(tshirtJson))
                );
    }

    @Test
    public void shouldReturnTshirtByColor() throws Exception
    {
        doReturn(Optional.of(tshirt)).when(repo).findTshirtByColor("Black");

        ResultActions result = mockMvc.perform(
                        get("/tshirts/color/black"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(tshirtJson))
                );
    }

    @Test
    public void shouldReturnTshirtBySize() throws Exception
    {
        doReturn(Optional.of(tshirt)).when(repo).findTshirtBySize("Medium");

        ResultActions result = mockMvc.perform(
                        get("/tshirts/size/medium"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(tshirtJson))
                );
    }

    @Test
    public void shouldBStatusOkForNonExistentTshirtId() throws Exception
    {
        doReturn(Optional.empty()).when(repo).findById(3);

        mockMvc.perform(
                        get("/tshirts/1234"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void shouldReturnAllTshirts() throws Exception {
        doReturn(allTshirts).when(repo).findAll();

        mockMvc.perform(
                        get("/tshirts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allTshirtsJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception
    {
        mockMvc.perform(
                        put("/tshirts")
                                .content(tshirtJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        mockMvc.perform(delete("/tshirts/2")).andExpect(status().isOk());
    }
}
