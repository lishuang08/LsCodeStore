package ls.yylx.lscodestore.base.ext

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.tencent.mmkv.MMKV
import ls.yylx.lscodestore.BuildConfig
import ls.yylx.lscodestore.MainActivity
import ls.yylx.lscodestore.R

fun MainActivity.showNotification(push: MainActivity.PushResults) {
    val last = MMKV.defaultMMKV().getInt("lastCount", 0)
    val lastTitle = MMKV.defaultMMKV().getString("lastTitle", "")

    if (NotificationManagerCompat.from(this )
            .areNotificationsEnabled()
    ) {
        if (last == 0 && lastTitle != push.title) {

            val builder = NotificationCompat.Builder(
                this,
                BuildConfig.APPLICATION_ID
            )
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(push.title)
                .setContentText("点击即可查看最新${push.num}条未读通知消息。")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .apply {
                    val startIntent = navController
                        .createDeepLink()
                        .setDestination(R.id.x5WebViewFragment)
                        .createPendingIntent()

                    setContentIntent(startIntent)
                }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    BuildConfig.APPLICATION_ID,
                    "应用名称",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "通知内容"
                }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
            with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }

            MMKV.defaultMMKV().putInt("lastCount", push.num)
            MMKV.defaultMMKV()
                .putString("lastTitle", push.title)
        }
    }
}