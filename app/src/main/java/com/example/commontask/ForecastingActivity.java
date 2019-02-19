package com.example.commontask;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.commontask.model.DetailedWeatherForecast;
import com.example.commontask.model.Location;
import com.example.commontask.model.LocationsDbHelper;
import com.example.commontask.service.ForecastWeatherService;
import com.example.commontask.utils.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.commontask.utils.LogToFile.appendLog;

public abstract class ForecastingActivity extends WeatherBaseActivity {

    private final String TAG = "ForecastingActivity";

    protected Map<Long, List<DetailedWeatherForecast>> weatherForecastList = new HashMap<>();
    protected Map<Long, Long> locationWeatherForecastLastUpdate = new HashMap<>();
    protected ConnectionDetector connectionDetector;
    private ProgressDialog mGetWeatherProgress;
    private Handler mHandler;
    protected BroadcastReceiver mWeatherUpdateReceiver;
    protected Location currentLocation;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((YourLocalWeather) getApplication()).applyTheme(this);
        super.onCreate(savedInstanceState);
        connectionDetector = new ConnectionDetector(this);
        mGetWeatherProgress = getProgressDialog();
        locationsDbHelper = LocationsDbHelper.getInstance(this);

        mHandler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case Constants.TASK_RESULT_ERROR:
                        Toast.makeText(ForecastingActivity.this,
                                R.string.toast_parse_error,
                                Toast.LENGTH_SHORT).show();
                        setVisibleUpdating(false);
                        break;
                    case Constants.PARSE_RESULT_ERROR:
                        Toast.makeText(ForecastingActivity.this,
                                R.string.toast_parse_error,
                                Toast.LENGTH_SHORT).show();
                        setVisibleUpdating(false);
                        break;
                    case Constants.PARSE_RESULT_SUCCESS:
                        setVisibleUpdating(false);
                        updateUI();
                        break;
                }
            }
        };

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGetWeatherProgress != null) {
            mGetWeatherProgress.dismiss();
        }
        unregisterReceiver(mWeatherUpdateReceiver);
    }

    protected abstract void updateUI();

    protected void setVisibleUpdating(boolean visible) {
        try {
            if (visible) {
                mGetWeatherProgress.show();
            } else {
                mGetWeatherProgress.cancel();
            }
        } catch (Exception e) {
            appendLog(getBaseContext(), TAG, "Exception in setVisibleUpdating method:", e);
        }
    }

    protected void updateWeatherForecastFromNetwork(String updateSource, Context context) {
        if (currentLocation == null) {
            return;
        }
        boolean isNetworkAvailable = connectionDetector.isNetworkAvailableAndConnected();
        if (isNetworkAvailable) {
            setVisibleUpdating(true);
            sendMessageToWeatherForecastService(currentLocation.getId(), updateSource);
        } else {
            Toast.makeText(getApplicationContext(),
                    R.string.connection_not_found,
                    Toast.LENGTH_SHORT).show();
        }
    }

    protected void initializeWeatherForecastReceiver(final String actionResult) {
        mWeatherUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                appendLog(context, TAG, "BroadcastReceiver:", intent);
                if ((mGetWeatherProgress != null) && (mHandler != null)) {
                    mHandler.post(new Runnable() {
                        public void run() {
                            if (mGetWeatherProgress != null) {
                                mGetWeatherProgress.dismiss();
                            }
                        }
                    });
                }
                switch (intent.getStringExtra(actionResult)) {
                    case ForecastWeatherService.ACTION_WEATHER_UPDATE_OK:
                        updateUI();
                        break;
                    case ForecastWeatherService.ACTION_WEATHER_UPDATE_FAIL:
                        Toast.makeText(ForecastingActivity.this,
                                getString(R.string.toast_parse_error),
                                Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
}
