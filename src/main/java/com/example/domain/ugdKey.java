package com.example.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ugdKey implements Serializable {
    private int uid;
    private int gid;
    private int dlc;
}
