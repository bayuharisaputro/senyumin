package com.example.bayuharisaputro.senyumin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bayuharisaputro.senyumin.Interface.RecyclerClick;
import com.example.bayuharisaputro.senyumin.Model.Komen;
import com.example.bayuharisaputro.senyumin.Model.Post;
import com.example.bayuharisaputro.senyumin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerCommentAdapter extends RecyclerView.Adapter<RecyclerCommentAdapter.RecyclerViewHolder>  {

    private Context mContext;
    private List<Komen> mKomen;

    public RecyclerCommentAdapter(Context mContext, List<Komen> mKomen) {
        this.mContext = mContext;
        this.mKomen = mKomen;

    }



    @Override
    public RecyclerCommentAdapter.RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.show_komen, parent, false);
        RecyclerCommentAdapter.RecyclerViewHolder evh = new RecyclerCommentAdapter.RecyclerViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {

        final Komen komentar = mKomen.get(i);
        recyclerViewHolder.tanggal.setText(komentar.getTgl());
        recyclerViewHolder.nama.setText(komentar.getNomorPengomentar());
        recyclerViewHolder.komentar.setText(komentar.getKomentar());


    }

    @Override
    public int getItemCount() {
        return mKomen.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView komentar, tanggal, nama, jumlahLike;


        public RecyclerViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama);
            komentar = itemView.findViewById(R.id.komentar);
            tanggal = itemView.findViewById(R.id.tanggal);



        }

    }
}
