
# SystemState Plugin

`SystemState` is a Flutter plugin designed to provide access to essential device states and controls, currently supporting Android. The plugin allows you to monitor and control **Battery**, **Volume**, and **Wi-Fi** states. Future updates will extend functionality and support additional platforms.

## Features

- **Battery State Monitoring**: Retrieve and listen to battery level, temperature, and charging status.
- **Volume Control**: Get the current system volume, set a new volume level, and listen to volume changes.
- **Wi-Fi State Control**: Get the current Wi-Fi state, enable or disable Wi-Fi, and listen to Wi-Fi state changes.

> **Note**: Currently, `SystemState` is Android-only. Platform checks ensure that unsupported platforms throw exceptions. Future versions will include support for iOS, Web, and more.

## Installation

To install the plugin, add the following line to your `pubspec.yaml`:

```yaml
dependencies:
  system_state: ^1.2.4
```

Then, run the following command to install the package:

```bash
flutter pub get
```

## Permissions

To access and control various system states, the following Android permissions are required:

- **Volume Control**:
  ```xml
  <uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS"/>
  ```

- **Wi-Fi State**:
  - To read Wi-Fi and network state:
    ```xml
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    ```
  - To toggle Wi-Fi state:
    ```xml
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
    ```
  - To view connected Wi-Fi name (SSID), add the following permissions and ensure location services are enabled on the device:
    ```xml
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    ```

## Usage

### Import the Package

Import the `system_state` package into your Dart code:

```dart
import 'package:system_state/system_state.dart';
```

### Battery Monitoring

Use `SystemState.battery` to access battery-related features.

#### Get Battery State

```dart
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
void listenBatteryState() {
  SystemState.battery.listen((batteryState) {
    print("Battery updated - Level: ${batteryState.level}%, Charging: ${batteryState.isCharging}");
  });
}
```

### Volume Control

Use `SystemState.volume` to access volume-related features.

#### Get Current Volume Level

```dart
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
void listenVolumeState() {
  SystemState.volume.listen((volumeState) {
    print("Volume changed - Level: ${volumeState.level}");
  });
}
```

### Wi-Fi Control

Use `SystemState.wifi` to access Wi-Fi-related features.

#### Get Wi-Fi State

```dart
Future<void> getWifiState() async {
  try {
    final wifiState = await SystemState.wifi.getWifi();
    print("Wi-Fi Enabled: ${wifiState.isEnabled}");
    print("Wi-Fi Connected: ${wifiState.isConnected}");
    print("Connected Wi-Fi Name: ${wifiState.connectedWifiName}");
  } catch (e) {
    print("Error fetching Wi-Fi state: $e");
  }
}
```

#### Set Wi-Fi State

```dart
Future<void> setWifiState(bool enable) async {
  try {
    await SystemState.wifi.setWifiState(enable);
    print("Wi-Fi state set to ${enable ? 'Enabled' : 'Disabled'}");
  } catch (e) {
    print("Error setting Wi-Fi state: $e");
  }
}
```

#### Listen to Wi-Fi State Changes

```dart
void listenWifiState() {
  SystemState.wifi.listen((wifiState) {
    print("Wi-Fi State Changed - Enabled: ${wifiState.isEnabled}, Connected: ${wifiState.isConnected}");
  });
}
```

## API Reference

- `Battery.getBattery()`: Retrieves the current battery level, temperature, and charging status.
- `Battery.listen(void Function(BatteryState) callback)`: Listens for changes in battery status.
- `Volume.getVolume()`: Retrieves the current system volume level.
- `Volume.setVolume(int level)`: Sets the system volume level.
- `Volume.listen(void Function(VolumeState) callback)`: Listens for changes in the system volume level.
- `Wifi.getWifi()`: Retrieves the current Wi-Fi state and connection information.
- `Wifi.setWifiState(bool enable)`: Enables or disables Wi-Fi.
- `Wifi.listen(void Function(WifiState) callback)`: Listens for changes in Wi-Fi state.

## Platform Support

| Platform | Battery | Volume | Wi-Fi |
|----------|---------|--------|-------|
| Android  | ✅      | ✅     | ✅    |
| iOS      | ❌      | ❌     | ❌    |
| Web      | ❌      | ❌     | ❌    |

> **Note**: Platform checks ensure the plugin throws an exception if accessed on non-Android platforms. Future versions will aim to support more platforms, including iOS and Web.

## Future Development

`SystemState` is actively under development. Future updates will introduce:

- **Network Controller**: Plan to implement features to view and toggle:
  - **Airplane Mode**: Check and update the status of airplane mode.
  - **Mobile Data**: View and toggle mobile data connectivity.
  - **Bluetooth**: View and control Bluetooth status.

- **Cross-Platform Support**: Plans to support iOS, Web, and other platforms.

## Contributing

We welcome contributions! If you have ideas or suggestions, feel free to submit issues or pull requests.

## License

This project is licensed under the [MIT License](LICENSE).