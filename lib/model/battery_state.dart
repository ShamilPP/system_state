/// Represents the battery state, including level, temperature, and charging status.
class BatteryState {
  /// The battery level as a percentage.
  final int level;

  /// The battery temperature in tenths of a degree Celsius.
  final int temperature;

  /// Indicates whether the device is charging.
  final bool isCharging;

  /// Constructs a [BatteryState] object with the given properties.
  BatteryState({
    required this.level,
    required this.temperature,
    required this.isCharging,
  });

  /// Creates a [BatteryState] from a map, typically received from platform code.
  ///
  /// The [map] must contain 'level', 'temperature', and 'isCharging' keys.
  factory BatteryState.fromMap(Map<String, dynamic> map) {
    return BatteryState(
      level: map['level'],
      temperature: map['temperature'],
      isCharging: map['isCharging'],
    );
  }
}
