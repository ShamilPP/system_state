/// Represents the state of mobile data and SIM information.
class MobileDataState {
  /// Indicates if mobile data is enabled.
  final bool isMobileDataEnabled;

  /// The name of the network operator (e.g., Airtel, Jio).
  final String? networkOperator;

  /// The network type (e.g., 4G, 5G).
  final String? networkType;

  /// Constructs a [MobileDataState] object.
  MobileDataState({
    required this.isMobileDataEnabled,
    this.networkOperator,
    this.networkType,
  });

  /// Creates a [MobileDataState] from a map, typically received from platform code.
  factory MobileDataState.fromMap(Map<String, dynamic> map) {
    return MobileDataState(
      isMobileDataEnabled: map['isMobileDataEnabled'] ?? false,
      networkOperator: map['networkOperator'],
      networkType: map['networkType'],
    );
  }
}
