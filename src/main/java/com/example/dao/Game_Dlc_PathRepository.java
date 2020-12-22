package com.example.dao;

import com.example.domain.game_dlc_path;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Game_Dlc_PathRepository extends JpaRepository<game_dlc_path, Long>{
    List<game_dlc_path> findAll();

    List<game_dlc_path> findByGid(int gid);

    List<game_dlc_path> findByGidAndDlcid(int gid, int dlc_id);

    game_dlc_path saveAndFlush(game_dlc_path gdp);

    void deleteByKeyid(int id);

    void deleteAllByGid(int gid);
}
