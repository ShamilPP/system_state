package com.shamil.system_state;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.EventChannel;

public class SystemStatePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
  private MethodChannel methodChannel;
  private EventChannel eventChannel;
  private Context context;
  private BatteryMethod batteryManager;
  private VolumeMethod volumeManager;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/methods");
    eventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/events");

    context = flutterPluginBinding.getApplicationContext();

    batteryManager = new BatteryMethod(context);
    volumeManager = new VolumeMethod(context);

    methodChannel.setMethodCallHandler(this);
    eventChannel.setStreamHandler(this);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    eventChannel.setStreamHandler(null);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
    switch (call.method) {
      case "getBatteryState":
        result.success(batteryManager.getBatteryState());
        break;
      case "getVolume":
        result.success(volumeManager.getVolume());
        break;
      case "setVolume":
        int volume = call.argument("volume");
        volumeManager.setVolume(volume);
        result.success(null);
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @Override
  public void onListen(Object arguments, EventChannel.EventSink events) {
    if ("listenBatteryState".equals(arguments)) {
      batteryManager.startListeningBatteryState(events);
    } else if ("listenVolumeState".equals(arguments)) {
      volumeManager.startListeningVolume(events);
    }
  }

  @Override
  public void onCancel(Object arguments) {
    if ("listenBatteryState".equals(arguments)) {
      batteryManager.stopListeningBatteryState();
    } else if ("listenVolumeState".equals(arguments)) {
      volumeManager.stopListeningVolume();
    }
  }
}
