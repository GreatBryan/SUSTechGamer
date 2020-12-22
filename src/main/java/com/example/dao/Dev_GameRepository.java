package com.example.dao;

import com.example.domain.dev_game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Dev_GameRepository extends JpaRepository<dev_game, Long> {
    List<dev_game> findAll();

    List<dev_game> findByUidAndGid(int uid, int gid);

    List<dev_game> findByUid(int uid);

    List<dev_game> findByGid(int gid);

    dev_game saveAndFlush(dev_game dev_game);

    void deleteAllByGid(int gid);
}
