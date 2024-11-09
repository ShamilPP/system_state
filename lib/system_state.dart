import 'services/battery.dart';
import 'services/volume.dart';

export 'model/battery_state.dart';
export 'model/volume_state.dart';

/// The main entry point for accessing device statistics.
class SystemState {
  /// Provides access to battery-related functionality.
  static final Battery battery = Battery();

  /// Provides access to volume-related functionality.
  static final Volume volume = Volume();
}
