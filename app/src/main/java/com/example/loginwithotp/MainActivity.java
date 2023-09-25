package com.example.loginwithotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.loginwithotp.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    public ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneAuthPro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();

        binding.sendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnsendOtp();
            }
        });
    }

    public void btnsendOtp(){
        if (binding.getNumber.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        } else if (binding.getNumber.getText().toString().trim().length() !=10) {
            Toast.makeText(this, "Please enter valid phone number...", Toast.LENGTH_SHORT).show();
        }else {
            getotpsend();
        }

    }

    private void getotpsend() {
        binding.getOtpProgress.setVisibility(View.VISIBLE);
        binding.sendOtp.setVisibility(View.INVISIBLE);

        phoneAuthPro = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {}
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                binding.getOtpProgress.setVisibility(View.GONE);
                binding.sendOtp.setVisibility(View.VISIBLE);

//                Toast.makeText(MainActivity.this, binding.getNumber.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                Log.d("phoneError","hello" + e.getLocalizedMessage());
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                binding.sendOtp.setVisibility(View.VISIBLE);
                binding.getOtpProgress.setVisibility(View.INVISIBLE);
                Log.d("verificationId: ",verificationId);
                Intent intent = new Intent(MainActivity.this,typeOtp.class);
                intent.putExtra("phoneNumber",binding.getNumber.getText().toString().trim());
                intent.putExtra("verificationId",verificationId);
                startActivity(intent);

            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber("+91"+binding.getNumber.getText().toString().trim())
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // (optional) Activity for callback binding
                        // If no activity is passed, reCAPTCHA verification can not be used.
                        .setCallbacks(phoneAuthPro)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


}