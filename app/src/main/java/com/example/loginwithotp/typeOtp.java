package com.example.loginwithotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginwithotp.databinding.ActivityTypeOtpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class typeOtp extends AppCompatActivity {

    public @NonNull ActivityTypeOtpBinding binding;
    private String verificationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTypeOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toChangeFocus();

        binding.phoneNumberDisplay.setText(String.format(
                "+91-%s", getIntent().getStringExtra("phoneNumber")
        ));

        verificationId = getIntent().getStringExtra("verificationId");
//        String verificationId = "986188" ;

        binding.resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(typeOtp.this, "OTP send successfully.", Toast.LENGTH_SHORT).show();
            }
        });

        binding.verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.verifyProgress.setVisibility(View.VISIBLE);
                binding.verifyBtn.setVisibility(View.INVISIBLE);

                if (binding.otpBtn1.getText().toString().trim().isEmpty() ||
                    binding.otpBtn2.getText().toString().trim().isEmpty() ||
                    binding.otpBtn3.getText().toString().trim().isEmpty() ||
                    binding.otpBtn4.getText().toString().trim().isEmpty()||
                    binding.otpBtn5.getText().toString().trim().isEmpty()||
                    binding.otpBtn6.getText().toString().trim().isEmpty()){
                    Toast.makeText(typeOtp.this, "Please enter OTP...", Toast.LENGTH_SHORT).show();
                }else {
                    if (verificationId != null){
                        String otp = String.valueOf(binding.otpBtn1.getText().toString().trim()) +
                                     String.valueOf(binding.otpBtn2.getText().toString().trim())+
                                     String.valueOf(binding.otpBtn3.getText().toString().trim())+
                                     String.valueOf(binding.otpBtn4.getText().toString().trim())+
                                     String.valueOf(binding.otpBtn5.getText().toString().trim())+
                                     String.valueOf(binding.otpBtn6.getText().toString().trim());

                        Toast.makeText(typeOtp.this, otp, Toast.LENGTH_SHORT).show();
                        Toast.makeText(typeOtp.this, verificationId, Toast.LENGTH_SHORT).show();

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
                        FirebaseAuth.getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    binding.verifyProgress.setVisibility(View.VISIBLE);
                                    binding.verifyBtn.setVisibility(View.INVISIBLE);

                                    Intent intent = new Intent(typeOtp.this, VerificationStatus.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else {
                                    binding.verifyProgress.setVisibility(View.INVISIBLE);
                                    binding.verifyBtn.setVisibility(View.VISIBLE);
                                    Log.d("task not successfull : ", String.valueOf(task.getException()));
                                    Toast.makeText(typeOtp.this, "Invalid OTP..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });


    }

    private void toChangeFocus() {
        binding.otpBtn1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.otpBtn2.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.otpBtn2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.otpBtn3.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.otpBtn3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.otpBtn4.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.otpBtn4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.otpBtn5.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        binding.otpBtn5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.otpBtn6.requestFocus();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        
    }
}