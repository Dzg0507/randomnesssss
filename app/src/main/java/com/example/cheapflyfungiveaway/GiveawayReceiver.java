package com.example.cheapflyfungiveaway;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class GiveawayReceiver extends BroadcastReceiver {
    private static final String TAG = "GiveawayReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive");

        GiveawayService giveawayService = new GiveawayService();
        giveawayService.selectWinner();
    }
}