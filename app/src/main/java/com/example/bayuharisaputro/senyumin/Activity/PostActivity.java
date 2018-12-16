package com.example.bayuharisaputro.senyumin.Activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bayuharisaputro.senyumin.Model.Post;
import com.example.bayuharisaputro.senyumin.Model.User;
import com.example.bayuharisaputro.senyumin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PostActivity extends AppCompatActivity {

    ImageButton image;
    Button simpan;
    String sJudul,sNamapost, sTanggal,fnomor;
    StorageReference mStorage;
    static final int GALLERY = 1;
    EditText judul,tanggal;
    Uri selectedImageURI;
    ProgressDialog mprogress;
    DatabaseReference databaseUser;
     StorageTask mUploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        databaseUser = FirebaseDatabase.getInstance().getReference("Post");
        mStorage = FirebaseStorage.getInstance().getReference();
        image = findViewById(R.id.gambar);
        judul = findViewById(R.id.judul);
        tanggal = findViewById(R.id.tanggal);
        simpan = findViewById(R.id.simpanPost);
        SharedPreferences preferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE);
       fnomor = preferences.getString("noHp","kosong");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        tanggal.setText(formattedDate);
        mprogress = new ProgressDialog(this);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY);

            }
        });

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sJudul = judul.getText().toString();
                sTanggal = tanggal.getText().toString();
                addPost(sJudul,sTanggal,0,0,0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY) {

               selectedImageURI = data.getData();

                Picasso.get().load(selectedImageURI).fit().into(image);

            }

        }
    }
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }


    private void addPost(final String uJudul, final String uTanggal, final int uLike, final int uDislike, final int uReport ) {
        if(!TextUtils.isEmpty(uJudul) && selectedImageURI!=null) {
            final StorageReference fileReference = mStorage.child(selectedImageURI.getLastPathSegment()+ "." + getFileExtension(selectedImageURI));
            mUploadTask = fileReference.putFile(selectedImageURI)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.postActivity),"Upload Sukses :)", Snackbar.LENGTH_SHORT);
                                    mySnackbar.show();
                                    mprogress.dismiss();
                                    String uNamapost =uri.toString();
                                    Post post = new Post(fnomor,uJudul,uTanggal,uNamapost,uLike, uDislike, uReport);
                                    String id = databaseUser.push().getKey();
                                    databaseUser.child(id).setValue(post);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.postActivity),e.getMessage(), Snackbar.LENGTH_SHORT);
                            mySnackbar.show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            mprogress.setMessage("Uploading ...");
                            mprogress.show();
                        }
                    });
                } else {
                  Snackbar mySnackbar = Snackbar.make(findViewById(R.id.postActivity),"Judul atau Gambar tidak ditemukan", Snackbar.LENGTH_SHORT);
                    mySnackbar.show();
        }
        }

    }

