package com.company.FinalProject.controller;

import com.company.FinalProject.model.Console;
import com.company.FinalProject.repository.ConsoleRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

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

    }

    @Test
    public void shouldCreateNewConsoleOnPostRequest() throws Exception
    {

    }

    @Test
    public void shouldReturnConsoleById() throws Exception
    {

    }

    @Test
    public void shouldBStatusOkForNonExistentConsoleId() throws Exception
    {

    }

    @Test
    public void shouldReturnAllConsoles() throws Exception
    {

    }

    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception
    {

    }

    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception
    {

    }
}
