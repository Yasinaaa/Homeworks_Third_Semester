package itis.homework.parallelrequests;

import android.app.Fragment;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import itis.homework.parallelrequests.app.AppDelegate;
import itis.homework.parallelrequests.network.RequestsService;

/**
 * @author Artur Vasilov
 */
public class MainFragment extends Fragment implements Loader.OnLoadCompleteListener<Void> {

    private boolean mIsConfigLoaded;
    private boolean mIsAuthLoaded;
    private boolean mIsMessagesLoaded;

    private RequestsService mRequestsService;

    private ConfigLoader mConfigLoader;
    private AuthLoader mAuthLoader;

    private FriendsLoader mFriendsLoader;
    private PostsLoader mPostsLoader;
    private GroupsLoader mGroupsLoader;
    private MessagesLoader mMessageLoader;

    private PhotosLoader mPhotosLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestsService = AppDelegate.get(getActivity()).getRequestsService();

        mConfigLoader = new ConfigLoader(getActivity());
        mAuthLoader = new AuthLoader(getActivity());
        mFriendsLoader = new FriendsLoader(getActivity());
        mPostsLoader = new PostsLoader(getActivity());
        mGroupsLoader = new GroupsLoader(getActivity());
        mMessageLoader = new MessagesLoader(getActivity());
        mPhotosLoader = new PhotosLoader(getActivity());

        mConfigLoader.registerListener(0, this);
        mAuthLoader.registerListener(1, this);
        mFriendsLoader.registerListener(2, this);
        mPostsLoader.registerListener(3, this);
        mGroupsLoader.registerListener(4, this);
        mMessageLoader.registerListener(5, this);
        mPhotosLoader.registerListener(6, this);

        mIsConfigLoaded = false;
        mIsAuthLoaded = false;
        mIsMessagesLoaded = false;

        mConfigLoader.forceLoad();
        mAuthLoader.forceLoad();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onLoadComplete(Loader<Void> loader, Void data) {

        switch (loader.getId()) {
            case 0:
                mIsConfigLoaded = true;
                break;
            case 1:
                mIsAuthLoaded = true;
                break;
            case 5:
                mIsMessagesLoaded = true;
                break;
        }

        if ((loader instanceof ConfigLoader || loader instanceof AuthLoader)
                && mIsConfigLoaded && mIsAuthLoaded) {
            mFriendsLoader.forceLoad();
            mPostsLoader.forceLoad();
            mGroupsLoader.forceLoad();
            mMessageLoader.forceLoad();
        }

        if (mIsMessagesLoaded) {
            mPhotosLoader.forceLoad();
        }
    }

    public class ConfigLoader extends AsyncTaskLoader<Void> {


        public ConfigLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            mRequestsService.config();
            return null;
        }
    }
    public class AuthLoader extends AsyncTaskLoader<Void> {

        public AuthLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            mRequestsService.auth();
            return null;
        }
    }
    public class FriendsLoader extends AsyncTaskLoader<Void> {

        public FriendsLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            mRequestsService.friends();
            return null;
        }
    }
    public class PostsLoader extends AsyncTaskLoader<Void> {

        public PostsLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            mRequestsService.posts();
            return null;
        }
    }
    public class GroupsLoader extends AsyncTaskLoader<Void> {

        public GroupsLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            mRequestsService.groups();
            return null;
        }
    }
    public class MessagesLoader extends AsyncTaskLoader<Void> {

        public MessagesLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            mRequestsService.messages();
            return null;
        }
    }
    public class PhotosLoader extends AsyncTaskLoader<Void> {

        public PhotosLoader(Context context) {
            super(context);
        }

        @Override
        public Void loadInBackground() {
            mRequestsService.photos();
            return null;
        }
    }
}
