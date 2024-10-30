package dore.notchannel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

public class NotChannel {

    private static final String TAG = "NotificationUtils";


    public static void Create_Sound_Channel(
            Context context,
            String channelId,
            String channelName,
            String soundName
    ) {
        // Check if the Android version supports notification channels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


            //int rid = context.getResources().getIdentifier("join","raw", context.getPackageName());

            // Check if the channel is already created
            NotificationChannel existingChannel = notificationManager.getNotificationChannel(channelId);
            if (existingChannel == null) {


                Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName()
                            + "/raw/" + soundName);

                // Define audio attributes for the channel
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();

                // Create the notification channel
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Channel for " + channelName + " notifications");
                channel.setSound(soundUri, audioAttributes);

                // Register the channel with the system
                notificationManager.createNotificationChannel(channel);

                Log.d(TAG, channelId+ " Created Channel! " + soundUri.getPath());
            }
            else {
                Log.d(TAG, channelId+ " Channel " + channelId+ " already exists ");
            }
        }
    }

}