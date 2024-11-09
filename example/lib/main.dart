import 'package:flutter/material.dart';
import 'package:system_state/system_state.dart';
import 'package:system_state/model/battery_state.dart';
import 'package:system_state/model/volume_state.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'System State Example',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const SystemStatePage(),
    );
  }
}

class SystemStatePage extends StatefulWidget {
  const SystemStatePage({Key? key}) : super(key: key);

  @override
  _SystemStatePageState createState() => _SystemStatePageState();
}

class _SystemStatePageState extends State<SystemStatePage> {
  BatteryState? _batteryState;
  VolumeState? _volumeState;
  bool _isLoading = true; // To show the loading indicator
  bool _isListening = false; // To check if listening has started

  @override
  void initState() {
    super.initState();
    _initBatteryState();
    _initVolumeState();
  }

  // Initialize Battery State
  void _initBatteryState() async {
    try {
      final batteryState = await SystemState.battery.getBattery();
      setState(() {
        _batteryState = batteryState;
        _isLoading = false; // Stop loading once data is fetched
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      print("Error fetching battery state: $e");
    }
  }

  // Initialize Volume State
  void _initVolumeState() async {
    try {
      final volumeState = await SystemState.volume.getVolume();
      setState(() {
        _volumeState = volumeState;
        _isLoading = false; // Stop loading once data is fetched
      });
    } catch (e) {
      setState(() {
        _isLoading = false;
      });
      print("Error fetching volume state: $e");
    }
  }

  // Start listening to battery and volume state changes
  void _startListening() {
    setState(() {
      _isListening = true;
    });

    SystemState.battery.listen((batteryState) {
      setState(() {
        _batteryState = batteryState;
      });
    });

    SystemState.volume.listen((volumeState) {
      setState(() {
        _volumeState = volumeState;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('System State Example'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Text(
              'Battery State:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            if (_isLoading)
              const Center(child: CircularProgressIndicator())
            else if (_batteryState != null)
              Text('Level: ${_batteryState?.level ?? 'N/A'}%'),
            if (_batteryState != null)
              Text('Temperature: ${_batteryState?.temperature ?? 'N/A'}°C'),
            if (_batteryState != null)
              Text('Charging: ${_batteryState?.isCharging ?? 'N/A'}'),
            const SizedBox(height: 16),
            const Text(
              'Volume State:',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            if (_isLoading)
              const Center(child: CircularProgressIndicator())
            else if (_volumeState != null)
              Text('Volume Level: ${_volumeState?.level ?? 'N/A'}'),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _startListening,
              child: Text(_isListening ? 'Listening...' : 'START Listening'),
            ),
          ],
        ),
      ),
    );
  }
}
