/// Represents the volume state, including the volume level.
class VolumeState {
  /// The system volume level as a percentage.
  final int level;

  /// Constructs a [VolumeState] object with the given volume level.
  VolumeState({required this.level});

  /// Creates a [VolumeState] from a map, typically received from platform code.
  factory VolumeState.fromMap(Map<String, dynamic> map) {
    return VolumeState(level: map['level']);
  }
}
