package com.example.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class FriendKey implements Serializable {
    private int user1;
    private int user2;
}
