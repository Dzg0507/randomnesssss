package com.example.cheapflyfungiveaway;

import java.util.HashMap;
import java.util.Map;

public class DonationActivity {
    public String userId;
    public double amount;

    public DonationActivity() {
        // Default constructor required for calls to DataSnapshot.getValue(Donation.class)
    }

    public DonationActivity(String userId, double amount) {
        this.userId = userId;
        this.amount = amount;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("amount", amount);
        return result;
    }
}