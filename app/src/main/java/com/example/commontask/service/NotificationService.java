package com.example.commontask.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

import com.example.commontask.MainActivity;
import com.example.commontask.R;
import com.example.commontask.model.CurrentWeatherDbHelper;
import com.example.commontask.model.Location;
import com.example.commontask.model.LocationsDbHelper;
import com.example.commontask.model.Weather;
import com.example.commontask.utils.AppPreference;
import com.example.commontask.utils.NotificationUtils;
import com.example.commontask.utils.TemperatureUtil;
import com.example.commontask.utils.Utils;

import static com.example.commontask.utils.LogToFile.appendLog;

public class NotificationService extends AbstractCommonService {

    private static final String TAG = "NotificationsService";

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        int ret = super.onStartCommand(intent, flags, startId);
        appendLog(getBaseContext(), TAG, "onStartCommand:", intent);

        if (intent == null) {
            return ret;
        }
        switch (intent.getAction()) {
            case "android.intent.action.START_WEATHER_NOTIFICATION_UPDATE": startWeatherCheck(); scheduleNextNotificationAlarm(); return ret;
            default: return ret;
        }
    }

    public void startWeatherCheck() {
        boolean isNotificationEnabled = AppPreference.isNotificationEnabled(getBaseContext());
        if (!isNotificationEnabled) {
            return;
        }
        Location currentLocation = NotificationUtils.getLocationForNotification(this);
        if (currentLocation == null) {
            return;
        }
        sendMessageToCurrentWeatherService(currentLocation, "NOTIFICATION", AppWakeUpManager.SOURCE_NOTIFICATION, true, true);
    }

    private void scheduleNextNotificationAlarm() {
        boolean isNotificationEnabled = AppPreference.isNotificationEnabled(getBaseContext());
        if (!isNotificationEnabled) {
            return;
        }
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        String intervalPref = AppPreference.getInterval(getBaseContext());
        if ("regular_only".equals(intervalPref)) {
            return;
        }
        long intervalMillis = Utils.intervalMillisForAlarm(intervalPref);
        appendLog(this, TAG, "Build.VERSION.SDK_INT:", Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + intervalMillis,
                    getPendingIntentForNotifiation());
        } else {
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + intervalMillis,
                    getPendingIntentForNotifiation());
        }
    }

    private PendingIntent getPendingIntentForNotifiation() {
        Intent sendIntent = new Intent("android.intent.action.START_WEATHER_NOTIFICATION_UPDATE");
        sendIntent.setPackage("com.example.commontask");
        PendingIntent pendingIntent = PendingIntent.getService(getBaseContext(), 0, sendIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }
}
