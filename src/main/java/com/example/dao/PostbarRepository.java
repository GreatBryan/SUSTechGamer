package com.example.dao;

import com.example.domain.postbar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostbarRepository extends JpaRepository<postbar, Long> {
    List<postbar> findAll();

    List<postbar> findByTid(int tid);

    List<postbar> findByUid(int uid);

    List<postbar> findByGid(int gid);

    void deleteByTid(int id);

    postbar saveAndFlush(postbar t_postbar);

    void deleteAllByGid(int gid);


}
