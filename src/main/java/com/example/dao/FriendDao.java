package com.example.dao;

import com.example.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface FriendDao extends JpaRepository<Friend, Integer> {

    List<Friend> findAllByUser1OrUser2(int u1, int u2);

    List<Friend> findByUser1(int uid);

    @Override
    Friend saveAndFlush(Friend friend);

    void deleteByUser1AndUser2(int u1, int u2);

    Friend findByUser1AndUser2(int u1, int u2);

    @Modifying
    @Query("update Friend f set f.intimacy = f.intimacy + ?1 where f.user1 = ?2 and f.user2 = ?3")
    void changeIntimacy(int val, int u1, int u2);

}
