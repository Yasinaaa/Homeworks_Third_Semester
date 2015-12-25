package com.github.sneepin.itisworkwithimage.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.github.sneepin.itisworkwithimage.fragment.ImageFragment;

/**
 * @author ravil
 */
public class ImageActivity extends BaseActivity {

    private static final String IMAGE_URL_EXTRA = "image_url";

    public static void start(@NonNull Context context, @NonNull String imageUrl) {
        Intent intent = new Intent(context, ImageActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_URL_EXTRA, imageUrl);

        intent.putExtras(bundle);

        context.startActivity(intent);
    }

    @NonNull
    @Override
    protected Fragment getFragment() {
        String imageUrl = getIntent().getExtras().getString(IMAGE_URL_EXTRA);

        return ImageFragment.newInstance(imageUrl);
    }
}
