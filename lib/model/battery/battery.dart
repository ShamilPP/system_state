import 'package:flutter/services.dart';

import 'battery_state.dart';

/// Manages battery-related information and operations.
class Battery {
  static const MethodChannel _methodChannel = MethodChannel('system_state/methods');
  static const EventChannel _eventChannel = EventChannel('system_state/events');

  /// Retrieves the current battery state, including level, temperature, and charging status.
   Future<BatteryState> getBatteryState() async {
    final result = await _methodChannel.invokeMapMethod<String, dynamic>('getBatteryState');
    return BatteryState.fromMap(result!);
  }

  /// Listens to battery state changes.
   Stream<BatteryState> listen(void Function(BatteryState) callback) {
    return _eventChannel.receiveBroadcastStream().map((dynamic event) {
      final batteryState = BatteryState.fromMap(Map<String, dynamic>.from(event));
      callback(batteryState);
      return batteryState;
    });
  }
}
