package com.example.bayuharisaputro.senyumin.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bayuharisaputro.senyumin.Activity.ProfileActivity;
import com.example.bayuharisaputro.senyumin.Model.User;
import com.example.bayuharisaputro.senyumin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class InputNamaActivity extends Fragment {

    EditText Enama,Enomor,Etentang;
    String nama, nomor ,tentang;
    Button simpan;
    DatabaseReference databaseUser;
    public InputNamaActivity() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_input_nama, container, false);
        databaseUser = FirebaseDatabase.getInstance().getReference("User");
        Enama = rootView.findViewById(R.id.nama);
        Enomor = rootView.findViewById(R.id.nomor);
        Etentang = rootView.findViewById(R.id.tentang);
        simpan = rootView.findViewById(R.id.simpan);

        SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        String fnomor = preferences.getString("noHp","kosong");
        Enomor.setText(fnomor);
        nomor = Enomor.getText().toString();
        simpan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                nama = Enama.getText().toString().trim();
                tentang = Etentang.getText().toString().trim();
                addUser(nama,nomor,tentang);

            }
        });
        return  rootView;
    }

    private void addUser(String uNama, String uNomor, String uTentang ) {
        if(!TextUtils.isEmpty(uNama)) {
            String id = databaseUser.push().getKey();
            User user = new User(uNomor, uNama, uTentang,id);
            databaseUser.child(id).setValue(user);
            Toast.makeText(getContext(),
                    "Data kamu berhasil disimpan :)", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getContext(),
                    "Nama tidak boleh kosong ", Toast.LENGTH_LONG).show();
        }

    }


}
