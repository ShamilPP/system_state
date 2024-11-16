import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';
import '../model/mobile_data_state.dart';

/// Manages mobile data-related information and operations.
class MobileDataController {
  MobileDataController() {
    // Platform check to make sure the plugin only works on Android.
    if (Platform.isAndroid == false) {
      throw UnsupportedError(
          'MobileDataController functionality is only supported on Android');
    }
  }

  static const MethodChannel _methodChannel =
      MethodChannel('system_state/methods');
  static const EventChannel _eventChannel =
      EventChannel('system_state/mobile_data_events');

  /// Retrieves the current mobile data state.
  Future<MobileDataState> getMobileDataState() async {
    try {
      final result = await _methodChannel
          .invokeMapMethod<String, dynamic>('getMobileData');
      if (result == null) {
        throw Exception("Failed to retrieve mobile data state.");
      }
      return MobileDataState.fromMap(result);
    } catch (e) {
      throw Exception("Error fetching mobile data state: $e");
    }
  }

  /// Listens to mobile data state changes.
  StreamSubscription listen(void Function(MobileDataState) callback) {
    try {
      return _eventChannel
          .receiveBroadcastStream('listenMobileDataState')
          .listen((data) {
        final mobileDataState =
            MobileDataState.fromMap(Map<String, dynamic>.from(data));
        callback(mobileDataState);
      });
    } catch (e) {
      throw Exception("Error listening to mobile data state changes: $e");
    }
  }
}
