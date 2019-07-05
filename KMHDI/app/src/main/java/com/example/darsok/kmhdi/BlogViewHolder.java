package com.example.darsok.kmhdi;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

public class BlogViewHolder extends RecyclerView.ViewHolder {
    View  mView;

    public BlogViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //itemClick
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });
        //itemLongClick
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view, getAdapterPosition());


                return true;
            }
        });

    }
    public void setDetails(Context ctx, String title, String desc,String image ){
        TextView mTitleTv = mView.findViewById(R.id.post_title);
        TextView mDetailTv = mView.findViewById(R.id.post_desc);
        ImageView mImageIv = mView.findViewById(R.id.post_image);

        mTitleTv.setText(title);
        mDetailTv.setText(desc);
        Picasso.with(ctx).load(image).into(mImageIv);

    }

    private BlogViewHolder.ClickListener mClickListener;


    //interface to send callback
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

    }
    public void setOnClickListener(BlogViewHolder.ClickListener clickListener){
        mClickListener = clickListener;

    }


}
