package com.OlymFollow.Backend.Models;

public enum Medal {
    OURO,
    PRATA,
    BRONZE;

    public static Medal fromString(String medal) {
        medal = medal.trim().toUpperCase();
        switch (medal) {
            case "OURO":
                return OURO;
            case "PRATA":
                return PRATA;
            case "BRONZE":
                return BRONZE;
        }
        return null;
    }
}
