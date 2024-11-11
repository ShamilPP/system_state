import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';
import '../model/volume_state.dart';

/// Manages volume-related information and operations.
class VolumeController {
  VolumeController() {
    // Platform check to make sure the plugin only works on Android
    if (Platform.isAndroid == false) {
      throw UnsupportedError(
          'SystemStats functionality is only supported on Android');
    }
  }

  static const MethodChannel _methodChannel =
      MethodChannel('system_state/methods');
  static const EventChannel _eventChannel =
      EventChannel('system_state/volume_events');

  /// Retrieves the current volume level.
  Future<VolumeState> getVolume() async {
    try {
      final result =
          await _methodChannel.invokeMapMethod<String, dynamic>('getVolume');
      if (result == null) {
        throw Exception("Failed to retrieve volume state.");
      }
      return VolumeState.fromMap(result);
    } catch (e) {
      throw Exception("Error fetching volume state: $e");
    }
  }

  /// Listens to volume changes.
  StreamSubscription listen(void Function(VolumeState) callback) {
    try {
      return _eventChannel
          .receiveBroadcastStream('listenVolume')
          .listen((data) {
        final volumeState =
            VolumeState.fromMap(Map<String, dynamic>.from(data));
        callback(volumeState);
      });
    } catch (e) {
      throw Exception("Error listening to volume state changes: $e");
    }
  }

  /// Sets the system volume.
  Future<void> setVolume(int volume) async {
    try {
      await _methodChannel.invokeMethod('setVolume', {'volume': volume});
    } catch (e) {
      throw Exception("Error setting volume: $e");
    }
  }
}
