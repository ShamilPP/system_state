# Changelog

All notable changes to this project will be documented in this file.

## [1.2.5]
### Added
- **Mobile Data Controller**:
  - Functionality to check if mobile data is enabled.
  - Retrieve the name of the network operator (e.g., Airtel, Jio).
  - Retrieve the network type (e.g., 4G, 5G).
  - Listen for changes in mobile data state.

## [1.2.4]
### Added
- **Wi-Fi Controller**: Added functionality to get Wi-Fi state, toggle Wi-Fi on/off, and listen to Wi-Fi state changes.

## [1.2.3]
### Added
- **Volume Controller**: Added functionality to get current volume level, adjust volume, and listen to volume level changes.

## [1.2.2]
### Added
- **Battery Controller**: Added functionality to retrieve battery level, temperature, charging status, and listen to battery state changes.

## [1.2.1]
### Initial Release
- Initial release of `SystemState` plugin with modular controllers for managing and accessing device information.

## Future Development
### Planned Features
- **Network Controller**: Plan to implement features to view and toggle:
  - **Airplane Mode**: Check and update the status of airplane mode.
  - **Bluetooth**: View and control Bluetooth status.

- **Cross-Platform Development**: Expand functionality to support **iOS**, **Web**, and other platforms, enabling similar device state and control capabilities across devices.
