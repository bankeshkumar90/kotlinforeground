package com.fourcpplus.kotlinforegrounddemo

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class Foreground : Service() {
    private val CHANNEL_ID = "Foreground"

    companion object {
        fun startService(context: Context, message: String){
            val startIntent = Intent(context, Foreground::class.java)
            startIntent.putExtra("inputMessage",message);
            ContextCompat.startForegroundService(context, startIntent);
        }
        fun stopService(context: Context){
            val stopIntent = Intent(context, Foreground::class.java)
            context.stopService(stopIntent)
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("inputMessage");
        createNotificationChannel(input);
        val  notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, 0)
         val notification = NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("Kotlin Demo")
             .setContentText(input).setSmallIcon(R.drawable.ic_launcher_background)
             .setContentIntent(pendingIntent)
             .build()
        startForeground(1, notification)

        return START_NOT_STICKY
    }

    fun createNotificationChannel(message: String?){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID,"Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)

        }
    }

}