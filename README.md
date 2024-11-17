# SystemState Plugin

`SystemState` is a Flutter plugin designed to provide access to essential device states and controls, currently supporting Android. The plugin allows you to monitor and control **Battery**, **Volume**, **Wi-Fi**, and **Mobile Data** states. Future updates will extend functionality and support additional platforms.

## Features

- **Battery State Monitoring**: Retrieve and listen to battery level, temperature, and charging status.
- **Volume Control**: Get the current system volume, set a new volume level, and listen to volume changes.
- **Wi-Fi State Monitoring and Control:**:
  - Check if Wi-Fi is enabled or connected.
  - Retrieve the name of the connected Wi-Fi network (connectedWifiName).
  - Listen to Wi-Fi state changes.
- **Mobile Data State Monitoring and Control**:
  - Check if mobile data is enabled.
  - Retrieve the SIM operator name, network operator, and network type (e.g., 4G, 5G).
  - Listen to mobile data state changes.

> **Note**: Currently, `SystemState` is Android-only. Platform checks ensure that unsupported platforms throw exceptions. Future versions will include support for iOS, Web, and more.

## Installation

To install the plugin, add the following line to your `pubspec.yaml`:

```yaml
dependencies:
  system_state: ^1.2.6
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

- **Mobile Data State**:
  - To check network status and retrieve operator information:
    ```xml
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    ```

## Usage

### Import the Package

Import the `system_state` package into your Dart code:

```dart
import 'package:system_state/system_state.dart';
```

### Mobile Data State Monitoring

Use `SystemState.mobileData` to access mobile data-related features.

#### Get Mobile Data State

```dart
Future<void> getMobileDataState() async {
  try {
    final mobileDataState = await SystemState.mobileData.getMobileDataState();
    print("Mobile Data Enabled: ${mobileDataState.isMobileDataEnabled}");
    print("Network Operator: ${mobileDataState.networkOperator}");
    print("Network Type: ${mobileDataState.networkType}");
  } catch (e) {
    print("Error fetching mobile data state: $e");
  }
}
```

#### Listen to Mobile Data State Changes

```dart
void listenMobileDataState() {
  SystemState.mobileData.listen((mobileDataState) {
    print("Mobile Data Changed - Enabled: ${mobileDataState.isMobileDataEnabled}, Operator: ${mobileDataState.networkOperator}, Network Type: ${mobileDataState.networkType}");
  });
}
```

### Battery, Volume, and Wi-Fi Features

Refer to the respective sections in the original documentation for battery monitoring, volume control, and Wi-Fi state control.

## API Reference

- `MobileData.getMobileDataState()`: Retrieves the current mobile data state, including operator name and network type.
- `MobileData.listen(void Function(MobileDataState) callback)`: Listens for changes in mobile data state.
- `Battery`, `Volume`, and `Wi-Fi` methods remain unchanged.

## Platform Support

| Platform | Battery | Volume | Wi-Fi | Mobile Data |
|----------|---------|--------|-------|-------------|
| Android  | ✅      | ✅     | ✅    | ✅          |
| iOS      | ❌      | ❌     | ❌    | ❌          |
| Web      | ❌      | ❌     | ❌    | ❌          |

> **Note**: Platform checks ensure the plugin throws an exception if accessed on non-Android platforms. Future versions will aim to support more platforms, including iOS and Web.

## Future Development

`SystemState` is actively under development. Future updates will introduce:

- **Network Controller**: Features to manage:
  - **Airplane Mode**: Check and update the status of airplane mode.
  - **Bluetooth**: View and control Bluetooth status.

- **Cross-Platform Support**: Plans to support iOS, Web, and other platforms.

## Contributing

We welcome contributions! If you have ideas or suggestions, feel free to submit issues or pull requests.

## License

This project is licensed under the [MIT License](LICENSE).