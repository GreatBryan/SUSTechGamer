package com.example.dao;

import com.example.domain.Comment;
import com.example.domain.ban_user_com;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface busRepository extends JpaRepository<ban_user_com,Integer> {
    @Override
    ban_user_com saveAndFlush(ban_user_com bus);

    List<ban_user_com> findAll();

    List<ban_user_com> findByUidAndGid(int uid, int gid);

    void deleteAllByGid(int gid);
}
