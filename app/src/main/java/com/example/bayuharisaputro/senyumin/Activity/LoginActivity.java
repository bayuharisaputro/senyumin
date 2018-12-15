package com.example.bayuharisaputro.senyumin.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bayuharisaputro.senyumin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    EditText InputNomorTelp, InputKode;
    Button kirimKode, login;
    String kodeTerkirim,kode,nomor;
    FirebaseAuth firebaseAuth;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        String nomor = preferences.getString("noHp","kosong");
        if(!"kosong".equals(nomor)) {
            Intent myIntent = new Intent(LoginActivity.this, ProfileActivity.class);
            startActivity(myIntent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        InputNomorTelp = findViewById(R.id.phoneNumber);
        InputKode = findViewById(R.id.verifyCode);
        kirimKode = findViewById(R.id.sendCode);
        login = findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();
        kirimKode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifikasiKode();

            }
        });

    }

    private void sendVerificationCode(){

        String nomor = InputNomorTelp.getText().toString();

        if(nomor.isEmpty()){
            InputNomorTelp.setError("Nomor telp harus diisi");
            InputNomorTelp.requestFocus();
            return;
        }

        if(nomor.length() < 10 ){
            InputNomorTelp.setError("Masukan nomor telp");
            InputNomorTelp.requestFocus();
            return;
        }


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                nomor,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);
    }



    private void verifikasiKode(){
        kode = InputKode.getText().toString();
        nomor = InputNomorTelp.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(kodeTerkirim, kode);
        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
        editor.putString("noHp", nomor);
        editor.commit();
        Login(credential);
    }

    private void Login(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent myIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                            myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(myIntent);
                            finish();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(),
                                        "Kode Verifikasi Salah", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }



    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            kodeTerkirim = s;
        }
    };

}
