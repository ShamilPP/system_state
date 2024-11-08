import 'model/battery/battery.dart';

export 'model/battery/battery_state.dart';

/// The main entry point for accessing device statistics.
class DeviceStats {
  /// Provides access to battery-related functionality.
  static final Battery battery = Battery();
}
