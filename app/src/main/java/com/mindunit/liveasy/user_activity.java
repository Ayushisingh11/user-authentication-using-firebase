package com.mindunit.liveasy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class user_activity extends AppCompatActivity {

    EditText enternumber;
    Button buttontootp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        enternumber= findViewById(R.id.input_mobile_number);
        buttontootp = findViewById(R.id.buttongetotp);

        final ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        buttontootp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!enternumber.getText().toString().trim().isEmpty()){
                    if((enternumber.getText().toString().trim().length()==10)){

                        progressBar.setVisibility(View.VISIBLE);
                        buttontootp.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(), 60,
                                TimeUnit.SECONDS, user_activity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        buttontootp.setVisibility(View.VISIBLE);

                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        buttontootp.setVisibility(View.VISIBLE);
                                        Toast.makeText(user_activity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        super.onCodeSent(backendotp, forceResendingToken);
                                        progressBar.setVisibility(View.GONE);
                                        buttontootp.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(),verification_activity.class);
                                        intent.putExtra("Mobile",enternumber.getText().toString());
                                        intent.putExtra("backendotp",backendotp);
                                        startActivity(intent);
                                    }
                                }
                        );
                    Intent intent = new Intent(getApplicationContext(),verification_activity.class);
                    intent.putExtra("Mobile",enternumber.getText().toString());
                    startActivity(intent);
                }else{
                    Toast.makeText(user_activity.this,"Please enter correct number",Toast.LENGTH_SHORT).show();
                }
            }else{
                    Toast.makeText(user_activity.this,"Enter mobile number",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}