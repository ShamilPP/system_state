import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';
import '../model/battery_state.dart';

/// Manages battery-related information and operations.
class BatteryController {
  BatteryController() {
    // Platform check to make sure the plugin only works on Android
    if (Platform.isAndroid == false) {
      throw UnsupportedError(
          'SystemStats functionality is only supported on Android');
    }
  }

  static const MethodChannel _methodChannel =
      MethodChannel('system_state/methods');
  static const EventChannel _eventChannel =
      EventChannel('system_state/battery_events');

  /// Retrieves the current battery state, including level, temperature, and charging status.
  Future<BatteryState> getBattery() async {
    try {
      final result =
          await _methodChannel.invokeMapMethod<String, dynamic>('getBattery');
      if (result == null) {
        throw Exception("Failed to retrieve battery state.");
      }
      return BatteryState.fromMap(result);
    } catch (e) {
      throw Exception("Error fetching battery state: $e");
    }
  }

  /// Listens to battery state changes.
  StreamSubscription listen(void Function(BatteryState) callback) {
    try {
      return _eventChannel
          .receiveBroadcastStream('listenBattery')
          .listen((data) {
        final batteryState =
            BatteryState.fromMap(Map<String, dynamic>.from(data));
        callback(batteryState);
      });
    } catch (e) {
      throw Exception("Error listening to battery state changes: $e");
    }
  }
}
