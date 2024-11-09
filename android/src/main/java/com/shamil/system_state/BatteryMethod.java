package com.shamil.system_state;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import io.flutter.plugin.common.EventChannel;
import java.util.HashMap;
import java.util.Map;

public class BatteryMethod {
    private final Context context;
    private BroadcastReceiver batteryReceiver;
    private EventChannel.EventSink eventSink;

    public BatteryMethod(Context context) {
        this.context = context;
    }

    // Retrieves the current battery state as a map
    public Map<String, Object> getBatteryState() {
        Intent batteryStatus = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        if (batteryStatus == null) return new HashMap<>();

        Map<String, Object> batteryState = new HashMap<>();
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int temperature = batteryStatus.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = (status == BatteryManager.BATTERY_STATUS_CHARGING) ||
                (status == BatteryManager.BATTERY_STATUS_FULL);

        batteryState.put("level", level);
        batteryState.put("temperature", temperature);
        batteryState.put("isCharging", isCharging);

        return batteryState;
    }

    // Starts listening for battery state changes and sends updates to Flutter
    public void startListeningBatteryState(EventChannel.EventSink events) {
        this.eventSink = events;

        if (batteryReceiver == null) {
            batteryReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction()) && eventSink != null) {
                        eventSink.success(getBatteryState());
                    }
                }
            };
            context.registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        }
    }

    // Stops listening for battery state changes
    public void stopListeningBatteryState() {
        if (batteryReceiver != null) {
            context.unregisterReceiver(batteryReceiver);
            batteryReceiver = null;
        }
    }
}