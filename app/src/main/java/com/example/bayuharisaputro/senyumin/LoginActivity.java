package com.example.bayuharisaputro.senyumin;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    String kodeTerkirim;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        String kode = InputKode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(kodeTerkirim, kode);
        Login(credential);
    }

    private void Login(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(),
                                    "Login Berhasil", Toast.LENGTH_LONG).show();
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
