package com.example.dao;

import com.example.domain.Comment;
import com.example.domain.user_hash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface user_hashRepository extends JpaRepository<user_hash,Integer> {
    List<user_hash> findByUid(int uid);

    List<user_hash> findByHash(String hash);

    @Override
    user_hash saveAndFlush(user_hash u_h);

}
