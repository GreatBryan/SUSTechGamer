package com.sdk.api;

import java.io.IOException;
import java.util.List;

public class test {
    public static void main(String[] args) throws IOException {
        SDKClient tool = SDKClient.getInstance();
        System.out.println(tool.getAvatar());
        System.out.println(tool.getFriends());
    }
}
