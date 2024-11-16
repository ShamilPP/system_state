package com.shamil.system_state;

import android.content.Context;
import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.EventChannel;

public class SystemStatePlugin implements FlutterPlugin, MethodChannel.MethodCallHandler, EventChannel.StreamHandler {
  private MethodChannel methodChannel;
  private EventChannel batteryEventChannel, volumeEventChannel, wifiEventChannel, mobileDataEventChannel;
  private Context context;
  private BatteryMethod batteryManager;
  private VolumeMethod volumeManager;
  private WifiMethod wifiManager;
  private MobileDataMethod mobileDataManager;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    methodChannel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/methods");
    batteryEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/battery_events");
    volumeEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/volume_events");
    wifiEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/wifi_events");
    mobileDataEventChannel = new EventChannel(flutterPluginBinding.getBinaryMessenger(), "system_state/mobile_data_events");

    context = flutterPluginBinding.getApplicationContext();

    batteryManager = new BatteryMethod(context);
    volumeManager = new VolumeMethod(context);
    wifiManager = new WifiMethod(context);
    mobileDataManager = new MobileDataMethod(context);

    methodChannel.setMethodCallHandler(this);
    batteryEventChannel.setStreamHandler(this);
    volumeEventChannel.setStreamHandler(this);
    wifiEventChannel.setStreamHandler(this);
    mobileDataEventChannel.setStreamHandler(this);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    methodChannel.setMethodCallHandler(null);
    batteryEventChannel.setStreamHandler(null);
    volumeEventChannel.setStreamHandler(null);
    wifiEventChannel.setStreamHandler(null);
    mobileDataEventChannel.setStreamHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
    switch (call.method) {
      case "getBattery":
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
      case "getWifi":
        result.success(wifiManager.getWifiState());
        break;
      case "setWifi":
        boolean wifiEnable = call.argument("wifi");
        boolean wifiSuccess = wifiManager.setWifiState(wifiEnable);
        result.success(wifiSuccess);
        break;
      case "getMobileData":
        result.success(mobileDataManager.getMobileDataState());
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @Override
  public void onListen(Object arguments, EventChannel.EventSink events) {
    if ("listenBattery".equals(arguments)) {
      batteryManager.startListeningBatteryState(events);
    } else if ("listenVolume".equals(arguments)) {
      volumeManager.startListeningVolume(events);
    } else if ("listenWifiState".equals(arguments)) {
      wifiManager.startListeningWifiState(events);
    } else if ("listenMobileDataState".equals(arguments)) {
      mobileDataManager.startListeningMobileDataState(events);
    }
  }

  @Override
  public void onCancel(Object arguments) {
    if ("listenBattery".equals(arguments)) {
      batteryManager.stopListeningBatteryState();
    } else if ("listenVolume".equals(arguments)) {
      volumeManager.stopListeningVolume();
    } else if ("listenWifiState".equals(arguments)) {
      wifiManager.stopListeningWifiState();
    } else if ("listenMobileDataState".equals(arguments)) {
      mobileDataManager.stopListeningMobileDataState();
    }
  }
}
