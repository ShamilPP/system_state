import 'package:flutter/material.dart';
import 'package:system_state/system_state.dart';

void main() {
  runApp(const SystemStateExampleApp());
}

class SystemStateExampleApp extends StatelessWidget {
  const SystemStateExampleApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'SystemState Example',
      theme: ThemeData(primarySwatch: Colors.blue),
      home: const SystemStateHomePage(),
    );
  }
}

class SystemStateHomePage extends StatefulWidget {
  const SystemStateHomePage({super.key});

  @override
  _SystemStateHomePageState createState() => _SystemStateHomePageState();
}

class _SystemStateHomePageState extends State<SystemStateHomePage> {
  BatteryState? _batteryState;
  VolumeState? _volumeState;
  WifiState? _wifiState;

  bool _isBatteryLoading = false;
  bool _isVolumeLoading = false;
  bool _isWifiLoading = false;

  @override
  void initState() {
    super.initState();
    _fetchAllStates();
  }

  Future<void> _fetchAllStates() async {
    _getBatteryState();
    _getVolumeState();
    _getWifiState();
  }

  Future<void> _getBatteryState() async {
    setState(() => _isBatteryLoading = true);
    try {
      final batteryState = await SystemState.battery.getBattery();
      setState(() => _batteryState = batteryState);
    } catch (e) {
      print("Error fetching battery state: $e");
    } finally {
      setState(() => _isBatteryLoading = false);
    }
  }

  Future<void> _getVolumeState() async {
    setState(() => _isVolumeLoading = true);
    try {
      final volumeState = await SystemState.volume.getVolume();
      setState(() => _volumeState = volumeState);
    } catch (e) {
      print("Error fetching volume state: $e");
    } finally {
      setState(() => _isVolumeLoading = false);
    }
  }

  Future<void> _getWifiState() async {
    setState(() => _isWifiLoading = true);
    try {
      final wifiState = await SystemState.wifi.getWifi();
      setState(() => _wifiState = wifiState);
    } catch (e) {
      print("Error fetching Wi-Fi state: $e");
    } finally {
      setState(() => _isWifiLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('SystemState Example'),
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            // Battery Section
            ListTile(
              title: const Text("Battery"),
              subtitle: _isBatteryLoading
                  ? const CircularProgressIndicator()
                  : Text(_batteryState != null
                      ? "Level: ${_batteryState!.level}%"
                      : "Battery data not available"),
              trailing: ElevatedButton(
                onPressed: _getBatteryState,
                child: const Text("Refresh"),
              ),
            ),
            const Divider(),

            // Volume Section
            ListTile(
              title: const Text("Volume"),
              subtitle: _isVolumeLoading
                  ? const CircularProgressIndicator()
                  : Text(_volumeState != null
                      ? "Current Volume: ${_volumeState!.level}"
                      : "Volume data not available"),
              trailing: ElevatedButton(
                onPressed: _getVolumeState,
                child: const Text("Refresh"),
              ),
            ),
            const Divider(),

            // Wi-Fi Section
            ListTile(
              title: const Text("Wi-Fi"),
              subtitle: _isWifiLoading
                  ? const CircularProgressIndicator()
                  : Text(_wifiState != null
                      ? _wifiState!.isEnabled
                          ? "Connected to ${_wifiState!.connectedWifiName ?? 'Unknown Network'}"
                          : "Wi-Fi is disabled"
                      : "Wi-Fi data not available"),
              trailing: ElevatedButton(
                onPressed: _getWifiState,
                child: const Text("Refresh"),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
