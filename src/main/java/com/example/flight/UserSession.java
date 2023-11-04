package com.example.flight;

public class UserSession {
    private static Integer loggedInUserId;

    public static int getLoggedInUserId() {
        return loggedInUserId;
    }

    public static void setLoggedInUserId(Integer userId) {
        loggedInUserId = userId;
    }
}

