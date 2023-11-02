package kz.just_code.notificationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.MessagingStyle
import androidx.core.app.Person
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.IconCompat

class MyNotificationManager(context: Context) {
    private val bigText =
        "Channel for just code notifications Channel for just code notifications Channel for just code notifications Channel for just code notifications Channel for just code notifications Channel for just code notifications Channel for just code notifications Channel for just code notifications"

    private val notification = NotificationCompat.Builder(context, getChannelId(context))
        .setSmallIcon(R.drawable.ic_clock)
        .setContentTitle("My custom title")
        .setContentText("My one line notification text")
        .setSubText("Sub text")
        .setOngoing(true)
        .setVibrate(LongArray(250))
        .setContentIntent(getDeleteAction(context))
        //.setLargeIcon(context.createBitmap(R.drawable.ic_large))
        .setColor(ContextCompat.getColor(context, R.color.red))
        //.setProgress(100, 90, false)
        /*.setStyle(
            // BigPictureStyle().bigPicture(context.createBitmap(R.drawable.ic_large))
            // BigTextStyle().bigText(bigText)
            *//*InboxStyle()
                .addLine("1. Line")
                .addLine("2. Line")
                .addLine("3. Line")
                .setBigContentTitle("Big content title")*//*
            getMessagingStyle(context)
        )*/
        .addAction(android.R.drawable.ic_delete, "Show", getShowAction(context))
        .addAction(android.R.drawable.ic_delete, "Delete", getDeleteAction(context))
        .build()

    private fun getMessagingStyle(context: Context): MessagingStyle {
        val sender = Person.Builder()
            .setName("You")
            .setIcon(IconCompat.createWithBitmap(context.createBitmap(R.drawable.ic_favorite)))
            .build()

        val ivan = Person.Builder()
            .setName("Ivan")
            .setIcon(IconCompat.createWithBitmap(context.createBitmap(R.drawable.ic_notification)))
            .build()

        val style = MessagingStyle(sender)
        style.conversationTitle = "Just code chat"
        style.addMessage("Hello", System.currentTimeMillis(), ivan)
            .addMessage("Hello Ivan", System.currentTimeMillis(), sender)
            .addMessage("How are you?", System.currentTimeMillis(), ivan)
            .addMessage("I am very good", System.currentTimeMillis(), sender)

        return style
    }

    private fun getDeleteAction(context: Context): PendingIntent {
        val deleteAction = Intent(context, MainActivity::class.java)
        deleteAction.putExtra("ACTION", "DELETE")

        return PendingIntent.getActivity(context, 0, deleteAction, PendingIntent.FLAG_MUTABLE)
    }

    private fun getShowAction(context: Context): PendingIntent {
        val deleteAction = Intent(context, MainActivity::class.java)
        deleteAction.putExtra("ACTION", "SHOW")

        return PendingIntent.getActivity(context, 0, deleteAction, PendingIntent.FLAG_MUTABLE)
    }

    private var id: Int = 6535

    fun showMessage(context: Context) {
        val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(id, notification)
        id++
    }

    private fun getChannelId(context: Context): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "JustCodeNotification"
            val channelName = "JustCodeName"
            val channelDescription = "Channel for just code notifications"
            val channelImportance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, channelImportance)
            channel.description = channelDescription

            val manager =
                context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

            channelId
        } else ""
    }

    fun clear(context: Context) {
        val manager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.cancelAll()
    }
}

fun Context.createBitmap(@DrawableRes icon: Int): Bitmap {
    return BitmapFactory.decodeResource(this.resources, icon, BitmapFactory.Options())
}