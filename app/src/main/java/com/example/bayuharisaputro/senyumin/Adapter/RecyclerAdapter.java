package com.example.bayuharisaputro.senyumin.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bayuharisaputro.senyumin.Interface.RecyclerClick;
import com.example.bayuharisaputro.senyumin.Model.Post;
import com.example.bayuharisaputro.senyumin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    public ImageButton likeButton;
    private Context mContext;
    private List<Post> mPost;
    private RecyclerClick mlistener;


    public RecyclerAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;

    }

    public void setOnItemClickListener(RecyclerClick listener) {
        mlistener = listener;
    }


    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final Post uploadCurrent = mPost.get(position);
        holder.judul.setText(uploadCurrent.getJudul());
        holder.tanggal.setText(uploadCurrent.getTanggal());
        holder.nama.setText(uploadCurrent.getNomor());
        holder.jumlahLike.setText(uploadCurrent.getLike());
        holder.tanggal.setText(uploadCurrent.getTanggal());
        Picasso.get().load(uploadCurrent.getNamaPost()).placeholder(R.mipmap.ic_placeholder).into(holder.imageView);


    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.show_image, parent, false);
        RecyclerViewHolder evh = new RecyclerViewHolder(v, mlistener);
        return evh;
    }




    @Override
    public int getItemCount() {
        return mPost.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, tanggal, nama, jumlahLike;
        public ImageView imageView;


        public RecyclerViewHolder(View itemView,final RecyclerClick listener) {
            super(itemView);
            likeButton = itemView.findViewById(R.id.like);
            nama = itemView.findViewById(R.id.nama);
            jumlahLike = itemView.findViewById(R.id.jumlahLike);
            judul = itemView.findViewById(R.id.judul);
            tanggal = itemView.findViewById(R.id.tanggal);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onPositionClicked(position);
                        }
                    }
                }
            });

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onLikeClicked(position);
                        }

                    }
                }
            });

        }

    }

    public void changeLike(int draw) {
        likeButton.setImageResource(draw);
    }

}
