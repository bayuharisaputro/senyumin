package com.example.bayuharisaputro.senyumin.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DecorToolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bayuharisaputro.senyumin.Activity.DetailActivity;
import com.example.bayuharisaputro.senyumin.Activity.MainActivity;
import com.example.bayuharisaputro.senyumin.Activity.PostActivity;
import com.example.bayuharisaputro.senyumin.Adapter.RecyclerAdapter;
import com.example.bayuharisaputro.senyumin.Interface.RecyclerClick;
import com.example.bayuharisaputro.senyumin.Model.Like;
import com.example.bayuharisaputro.senyumin.Model.Post;
import com.example.bayuharisaputro.senyumin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class Main extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    int jumlah = 0;
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    DatabaseReference ref, ref2,update;
    Query query,query2;
    int LikeStat = 0;
    DatabaseReference databaselike;
    List<Post> mPost;
    List<Like> mLike;
    boolean check = true;

    Hot.OnFragmentInteractionListener mListener;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String nomor = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
    public Main() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View rootView = inflater.inflate(R.layout.fragment_main2, container, false);
        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaselike = FirebaseDatabase.getInstance().getReference("Like");

        mPost = new ArrayList<>();
        mLike = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Post");
        ref2 = FirebaseDatabase.getInstance().getReference("Like");
        update = FirebaseDatabase.getInstance().getReference("Post");
        query2 = ref2.orderByChild("id");

        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_container);

       mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadData();

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Hot.OnFragmentInteractionListener) {
            mListener = (Hot.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onRefresh() {
        mPost.clear();
        loadData();
    }


    void loadData() {

        LikeStat = 0;
       mSwipeRefreshLayout.setRefreshing(true);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post upload = postSnapshot.getValue(Post.class);
//                    if(LikeStat == 0 ) {
                        mPost.add(upload);
//                   }

                }

                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Like uLike = postSnapshot.getValue(Like.class);
                            mLike.add(uLike);

                        }

                        mAdapter = new RecyclerAdapter(getActivity(), mPost);
                        mSwipeRefreshLayout.setRefreshing(false);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new RecyclerClick() {
                            @Override
                            public void onPositionClicked(int position) {
                                Intent myIntent = new Intent(getActivity(), DetailActivity.class);
                                myIntent.putExtra("nomor", mPost.get(position).getNomor());
                                myIntent.putExtra("judul", mPost.get(position).getJudul());
                                myIntent.putExtra("tanggal", mPost.get(position).getTanggal());
                                myIntent.putExtra("gambar", mPost.get(position).getNamaPost());
                                myIntent.putExtra("jumlahlike", mPost.get(position).getLike());
                                myIntent.putExtra("id", mPost.get(position).getId());
                                startActivity(myIntent);
                            }
                            @Override
                            public void onLikeClicked(final int position) {

                                    for (int i = 0; i < mLike.size(); i++) {
                                        if (mLike.get(i).getIdPost().equals(mPost.get(position).getNamaPost()) &&
                                                mLike.get(i).getNomorUser().equals(nomor)) {
                                            check = false;

                                        }
                                    }


                                if(check) {
                                    addLike(mPost.get(position).getNamaPost(),nomor);
                                    Toast.makeText(getContext(),
                                            "kamu like " + mPost.get(position).getJudul(), Toast.LENGTH_LONG).show();

                                    HashMap<String, Object> result = new HashMap<>();
                                    int like = Integer.parseInt(mPost.get(position).getLike());
                                    like++;
                                    result.put("like", Integer.toString(like));
                                    update.child(mPost.get(position).getId()).updateChildren(result);

                                     mPost.get(position).setLike(Integer.toString(like));
                                     mAdapter.notifyDataSetChanged();


                                }else {
                                    Toast.makeText(getContext(),
                                            "kamu sudah like untuk " + mPost.get(position).getJudul(), Toast.LENGTH_LONG).show();
                                }

                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar mySnackbar = Snackbar.make(getView().findViewById(R.id.fragment_main),databaseError.getMessage(), Snackbar.LENGTH_SHORT);
                mySnackbar.show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }
    public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }


    private void addLike(final String idPost, final String nomorUser ) {
         String phoneId = databaselike.push().getKey();
        Like like = new Like(phoneId, idPost, nomorUser);
        mLike.add(like);
        databaselike.child(phoneId).setValue(like);

    }

    }

