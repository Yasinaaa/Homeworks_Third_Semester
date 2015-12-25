package com.github.sneepin.itisworkwithimage.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.sneepin.itisworkwithimage.R;
import com.github.sneepin.itisworkwithimage.activity.ImageActivity;
import com.github.sneepin.itisworkwithimage.content.Image;
import com.github.sneepin.itisworkwithimage.ui.PostsRecyclerViewAdapter;
import com.github.sneepin.itisworkwithimage.util.ImagesListCreator;

import java.util.List;
import java.util.Observable;

import rx.Scheduler;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * @author ravil
 */
public class ImagesFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_images, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        List<Image> imageList = ImagesListCreator.createImagesList();

        rx.Observable.create(new rx.Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {

            }
        }).subscribeOn(Schedulers.newThread());

        PostsRecyclerViewAdapter adapter = new PostsRecyclerViewAdapter(getActivity(), imageList);
        adapter.setOnImageClickListener(new PostsRecyclerViewAdapter.OnImageClickListener() {
            @Override
            public void onCLick(String imageUrl) {
                ImageActivity.start(getActivity(), imageUrl);
            }
        });

        mRecyclerView.setAdapter(adapter);
    }
}
