package com.example.dao;

import com.example.domain.User_game_dlc;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ugdDao extends JpaRepository<User_game_dlc, Integer> {
    @Override
    User_game_dlc saveAndFlush(User_game_dlc info);

    List<User_game_dlc> findByUidAndGid(int uid, int gid);

    List<User_game_dlc> findByUidAndGidAndDlc(int uid, int gid, int dlc);

    List<User_game_dlc> findByUid(int uid);

    void deleteAllByGid(int gid);
}
