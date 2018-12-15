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
        ref = root.child(user.getUid()).orderByChild("nomor").equalTo(fnomor);

        setContentView(R.layout.activity_profile);
        hello = findViewById(R.id.hello);
        bSimpan = findViewById(R.id.simpan);
        bLanjut = findViewById(R.id.lanjut);

        bLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            Intent myIntent = new Intent(ProfileActivity.this, MainActivity.class);
                            startActivity(myIntent);
                            finish();
                        }
                        else {
                            InputNamaActivity fm2 = new InputNamaActivity();
                            hello.setVisibility(View.GONE);
                            bLanjut.setVisibility(View.GONE);
                            gantiFrag(fm2);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


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
