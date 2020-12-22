package com.example.dao;

import com.example.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Integer> {
    @Override
    Comment saveAndFlush(Comment comment);

    List<Comment> findAll();

    List<Comment> findAllByTiezi(int tid);

    List<Comment> findByCid(int cid);

    @Modifying
    @Query("update Comment c set c.display = 0 where c.cid = ?1")
    void hideComment(int cid);

    @Modifying
    @Query("update Comment c set c.like_num = c.like_num + 1 where c.cid = ?1")
    void like(int cid);

    @Modifying
    @Query("update Comment c set c.dislike = c.dislike + 1 where c.cid = ?1")
    void dislike(int cid);

    void deleteAllByTiezi(int tid);
}
