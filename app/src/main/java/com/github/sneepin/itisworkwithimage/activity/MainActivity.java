package com.github.sneepin.itisworkwithimage.activity;

import android.app.Fragment;
import android.support.annotation.NonNull;

import com.github.sneepin.itisworkwithimage.fragment.ImagesFragment;

public class MainActivity extends BaseActivity {

    @NonNull
    @Override
    protected Fragment getFragment() {
        return new ImagesFragment();
    }
}
