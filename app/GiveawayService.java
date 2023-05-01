package com.example.cheapflyfungiveaway;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.cheapflyfungiveaway.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GiveawayService extends Service {
    private static final String TAG = "GiveawayService";
    private static final long INTERVAL = 24 * 60 * 60 * 1000; // 24 hours
    private static final String CHANNEL_ID = "giveaway_channel";
    private static final int NOTIFICATION_ID = 1;

    private DatabaseReference mDatabase;
    private double mGiveawayAmount;

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mGiveawayAmount = 0;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");

        // Set up notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Giveaway", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Schedule giveaway task
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent giveawayIntent = new Intent(this, GiveawayReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, giveawayIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), INTERVAL, pendingIntent);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void addDonation(double amount) {
        mGiveawayAmount += amount;
    }

    public void selectWinner() {
        mDatabase.child("donations").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> userIds = new ArrayList<>();
                List<Double> amounts = new ArrayList<>();

                for (DataSnapshot donationSnapshot : snapshot.getChildren()) {
                    Donation donation = donationSnapshot.getValue(Donation.class);
                    userIds.add(donation.userId);
                    amounts.add(donation.amount);
                }

                if (userIds.size() > 0) {
                    int index = new Random().nextInt(userIds.size());
                    String winnerId = userIds.get(index);
                    double winnerAmount = amounts.get(index);

                    // Award winner
                    mDatabase.child("winners").push().setValue(new Winner(winnerId, winnerAmount));

                    // Reset giveaway amount
                    mGiveawayAmount = 0;

                    // Show notification
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(GiveawayService.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_notification)
                            .setContentTitle("Giveaway Winner!")
                            .setContentText("Congratulations to " + winnerId + " for winning $" + winnerAmount + "!")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(GiveawayService.this);
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "selectWinner:onCancelled", error.toException());
            }
        })
    }
}
