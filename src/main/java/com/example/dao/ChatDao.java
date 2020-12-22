package com.example.dao;

import com.example.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatDao extends JpaRepository<Chat,Integer> {
    List<Chat> findAllByRecv(int recv);
    List<Chat> findAllBySender(int sender);
    List<Chat> findAll();

    @Override
    Chat saveAndFlush(Chat chat);
}
