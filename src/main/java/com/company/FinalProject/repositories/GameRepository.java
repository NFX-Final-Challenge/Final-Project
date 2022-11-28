package com.company.FinalProject.repositories;

import com.company.FinalProject.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository  extends JpaRepository<Game, Integer>  {
    List<Game> findAllGamesByStudio(String studio);
    List<Game> findAllGamesByTitle(String title);
    List<Game> findAllGamesByEsrbRating(String esrbRating);
}
