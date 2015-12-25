package ru.itis.myweather2.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by yasina on 08.11.15.
 */
public class AuthenticatorService extends Service {
    private Authenticator mAuthenticator;

    @Override
    public void onCreate() {
        mAuthenticator = new Authenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
