package com.example.bayuharisaputro.senyumin.Activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bayuharisaputro.senyumin.Adapter.RecyclerAdapter;
import com.example.bayuharisaputro.senyumin.Adapter.RecyclerCommentAdapter;
import com.example.bayuharisaputro.senyumin.Model.Komen;
import com.example.bayuharisaputro.senyumin.Model.Like;
import com.example.bayuharisaputro.senyumin.Model.Post;
import com.example.bayuharisaputro.senyumin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView tv_nama,tv_tanggal, tv_judul,tv_jumlahLike, tv_jumlahKomentar;
    String nama,tanggal,judul,jumlahLike,id,gambarurl,fnomor;
    ImageView gambar;
    EditText komentar;
    DatabaseReference komenRef;
    RecyclerView mRecyclerView;
    RecyclerCommentAdapter mAdapter;
    Button submit;
    List<Komen> mKomen;
    List<Komen> mKomenDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        komenRef = FirebaseDatabase.getInstance().getReference("Komentar");
        Query query = komenRef.orderByChild("tanggal");
        tv_nama = findViewById(R.id.nama);
        tv_tanggal = findViewById(R.id.tanggal);
        tv_judul = findViewById(R.id.judul);
        tv_jumlahLike = findViewById(R.id.jumlahLike);
        tv_jumlahKomentar = findViewById(R.id.jumlahKomentar);
        gambar = findViewById(R.id.image_view_upload);
        komentar = findViewById(R.id.komentar);
        mKomen = new ArrayList<>();
        mKomenDetail = new ArrayList<>();
        fnomor = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        mRecyclerView = findViewById(R.id.recycler_view_komen);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        submit = findViewById(R.id.submit);

        nama = getIntent().getStringExtra("nomor");
        tanggal = getIntent().getStringExtra("tanggal");
        judul = getIntent().getStringExtra("judul");
        jumlahLike = getIntent().getStringExtra("jumlahlike");
        gambarurl = getIntent().getStringExtra("gambar");
        id = getIntent().getStringExtra("id");
        tv_nama.setText(nama);
        tv_tanggal.setText(tanggal);
        tv_jumlahLike.setText(jumlahLike);
        tv_judul.setText(judul);

        Picasso.get().load(gambarurl).placeholder(R.mipmap.ic_placeholder).fit().into(gambar);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(komentar.getText())) {
                    String id = komenRef.push().getKey();
                    Komen komen = new Komen(id, gambarurl, komentar.getText().toString(), fnomor, tanggal);
                    komenRef.child(id).setValue(komen);
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.detailActivity),"kamu berhasil komentar :)", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
                    komentar.setText("");
                    mKomen.clear();
                    mKomenDetail.clear();

                }else {
                    komentar.setError( "Isi komentar kamu !" );
                }
            }
        });
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Komen komen = postSnapshot.getValue(Komen.class);
                    mKomen.add(komen);


                }

                for (int i = 0; i < mKomen.size(); i++) {
                    if (mKomen.get(i).getUrlpost().equals(gambarurl)) {
                        mKomenDetail.add(mKomen.get(i));

                    }
                }
                tv_jumlahKomentar.setText(mKomenDetail.size() + " komentar");
                Collections.reverse(mKomenDetail);
                mAdapter = new RecyclerCommentAdapter(DetailActivity.this, mKomenDetail);
                mAdapter.notifyDataSetChanged();
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.detailActivity),databaseError.getMessage(), Snackbar.LENGTH_SHORT);
                mySnackbar.show();
            }
        });




    }
}
