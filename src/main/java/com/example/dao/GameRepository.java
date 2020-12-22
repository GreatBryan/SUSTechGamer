package com.example.dao;

import com.example.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findAll();

    List<Game> findByName(String name);

    List<Game> findByUid(int uid);

    List<Game> findById(long id);

    String deleteById(long id);

    Game saveAndFlush(Game game);
}