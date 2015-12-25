package com.github.sneepin.itisworkwithimage.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.sneepin.itisworkwithimage.R;
import com.github.sneepin.itisworkwithimage.content.Image;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class PostsRecyclerViewAdapter extends RecyclerView.Adapter<PostsRecyclerViewAdapter.ImageHolder>  {

    private List<Image> mDataSet;
    private Context mContext;
    private OnImageClickListener mOnImageClickListener;

    public PostsRecyclerViewAdapter(@NonNull Context context, @NonNull List<Image> imageList) {
        mContext = context;

        mDataSet = imageList;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        mOnImageClickListener = onImageClickListener;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image, parent, false);

        return new ImageHolder(view);
    }



    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        final Image image = mDataSet.get(position);

        holder.mMainImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnImageClickListener.onCLick(image.getUrl());
            }
        });

        Glide.with(mContext)
                .load(image.getUrl())
                .placeholder(R.drawable.yellow)
                .error(R.drawable.red)
                .bitmapTransform(new BlurTransformation(mContext))
                .into(holder.mMainImageView);

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView mMainImageView;

        public ImageHolder(View itemView) {
            super(itemView);

            mMainImageView = (ImageView) itemView.findViewById(R.id.main_image_view);
        }
    }

    public interface OnImageClickListener {

        void onCLick(String imageUrl);
    }
}
