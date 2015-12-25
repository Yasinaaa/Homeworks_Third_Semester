package com.github.sneepin.itisworkwithimage.content;

/**
 * @author ravil
 */
public class Image {

    private final String mUrl;

    private final int mWidth;

    private final int mHeight;

    public Image(String url, int width, int height) {
        mUrl = url;
        mWidth = width;
        mHeight = height;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }
}
