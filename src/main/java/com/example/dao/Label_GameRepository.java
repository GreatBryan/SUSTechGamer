package com.example.dao;

import com.example.domain.Label_Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Label_GameRepository extends JpaRepository<Label_Game, Long> {
    List<Label_Game> findAll();

    List<Label_Game> findById(int id);

    List<Label_Game> findByKeyid(int id);

    List<Label_Game> findByLabel(String label);

    Label_Game saveAndFlush(Label_Game label_game);

    void deleteByKeyid(int id);

    void deleteAllById(int gid);
}
