package main.master.c31.Service

import android.content.ContentValues.TAG
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Typeface
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import main.master.c31.R
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        Log.d(TAG, "From: ${remoteMessage?.from}")
        // Check if message contains a notification payload.
        remoteMessage?.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}"+it.imageUrl.toString())

            val url = URL(it.imageUrl.toString())
            val `in`: InputStream
            //message = params[0] + params[1];
            //URL url = new URL(params[2]);
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            `in` = connection.getInputStream()
            val image = BitmapFactory.decodeStream(`in`)

            val spannableString = SpannableString(remoteMessage.from)
            val fontColor = ContextCompat.getColor(this, android.R.color.holo_red_dark)
            spannableString.setSpan(ForegroundColorSpan(fontColor), 0, 12, 0)


            val spannableStringContentText = SpannableString(it.body)
            val fontColorContentText = ContextCompat.getColor(this, android.R.color.holo_red_dark)
            spannableStringContentText.setSpan(StyleSpan(Typeface.BOLD_ITALIC), 0, 16, 0)
            spannableStringContentText.setSpan(UnderlineSpan(), 34, 37, 0)
            spannableStringContentText.setSpan(StyleSpan(Typeface.BOLD), 34, 37, 0)

            //Message Services handle notification
            val notification = NotificationCompat.Builder(this)
                .setContentTitle(spannableString)
                .setContentText(spannableStringContentText)
                .setSmallIcon(R.mipmap.appicon)
                .setStyle(NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null)
                    // Overrides ContentTitle in the big form of the template.
                    .setBigContentTitle(spannableString)

                    // Set the first line of text after the detail section in the big form of the template.
                    .setSummaryText(spannableStringContentText)
                )
                .setOngoing(true)
                .setColor(getApplicationContext().getResources().getColor(R.color.colorPrimaryDark))



//            val futureTarget = Glide.with(this)
//                .asBitmap()
//                .load(it.imageUrl)
//                .submit()
//
//            val bitmap = futureTarget.get()
//            notification.setLargeIcon(bitmap)
//
//            Glide.with(this).clear(futureTarget)
//


            val manager = NotificationManagerCompat.from(applicationContext)
            manager.notify(/*notification id*/0, notification.build())

        }
    }

    override fun onNewToken(token: String) {
        //handle token
    }
}