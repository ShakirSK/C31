package main.master.c31.Service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Typeface
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.core.content.ContextCompat
import main.master.c31.LauncherMainActivity.LoginActivity
import main.master.c31.R
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage?.data}")

        // Check if message contains a data payload.
        remoteMessage?.data?.isNotEmpty()?.let {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)

            // Compose and show notification
            if (!remoteMessage.data.isNullOrEmpty()) {
              //  val msg: String = remoteMessage.data.get("message").toString()
              //  sendNotification(msg)
                sendNotification(remoteMessage)
            }

        }

        // Check if message contains a notification payload.
       /* remoteMessage?.notification?.let {
            sendNotification(remoteMessage)
        }*/
    }
    override fun onNewToken(token: String) {
        //handle token
        Log.e("newToken", token);
    }

    private fun sendNotification(messageBody: RemoteMessage?) {

     //   String url = remoteMessage.getData().get("url");
        val urllink = messageBody!!.data.get("url")
        Log.d(TAG, "Message data payloadurl: " + urllink)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.setData(Uri.parse(urllink));
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val url = URL(messageBody!!.data.get("bigimage"))
        val `in`: InputStream
        //message = params[0] + params[1];
        //URL url = new URL(params[2]);
        val connection = url.openConnection() as HttpURLConnection
        connection.setDoInput(true)
        connection.connect()
        `in` = connection.getInputStream()
        val image = BitmapFactory.decodeStream(`in`)


        val spannableString = SpannableString(messageBody!!.data.get("title"))
        val fontColor = ContextCompat.getColor(this, android.R.color.holo_red_dark)
        spannableString.setSpan(ForegroundColorSpan(fontColor), 0, 12, 0)


        val spannableStringContentText = SpannableString(messageBody!!.data.get("body"))
        val fontColorContentText = ContextCompat.getColor(this, android.R.color.holo_red_dark)
        spannableStringContentText.setSpan(StyleSpan(Typeface.BOLD_ITALIC), 0, 16, 0)
        spannableStringContentText.setSpan(UnderlineSpan(), 34, 37, 0)
        spannableStringContentText.setSpan(StyleSpan(Typeface.BOLD), 34, 37, 0)


        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.appicon)
            .setLargeIcon(image)
            .setContentTitle(messageBody!!.data.get("title"))
            .setContentText(messageBody!!.data.get("body"))
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(image)
                .bigLargeIcon(image)
                // Overrides ContentTitle in the big form of the template.
                .setBigContentTitle(messageBody!!.data.get("title"))

                // Set the first line of text after the detail section in the big form of the template.
                .setSummaryText(messageBody!!.data.get("body"))
            )
            .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        // https://developer.android.com/training/notify-user/build-notification#Priority
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Channel human readable title", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notificationBuilder.build())
    }
}