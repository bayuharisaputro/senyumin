package com.example.bayuharisaputro.senyumin.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bayuharisaputro.senyumin.Model.Post;
import com.example.bayuharisaputro.senyumin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    private Context mContext;
    private List<Post> mPost;


    public RecyclerAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Post uploadCurrent = mPost.get(position);
        holder.judul.setText(uploadCurrent.getJudul());
        holder.tanggal.setText(uploadCurrent.getTanggal());
        Picasso.get().load(uploadCurrent.getNamaPost()).placeholder(R.mipmap.ic_placeholder).fit().into(holder.imageView);
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.show_image, parent, false);
        return new RecyclerViewHolder(v);
    }


    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, tanggal;
        public ImageView imageView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);

            judul = itemView.findViewById(R.id.judul);
            tanggal = itemView.findViewById(R.id.tanggal);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}
