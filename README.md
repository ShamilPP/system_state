
# SystemState Plugin

`SystemState` is a Flutter plugin designed to provide access to essential device states and controls, currently supporting Android. The plugin allows you to monitor and control **battery** and **volume** states. Future updates will extend functionality and support additional platforms.

## Features

- **Battery State Monitoring**: Retrieve and listen to battery level, temperature, and charging status.
- **Volume Control**: Get the current system volume, set a new volume level, and listen to volume changes.

> **Note**: Currently, `SystemState` is Android-only. Platform checks ensure that unsupported platforms throw exceptions. Future versions will include support for iOS, Web, and more.

## Installation

To install the plugin, add the following line to your `pubspec.yaml`:

```yaml
dependencies:
  system_state: ^1.2.3
```

Then, run the following command to install the package:

```bash
flutter pub get
```

## Usage

### Import the Package

Import the `system_state` package into your Dart code:

```dart
import 'package:system_state/system_state.dart';
```

### Battery Monitoring

Use `SystemState.battery` to access battery-related features:

#### Get Battery State

```dart
// Getting the current battery state
Future<void> getBatteryState() async {
  try {
    final batteryState = await SystemState.battery.getBattery();
    print("Battery Level: ${batteryState.level}%");
    print("Battery Temperature: ${batteryState.temperature}°C");
    print("Is Charging: ${batteryState.isCharging}");
  } catch (e) {
    print("Error fetching battery state: $e");
  }
}
```

#### Listen to Battery State Changes

```dart
// Listening to battery state changes
void listenBatteryState() {
  SystemState.battery.listen((batteryState) {
    print("Battery updated - Level: ${batteryState.level}%, Charging: ${batteryState.isCharging}");
  });
}
```

### Volume Control

Use `SystemState.volume` to access volume-related features:

#### Get Current Volume Level

```dart
// Getting the current volume level
Future<void> getVolumeLevel() async {
  try {
    final volumeState = await SystemState.volume.getVolume();
    print("Current Volume Level: ${volumeState.level}");
  } catch (e) {
    print("Error fetching volume state: $e");
  }
}
```

#### Set Volume Level

```dart
// Setting the system volume
Future<void> setVolumeLevel(int level) async {
  try {
    await SystemState.volume.setVolume(level);
    print("Volume set to $level");
  } catch (e) {
    print("Error setting volume: $e");
  }
}
```

#### Listen to Volume Changes

```dart
// Listening to volume state changes
void listenVolumeState() {
  SystemState.volume.listen((volumeState) {
    print("Volume changed - Level: ${volumeState.level}");
  });
}
```

## API Reference

- `Battery.getBattery()`: Retrieves the current battery level, temperature, and charging status.
- `Battery.listen(void Function(BatteryState) callback)`: Listens for changes in battery status.
- `Volume.getVolume()`: Retrieves the current system volume level.
- `Volume.setVolume(int level)`: Sets the system volume level.
- `Volume.listen(void Function(VolumeState) callback)`: Listens for changes in the system volume level.

## Platform Support

| Platform | Battery | Volume |
|----------|---------|--------|
| Android  | ✅      | ✅     |
| iOS      | ❌      | ❌     |
| Web      | ❌      | ❌     |

> **Note**: Platform checks ensure the plugin throws an exception if accessed on non-Android platforms. Future versions will aim to support more platforms, including iOS and Web.

## Future Development

`SystemState` is actively under development. Future updates will introduce:

- **Additional Device States**:
   - Brightness Control: View and adjust screen brightness.
   - Network State: Monitor and control network states (Wi-Fi, Bluetooth, Mobile Data, etc.).
   - Storage Access: Retrieve storage info and manage permissions.

- **Cross-Platform Support**: Plans to support iOS, Web, and other platforms.

## Contributing

We welcome contributions! If you have ideas or suggestions, feel free to submit issues or pull requests.

## License

This project is licensed under the [MIT License](LICENSE).
