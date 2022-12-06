package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Game;

import com.company.FinalProject.repositories.GameRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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
        game = new Game();
        game.setId(1);
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);

        gameJson = mapper.writeValueAsString(game);

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

        allGamesJson = mapper.writeValueAsString(allGames);
    }

    @Test
    public void shouldCreateNewGameOnPostRequest() throws Exception
    {
        game = new Game();
        game.setId(1);
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);

        gameJson = mapper.writeValueAsString(game);

        Game inputGame = new Game();
        inputGame.setId(1);
        inputGame.setTitle("Little Big Planet");
        inputGame.setEsrb_rating("E");
        inputGame.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        inputGame.setPrice(new BigDecimal("29.90"));
        inputGame.setStudio("Media Molecule");
        inputGame.setQuantity(10);

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
        Game game3 = new Game();
        game3.setTitle("Assasin's Creed Valhalla");
        game3.setEsrb_rating("M");
        game3.setDescription("Assassin's Creed Valhalla is a 2020 action role-playing video game.");
        game3.setPrice(new BigDecimal("59.99"));
        game3.setStudio("Ubisoft");
        game3.setQuantity(40);

        List<Game> allGamesUbisoft = new ArrayList<>();
        allGamesUbisoft.add(game3);

        doReturn(game3).when(repo).save(game3);

        String inputJson = mapper.writeValueAsString(game3);

        allGamesJson = mapper.writeValueAsString(allGamesUbisoft);

        doReturn(allGamesUbisoft).when(repo).findAllGamesByStudio("Ubisoft");

        mockMvc.perform(
                        post("/games")
                                .content(inputJson)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(inputJson));

        ResultActions result = mockMvc.perform(
                        get("/games/studio/Ubisoft"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(allGamesJson))
                );
    }

    @Test
    public void shouldReturnGameByTitle() throws Exception
    {
        Game game2 = new Game();
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("E");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);

        List<Game> allGamesByTitle = new ArrayList<>();
        allGamesByTitle.add(game2);

        doReturn(game2).when(repo).save(game2);

        String inputJson = mapper.writeValueAsString(game2);

        allGamesJson = mapper.writeValueAsString(allGamesByTitle);

        doReturn(allGamesByTitle).when(repo).findAllGamesByTitle("Little Big Planet 2");

        ResultActions result = mockMvc.perform(
                        get("/games/title/Little Big Planet 2"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(allGamesJson))
                );
    }

    @Test
    public void shouldReturnGameByRating() throws Exception
    {
        Game game2 = new Game();
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("T");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);

        List<Game> allGamesByRatings = new ArrayList<>();
        allGamesByRatings.add(game2);

        doReturn(game2).when(repo).save(game2);

        String inputJson = mapper.writeValueAsString(game2);

        allGamesJson = mapper.writeValueAsString(allGamesByRatings);

        doReturn(allGamesByRatings).when(repo).findAllGamesByEsrbRating("T");

        ResultActions result = mockMvc.perform(
                        get("/games/rating/T"))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.content().json(allGamesJson))
                );
    }

    @Test
    public void shouldBStatus422ForNonExistentGameId() throws Exception
    {
        mockMvc.perform(
                        get("/games/1234"))
                .andExpect(status().is4xxClientError()
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
    public void shouldUpdateByIdAndReturn204StatusCode() throws Exception
    {
        mockMvc.perform(
                        put("/games")
                                .content(gameJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldDeleteByIdAndReturn204StatusCode() throws Exception {
        Game game2 = new Game();
        game2.setId(2);
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("E");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);

        doReturn(game2).when(repo).save(game2);

        mockMvc.perform(delete("/games/2")).andExpect(status().isNoContent());
    }
}
