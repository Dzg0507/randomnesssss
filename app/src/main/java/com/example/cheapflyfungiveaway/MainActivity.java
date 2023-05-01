package com.example.cheapflyfungiveaway;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

 //       mAuth = FirebaseAuth.getInstance();
 //       mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final String PAYPAL_CLIENT_ID = "YOUR_PAYPAL_CLIENT_ID";
    private static final int PAYPAL_REQUEST_CODE = 123;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(PAYPAL_CLIENT_ID);


    public void onDonateButtonClicked(View view) {
        PayPalPayment payment = new PayPalPayment(new BigDecimal("0.10"), "USD", "Donation",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Handle successful donation
            } else {
                // Handle failed donation
            }
        }
    }


private void saveDonationData(String userId,double amount){
 //       String key=mDatabase.child("donations").push().getKey();
        DonationActivity donation=new DonationActivity(userId,amount);
        Map<String, Object> donationValues=donation.toMap();

        Map<String, Object> childUpdates=new HashMap<>();
   //     childUpdates.put("/donations/"+key,donationValues);

  //      mDatabase.updateChildren(childUpdates);
        }

}