package com.company.FinalProject.controller;

import com.company.FinalProject.models.Game;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

public class GameControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    private Game game;
    private String gameJson;
    private List<Game> allGames = new ArrayList<>();
    private String allGamesJson;

    @Before
    public void setup() throws Exception
    {

    }

    @Test
    public void shouldCreateNewGameOnPostRequest() throws Exception
    {

    }

    @Test
    public void shouldReturnGameById() throws Exception
    {

    }

    @Test
    public void shouldBStatusOkForNonExistentGameId() throws Exception
    {

    }

    @Test
    public void shouldReturnAllGames() throws Exception
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
