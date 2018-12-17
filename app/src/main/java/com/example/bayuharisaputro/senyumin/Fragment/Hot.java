package com.example.bayuharisaputro.senyumin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.bayuharisaputro.senyumin.Adapter.RecyclerAdapter;
import com.example.bayuharisaputro.senyumin.Model.Post;
import com.example.bayuharisaputro.senyumin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Hot extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    RecyclerView mRecyclerView;
    RecyclerAdapter mAdapter;
    ProgressBar mProgress;
    DatabaseReference ref;
    Query query;
    List<Post> mPost,mPostId;
    Hot.OnFragmentInteractionListener mListener;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String nomor = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
    public Hot() {
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

        mSwipeRefreshLayout = rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mPost = new ArrayList<>();
        mPostId = new ArrayList<>();
        ref = FirebaseDatabase.getInstance().getReference("Post");
        query = ref.orderByChild("tanggal");
        //mProgress.setVisibility(View.VISIBLE);
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
        mPostId.clear();
        loadData();
    }


    void loadData() {
        mSwipeRefreshLayout.setRefreshing(true);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post upload = postSnapshot.getValue(Post.class);
                    mPost.add(upload);


                }

                for (int i = 0; i < mPost.size(); i++) {
                    if (mPost.get(i).getNomor().equals(nomor)) {
                        mPostId.add(mPost.get(i));

                    }
                }
                Collections.reverse(mPost);
                mAdapter = new RecyclerAdapter(getActivity(), mPostId);
                mAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
                mRecyclerView.setAdapter(mAdapter);

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

}
