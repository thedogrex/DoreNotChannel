package dore.notchannel;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class NFCServiceManager extends Service {

    private static final String TAG = "NFCServiceManager";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "HCE service started");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "HCE service stopped");
    }

    // Method to stop the service from Unity
    public void stopNFCService() {
        stopSelf();
    }
}