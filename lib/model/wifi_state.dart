/// Represents the Wi-Fi state, including whether Wi-Fi is enabled, connected, and the network name.
class WifiState {
  /// Indicates if Wi-Fi is enabled.
  final bool isEnabled;

  /// Indicates if the device is connected to a Wi-Fi network.
  final bool isConnected;

  /// The name (SSID) of the connected Wi-Fi network.
  final String? connectedWifiName;

  /// Constructs a [WifiState] object with the specified Wi-Fi state information.
  WifiState({
    required this.isEnabled,
    required this.isConnected,
    this.connectedWifiName,
  });

  /// Creates a [WifiState] from a map, typically received from platform code.
  factory WifiState.fromMap(Map<String, dynamic> map) {
    return WifiState(
      isEnabled: map['isEnabled'] ?? false,
      isConnected: map['isConnected'] ?? false,
      connectedWifiName: map['connectedWifiName'],
    );
  }

  /// Converts the [WifiState] to a map, useful for debugging or sending data.
  Map<String, dynamic> toMap() {
    return {
      'isEnabled': isEnabled,
      'isConnected': isConnected,
      'connectedWifiName': connectedWifiName,
    };
  }
}
