import 'services/battery_controller.dart';
import 'services/volume_controller.dart';
import 'services/wifi_controller.dart';
import 'services/mobile_data_controller.dart';

export 'model/battery_state.dart';
export 'model/volume_state.dart';
export 'model/wifi_state.dart';
export 'model/mobile_data_state.dart';

/// The main entry point for accessing device statistics.
class SystemState {
  /// Provides access to battery-related functionality.
  static final BatteryController battery = BatteryController();

  /// Provides access to volume-related functionality.
  static final VolumeController volume = VolumeController();

  /// Provides access to wifi-related functionality.
  static final WifiController wifi = WifiController();

  /// Provides access to mobile data-related functionality.
  static final MobileDataController mobileData = MobileDataController();
}
