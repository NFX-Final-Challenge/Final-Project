package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameRepositoryTest
{
    @Autowired
    GameRepository gameRepository;

    @Before
    public void setUp() throws Exception
    {
        gameRepository.deleteAll();
    }

    @Test
    public void shouldAddGetDeleteGame()
    {
        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal(29.90));
        game.setStudio("Media Molecule");
        game.setQuantity(10);

        game = gameRepository.save(game);

        Optional<Game> testGame = gameRepository.findById(game.getId());
        assertEquals(testGame.get(),game);

        gameRepository.deleteById(game.getId());
        testGame = gameRepository.findById(game.getId());
        assertFalse(testGame.isPresent());
    }

    @Test
    public void shouldUpdateGame()
    {
        // First create Game
        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);

        game = gameRepository.save(game);

        game.setPrice(new BigDecimal("34.99"));
        game.setQuantity(7);

        gameRepository.save(game);

        Optional<Game> testGame = gameRepository.findById(game.getId());
        assertEquals(testGame.get(), game);
    }

    @Test
    public void shouldFindAllGames()
    {
        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);
        game = gameRepository.save(game);

        Game game2 = new Game();
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("E");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);
        game2 = gameRepository.save(game2);

        Game game3 = new Game();
        game3.setTitle("Little Big Planet 3");
        game3.setEsrb_rating("E");
        game3.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game3.setPrice(new BigDecimal("49.99"));
        game3.setStudio("Media Molecule");
        game3.setQuantity(40);
        game3 = gameRepository.save(game3);

        List<Game> allGames = gameRepository.findAll();
        assertEquals(allGames.size(),3);
    }

    @Test
    public void shouldFindGameByStudio()
    {
        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);
        game = gameRepository.save(game);

        Game game2 = new Game();
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("E");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);
        game2 = gameRepository.save(game2);

        Game game3 = new Game();
        game3.setTitle("Assasin's Creed Valhalla");
        game3.setEsrb_rating("M");
        game3.setDescription("Assassin's Creed Valhalla is a 2020 action role-playing video game.");
        game3.setPrice(new BigDecimal("59.99"));
        game3.setStudio("Ubisoft");
        game3.setQuantity(40);
        game3 = gameRepository.save(game3);

        Game game4 = new Game();
        game4.setTitle("Marvel's Spider-Man: Miles Morales");
        game4.setEsrb_rating("T");
        game4.setDescription("Marvel's Spider-Man: Miles Morales is a 2020 action-adventure game.");
        game4.setPrice(new BigDecimal("34.99"));
        game4.setStudio("Insomniac Games");
        game4.setQuantity(40);
        game4 = gameRepository.save(game4);

        List<Game> gameList = gameRepository.findAllGamesByStudio("Insomniac Games");
        assertEquals(gameList.size(),1);
    }

    @Test
    public void shouldFindGameByEsrbRating()
    {
        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);
        game = gameRepository.save(game);

        Game game2 = new Game();
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("E");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);
        game2 = gameRepository.save(game2);

        Game game3 = new Game();
        game3.setTitle("Assasin's Creed Valhalla");
        game3.setEsrb_rating("M");
        game3.setDescription("Assassin's Creed Valhalla is a 2020 action role-playing video game.");
        game3.setPrice(new BigDecimal("59.99"));
        game3.setStudio("Ubisoft");
        game3.setQuantity(40);
        game3 = gameRepository.save(game3);

        Game game4 = new Game();
        game4.setTitle("Marvel's Spider-Man: Miles Morales");
        game4.setEsrb_rating("T");
        game4.setDescription("Marvel's Spider-Man: Miles Morales is a 2020 action-adventure game.");
        game4.setPrice(new BigDecimal("34.99"));
        game4.setStudio("Insomniac Games");
        game4.setQuantity(40);
        game4 = gameRepository.save(game4);

        List<Game> gameList = gameRepository.findAllGamesByEsrbRating("E");
        assertEquals(gameList.size(),2);
    }

    @Test
    public void shouldFindGameByTitle()
    {
        Game game = new Game();
        game.setTitle("Little Big Planet");
        game.setEsrb_rating("E");
        game.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game.setPrice(new BigDecimal("29.90"));
        game.setStudio("Media Molecule");
        game.setQuantity(10);
        game = gameRepository.save(game);

        Game game2 = new Game();
        game2.setTitle("Little Big Planet 2");
        game2.setEsrb_rating("E");
        game2.setDescription("LittleBigPlanet is a puzzle platform video game series.");
        game2.setPrice(new BigDecimal("34.99"));
        game2.setStudio("Media Molecule");
        game2.setQuantity(20);
        game2 = gameRepository.save(game2);

        Game game3 = new Game();
        game3.setTitle("Assasin's Creed Valhalla");
        game3.setEsrb_rating("M");
        game3.setDescription("Assassin's Creed Valhalla is a 2020 action role-playing video game.");
        game3.setPrice(new BigDecimal("59.99"));
        game3.setStudio("Ubisoft");
        game3.setQuantity(40);
        game3 = gameRepository.save(game3);

        Game game4 = new Game();
        game4.setTitle("Marvel's Spider-Man: Miles Morales");
        game4.setEsrb_rating("T");
        game4.setDescription("Marvel's Spider-Man: Miles Morales is a 2020 action-adventure game.");
        game4.setPrice(new BigDecimal("34.99"));
        game4.setStudio("Insomniac Games");
        game4.setQuantity(40);
        game4 = gameRepository.save(game4);

        List<Game> gameList = gameRepository.findAllGamesByTitle("Little Big Planet");
        assertEquals(gameList.size(),1);
    }



}
