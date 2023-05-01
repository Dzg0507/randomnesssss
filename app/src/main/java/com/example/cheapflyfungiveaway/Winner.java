package com.example.cheapflyfungiveaway;

public class Winner {
    public String userId;
    public double amount;
    public long timestamp;

    public Winner() {
        // Default constructor required for calls to DataSnapshot.getValue(Winner.class)
    }

    public Winner(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
        this.timestamp = System.currentTimeMillis();
    }
}
