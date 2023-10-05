package com.example.flight;

public class UserSession {
    private static String loggedInUserId;

    public static String getLoggedInUserId() {
        return loggedInUserId;
    }

    public static void setLoggedInUserId(String userId) {
        loggedInUserId = userId;
    }
}

