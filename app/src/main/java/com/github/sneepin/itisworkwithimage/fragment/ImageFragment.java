package com.github.sneepin.itisworkwithimage.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.sneepin.itisworkwithimage.R;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author ravil
 */
public class ImageFragment extends Fragment {

    private static final String IMAGE_URL_ARG = "image_url";

    public static Fragment newInstance(String imageUrl) {
        Fragment fragment = new ImageFragment();

        Bundle args = new Bundle();
        args.putString(IMAGE_URL_ARG, imageUrl);

        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);

        String imageUrl = getArguments().getString(IMAGE_URL_ARG);

        Glide.with(this)
                .load(imageUrl)
                .into(imageView);

    }
}
