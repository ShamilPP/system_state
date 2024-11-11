import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';
import '../model/wifi_state.dart';

/// Manages Wi-Fi-related information and operations.
class WifiController {
  WifiController() {
    // Platform check to ensure the plugin only works on Android
    if (Platform.isAndroid == false) {
      throw UnsupportedError(
          'SystemStats functionality is only supported on Android');
    }
  }

  static const MethodChannel _methodChannel =
      MethodChannel('system_state/methods');
  static const EventChannel _eventChannel =
      EventChannel('system_state/wifi_events');

  /// Retrieves the current Wi-Fi state.
  Future<WifiState> getWifi() async {
    try {
      final result =
          await _methodChannel.invokeMapMethod<String, dynamic>('getWifi');
      if (result == null) {
        throw Exception("Failed to retrieve Wi-Fi state.");
      }
      return WifiState.fromMap(result);
    } catch (e) {
      throw Exception("Error fetching Wi-Fi state: $e");
    }
  }

  /// Listens to Wi-Fi state changes.
  StreamSubscription listen(void Function(WifiState) callback) {
    try {
      return _eventChannel
          .receiveBroadcastStream('listenWifiState')
          .listen((data) {
        final wifiState = WifiState.fromMap(Map<String, dynamic>.from(data));
        callback(wifiState);
      });
    } catch (e) {
      throw Exception("Error listening to Wi-Fi state changes: $e");
    }
  }

  /// Enables or disables the Wi-Fi.
  Future<void> setWifiState(bool enable) async {
    try {
      await _methodChannel.invokeMethod('setWifi', {'wifi': enable});
    } catch (e) {
      throw Exception("Error setting Wi-Fi state: $e");
    }
  }
}
