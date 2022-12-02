package com.company.FinalProject.controller;

import com.company.FinalProject.models.Game;
import com.company.FinalProject.repositories.GameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);

        Game game2 = new Game();
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("E");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);

        Game game3 = new Game();
        game3.setTitle("Assasin's Creed Valhalla");
        game3.setEsrb_rating("M");
        game3.setDescription("Assassin's Creed Valhalla is a 2020 action role-playing video game.");
        game3.setPrice(new BigDecimal("59.99"));
        game3.setStudio("Ubisoft");
        game3.setQuantity(40);
        
        allGames.add(game);
        allGames.add(game2);
        allGames.add(game3);
    }

    @Test
    public void shouldCreateNewGameOnPostRequest() throws Exception
    {
        Game inputGame = new Game();
        inputGame.setTitle("Marvel's Spider-Man: Miles Morales");
        inputGame.setEsrb_rating("T");
        inputGame.setDescription("Marvel's Spider-Man: Miles Morales is a 2020 action-adventure game.");
        inputGame.setPrice(new BigDecimal("34.99"));
        inputGame.setStudio("Insomniac Games");
        inputGame.setQuantity(40);

        String inputJson = mapper.writeValueAsString(inputGame);

        doReturn(game).when(repo).save(inputGame);

        mockMvc.perform(
                        post("/games")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(gameJson));
    }

    @Test
    public void shouldReturnGameById() throws Exception
    {
        doReturn(Optional.of(game)).when(repo).findById(1);

        ResultActions result = mockMvc.perform(
                        get("/games/1"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(gameJson))
                );
    }

    @Test
    public void shouldReturnGameByStudio() throws Exception
    {
        doReturn(Optional.of(game)).when(repo).findAllGamesByStudio("Ubisoft");

        ResultActions result = mockMvc.perform(
                        get("/games/studio/ubisoft"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(gameJson))
                );
    }

    @Test
    public void shouldReturnGameByTitle() throws Exception
    {
        doReturn(Optional.of(game)).when(repo).findAllGamesByTitle("Little Big Planet");

        ResultActions result = mockMvc.perform(
                        get("/games/title/little_big_planet"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(gameJson))
                );
    }

    @Test
    public void shouldReturnGameByRating() throws Exception
    {
        doReturn(Optional.of(game)).when(repo).findAllGamesByEsrbRating("T");

        ResultActions result = mockMvc.perform(
                        get("/games/rating/T"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(gameJson))
                );
    }

    @Test
    public void shouldBStatusOkForNonExistentGameId() throws Exception
    {
        doReturn(Optional.empty()).when(repo).findById(3);

        mockMvc.perform(
                        get("/games/1234"))
                .andExpect(status().isOk()
                );

    }

    @Test
    public void shouldReturnAllGames() throws Exception {
        doReturn(allGames).when(repo).findAll();

        mockMvc.perform(
                        get("/games"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(allGamesJson)
                );
    }

    @Test
    public void shouldUpdateByIdAndReturn200StatusCode() throws Exception
    {
        mockMvc.perform(
                        put("/games")
                                .content(gameJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteByIdAndReturn200StatusCode() throws Exception {
        mockMvc.perform(delete("/games/2")).andExpect(status().isOk());
    }
}
