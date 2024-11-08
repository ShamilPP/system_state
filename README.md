
# SystemState Plugin

The `system_state` plugin for Flutter provides an easy way to access and monitor device battery state and information. Future updates will introduce volume and brightness control functionalities.

## Features

- **Battery State**: View battery level, temperature, and charging status.
- **Battery Listener**: Listen for real-time battery state changes.

### Coming Soon

- Volume Control (Set, Get, and Adjust)
- Brightness Control (Set, Get, and Adjust)

## Installation

Add `system_state` to your `pubspec.yaml` file:

```yaml
dependencies:
  system_state: ^0.0.1
```

Then run:

```sh
flutter pub get
```

## Usage

Import `system_state` into your Dart file:

```dart
import 'package:system_state/system_state.dart';
```

### Battery State Example

1. **Retrieve Current Battery State**

   To get the current battery state, you can call the `getBatteryState` method:

   ```dart
   Future<void> fetchBatteryState() async {
     try {
       BatteryState batteryState = await DeviceStats.battery.getBatteryState();
       print('Battery Level: ${batteryState.level}%');
       print('Battery Temperature: ${batteryState.temperature / 10.0} °C');
       print('Is Charging: ${batteryState.isCharging}');
     } catch (e) {
       print('Failed to get battery state: $e');
     }
   }
   ```

2. **Listen for Battery State Changes**

   You can also listen to real-time updates of the battery state. This will provide updates whenever there is a change in the battery’s status:

   ```dart
   void listenToBatteryState() {
     DeviceStats.battery.listen((BatteryState batteryState) {
       print('Battery Level: ${batteryState.level}%');
       print('Battery Temperature: ${batteryState.temperature / 10.0} °C');
       print('Is Charging: ${batteryState.isCharging}');
     });
   }
   ```

### Battery State Model

The `BatteryState` model provides the following information:

- **level**: Battery level as a percentage.
- **temperature**: Battery temperature in tenths of a degree Celsius.
- **isCharging**: Boolean indicating whether the device is charging.

### Example App

Here’s an example of how you could use `system_state` in an app:

```dart
import 'package:flutter/material.dart';
import 'package:system_state/system_state.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: BatteryInfoPage(),
    );
  }
}

class BatteryInfoPage extends StatefulWidget {
  @override
  _BatteryInfoPageState createState() => _BatteryInfoPageState();
}

class _BatteryInfoPageState extends State<BatteryInfoPage> {
  BatteryState? _batteryState;

  @override
  void initState() {
    super.initState();
    fetchBatteryState();
    listenToBatteryState();
  }

  Future<void> fetchBatteryState() async {
    try {
      _batteryState = await DeviceStats.battery.getBatteryState();
      setState(() {});
    } catch (e) {
      print('Failed to get battery state: $e');
    }
  }

  void listenToBatteryState() {
    DeviceStats.battery.listen((BatteryState batteryState) {
      setState(() {
        _batteryState = batteryState;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Battery Info')),
      body: Center(
        child: _batteryState == null
            ? CircularProgressIndicator()
            : Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text('Battery Level: ${_batteryState!.level}%'),
                  Text('Battery Temperature: ${_batteryState!.temperature / 10.0} °C'),
                  Text('Is Charging: ${_batteryState!.isCharging}'),
                ],
              ),
      ),
    );
  }
}
```

## Future Development

The following features are planned for future releases:

- **Volume Control**: Set, get, and adjust volume.
- **Brightness Control**: Set, get, and adjust screen brightness.

Stay tuned for updates!

## Contributing

Feel free to contribute to this plugin. Fork the repository and submit pull requests if you would like to add features or fix bugs.

## License

This plugin is released under the MIT License.
