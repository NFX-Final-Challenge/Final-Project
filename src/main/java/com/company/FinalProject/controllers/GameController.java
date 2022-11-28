package com.company.FinalProject.controllers;

import com.company.FinalProject.models.Game;
import com.company.FinalProject.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {
    @Autowired
    GameRepository repository;


    @GetMapping("/games")
    public List<Game> getGamess() {
        return repository.findAll();
    }

    @GetMapping("/games/{id}")
    public Game getGamesById(@PathVariable int id) {

        Optional<Game> returnVal = repository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    @GetMapping("/games/title/{title}")
    public List<Game> getGameByTitle(@PathVariable String title) {
        List<Game> returnVal = repository.findAllGamesByTitle(title);
        if (returnVal.size() > 0) {
            return returnVal;
        } else {
            return null;
        }
    }

    @GetMapping("/games/rating/{esrbRating}")
    public List<Game> getGamesByEsrbRating(@PathVariable String esrbRating) {
        List<Game> returnVal = repository.findAllGamesByEsrbRating(esrbRating);
        if (returnVal.size() > 0) {
            return returnVal;
        } else {
            return null;
        }
    }

    @GetMapping("/games/studio/{studio}")
    public List<Game> getGameByStudio(@PathVariable String studio) {
        List<Game> returnVal = repository.findAllGamesByStudio(studio);
        if (returnVal.size() > 0) {
            return returnVal;
        } else {
            return null;
        }
    }



    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    public Game addGame(@RequestBody Game game) {
        return repository.save(game);
    }

    @PutMapping("/games")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGame(@RequestBody Game game) {
        repository.save(game);
    }

    @DeleteMapping("/games/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGame(@PathVariable int id) {
        repository.deleteById(id);
    }

}
