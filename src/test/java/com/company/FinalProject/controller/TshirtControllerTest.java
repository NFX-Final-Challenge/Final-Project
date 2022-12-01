package com.company.FinalProject.controller;

//<<<<<<< HEAD

//=======
//>>>>>>> origin/main
import com.company.FinalProject.models.Tshirt;
import com.company.FinalProject.repositories.TshirtRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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

    }

    @Test
    public void shouldCreateNewTshirtOnPostRequest() throws Exception
    {

    }

    @Test
    public void shouldReturnTshirtById() throws Exception
    {

    }

    @Test
    public void shouldBStatusOkForNonExistentTshirtId() throws Exception
    {

    }

    @Test
    public void shouldReturnAllTshirts() throws Exception
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
