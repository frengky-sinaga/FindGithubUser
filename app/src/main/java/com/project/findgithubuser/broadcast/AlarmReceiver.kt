package com.project.findgithubuser.broadcast

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.project.findgithubuser.R
import com.project.findgithubuser.views.MainActivity
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIF_ID = 0
        private const val TIME_FORMAT = "HH:mm"
        private const val TIME = "09:00"
    }

    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(context)
    }

    fun setRepeatingAlarm(context: Context) {
        if(isTimeInvalid()) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val timeArray = TIME.split(":").toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIF_ID, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

    private fun showAlarmNotification(context: Context) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "GithubAlarm channel"
        val TITLE = context.resources.getString(R.string.app_name)
        val MESSAGE = context.resources.getString(R.string.alarmMessage)

        val pendingIntent: PendingIntent = Intent(context, MainActivity::class.java).let {
            PendingIntent.getActivity(context, 0, it, 0)
        }

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.github)
            .setContentTitle(TITLE)
            .setContentText(MESSAGE)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(NOTIF_ID, notification)
    }

    private fun isTimeInvalid(): Boolean {
        return try {
            val df = SimpleDateFormat(TIME_FORMAT, Locale.getDefault())
            df.isLenient = false
            df.parse(TIME)
            false
        } catch (e: ParseException) {
            true
        }
    }
}
