package com.example.bayuharisaputro.senyumin.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bayuharisaputro.senyumin.Fragment.InputNamaActivity;
import com.example.bayuharisaputro.senyumin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    Query ref;
    FirebaseUser user;
    FirebaseAuth auth;
    Button bLanjut,bSimpan;
    TextView hello;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        final String fnomor = preferences.getString("noHp","kosong");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        DatabaseReference root = FirebaseDatabase.getInstance().getReference();
        ref = root.child("User").child("LTlWYvzFYThsmwbTtRK");
        setContentView(R.layout.activity_profile);
        hello = findViewById(R.id.hello);
        bSimpan = findViewById(R.id.simpan);
        bLanjut = findViewById(R.id.lanjut);
        if (auth.getCurrentUser() != null)
           hello.setText(auth.getCurrentUser().getUid());
        bLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);


            }
        });
    }
    public void gantiFrag(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_cont, fragment);
        fragmentTransaction.setCustomAnimations(R.anim.enter_right,R.anim.exit_right);
        fragmentTransaction.commit();

    }




}
