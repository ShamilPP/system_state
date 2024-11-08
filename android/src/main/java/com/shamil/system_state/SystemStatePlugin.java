package com.shamil.system_state;

import androidx.annotation.NonNull;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import java.util.Map;
import java.util.HashMap;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.EventChannel.StreamHandler;

/**
 * Plugin to provide system state information such as battery status.
 */
public class SystemStatePlugin implements FlutterPlugin, MethodCallHandler, StreamHandler {
  private MethodChannel methodChannel;
  private EventChannel eventChannel;
  private Context context;
  private BroadcastReceiver batteryReceiver;
  private EventChannel.EventSink eventSink;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPlugin.FlutterPluginBinding flutterPluginBinding) {
    methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/methods");
    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/events");

    methodChannel.setMethodCallHandler(this);
    eventChannel.setStreamHandler(this);
    context = flutterPluginBinding.getApplicationContext();
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getBatteryState")) {
      result.success(getBatteryState());
    } else {
      result.notImplemented();
    }
  }

  /**
   * Retrieves the current battery state, including level, temperature, and charging status.
   *
   * @return a Map with battery level, temperature, and charging status.
   */
  private Map<String, Object> getBatteryState() {
    Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    int temperature = batteryIntent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1);
    int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

    boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

    Map<String, Object> batteryState = new HashMap<>();
    batteryState.put("level", level);
    batteryState.put("temperature", temperature);
    batteryState.put("isCharging", isCharging);

    return batteryState;
  }

  @Override
  public void onListen(Object arguments, EventChannel.EventSink events) {
    eventSink = events;

    batteryReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if (intent.getAction() == null || !intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) return;

        Map<String, Object> batteryState = getBatteryState();
        eventSink.success(batteryState);
      }
    };

    IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    context.registerReceiver(batteryReceiver, filter);
  }

  @Override
  public void onCancel(Object arguments) {
    context.unregisterReceiver(batteryReceiver);
    batteryReceiver = null;
    eventSink = null;
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPlugin.FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    eventChannel.setStreamHandler(null);
  }
}
