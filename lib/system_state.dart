import 'services/battery_controller.dart';
import 'services/volume_controller.dart';
import 'services/wifi_controller.dart';
import 'services/mobile_data_controller.dart';

export 'model/battery_state.dart';
export 'model/volume_state.dart';
export 'model/wifi_state.dart';
export 'model/mobile_data_state.dart';

/// The main entry point for accessing and managing various system states
/// on Android devices, such as battery, volume, Wi-Fi, and mobile data.
///
/// This class provides easy-to-use static controllers for monitoring
/// and controlling these functionalities, ensuring a modular and extensible design.
class SystemState {
  /// Provides access to battery-related functionality.
  ///
  /// Use this controller to:
  /// - Retrieve the current battery level, temperature, and charging status.
  /// - Listen to changes in battery state.
  static final BatteryController battery = BatteryController();

  /// Provides access to volume-related functionality.
  ///
  /// Use this controller to:
  /// - Get the current system volume level.
  /// - Set a new volume level.
  /// - Listen to volume changes.
  static final VolumeController volume = VolumeController();

  /// Provides access to Wi-Fi-related functionality.
  ///
  /// Use this controller to:
  /// - Get the current Wi-Fi state (enabled/disabled, connected/disconnected).
  /// - Toggle Wi-Fi on or off.
  /// - Listen to Wi-Fi state changes.
  static final WifiController wifi = WifiController();

  /// Provides access to mobile data-related functionality.
  ///
  /// Use this controller to:
  /// - Check if mobile data is enabled.
  /// - Retrieve the SIM operator name, network operator, and network type (e.g., 4G, 5G).
  /// - Enable or disable mobile data.
  /// - Listen to mobile data state changes.
  static final MobileDataController mobileData = MobileDataController();
}
