package dore.notchannel;

import android.content.Context;

public class UnityContextHelper {
    private static Context applicationContext;

    public static void setApplicationContext(Context context) {
        applicationContext = context;
    }

    public static Context getApplicationContext() {
        return applicationContext;
    }
}