# Changelog

### Initial release
- **Battery Management**:
  - Get the current battery state (level, temperature, charging status).
  - Listen to battery state changes.
- **Volume Control**:
  - Get the current system volume.
  - Set the system volume.
  - Listen to volume state changes.
- **Platform support**: Android only (throws `UnsupportedError` for non-Android platforms).
- **Planned Future Features**:
  - Brightness control (view and update).
  - Network state (mobile data, Wi-Fi, Bluetooth, Airplane mode, etc.).
  - Cross-platform support (iOS, Web, etc.).