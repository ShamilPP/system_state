package com.shamil.system_state;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import io.flutter.plugin.common.EventChannel;
import java.util.HashMap;
import java.util.Map;

public class WifiMethod {
    private final Context context;
    private final WifiManager wifiManager;
    private final ConnectivityManager connectivityManager;
    private BroadcastReceiver wifiStateReceiver;
    private EventChannel.EventSink eventSink;

    public WifiMethod(Context context) {
        this.context = context;
        this.wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /// Gets the current Wi-Fi state.
    public Map<String, Object> getWifiState() {
        Map<String, Object> wifiData = new HashMap<>();
        if (wifiManager != null) {
            wifiData.put("isEnabled", wifiManager.isWifiEnabled());
            wifiData.put("isConnected", isWifiConnected());
            wifiData.put("connectedWifiName", getConnectedWifiName());
        } else {
            wifiData.put("isEnabled", false);
            wifiData.put("isConnected", false);
            wifiData.put("connectedWifiName", null);
        }
        return wifiData;
    }

    /// Checks if the device is connected to Wi-Fi.
    private boolean isWifiConnected() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Network network = connectivityManager.getActiveNetwork();
            if (network != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                return capabilities != null &&
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI);
            }
        } else {
            // Deprecated method for Android 9 and below
            return wifiManager.isWifiEnabled() && wifiManager.getConnectionInfo().getNetworkId() != -1;
        }
        return false;
    }

    /// Gets the name (SSID) of the connected Wi-Fi network.
    /**
     * Returns the SSID of the connected Wi-Fi network.
     * Requires ACCESS_FINE_LOCATION permission on Android 10 and above, and location services to be enabled.
     *
     * @return SSID of the connected Wi-Fi network, or null if not connected or access is restricted.
     */
    public String getConnectedWifiName() {
        // Retrieve SSID if connected to Wi-Fi
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                String ssid = wifiInfo.getSSID();
                // Handle cases where the SSID might be returned with double quotes
                if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
                return ssid;
            }
        }
        return null;
    }

    /**
     * Sets the Wi-Fi state for devices running Android 9 (Pie) and below.
     * For Android 10 and above, opens the Wi-Fi settings for the user to enable/disable Wi-Fi manually.
     *
     * @param enabled true to enable Wi-Fi, false to disable
     * @return true if the operation succeeded or the settings intent was opened; false if the Wi-Fi manager is null.
     */
    public boolean setWifiState(boolean enabled) {
        if (wifiManager == null) {
            return false;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // For Android 9 and below, use setWifiEnabled directly
            return wifiManager.setWifiEnabled(enabled);
        } else {
            // For Android 10 and above, open Wi-Fi settings for user to enable/disable
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true; // Indicate that settings intent was successfully opened
        }
    }

    /// Starts listening to Wi-Fi state changes.
    public void startListeningWifiState(EventChannel.EventSink events) {
        this.eventSink = events;
        if (wifiStateReceiver == null) {
            wifiStateReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Map<String, Object> wifiData = getWifiState();
                    if (eventSink != null) {
                        eventSink.success(wifiData); // Send Wi-Fi state data to Flutter
                    }
                }
            };
            // Register for both Wi-Fi state and network state changes
            IntentFilter filter = new IntentFilter();
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION); // Detect Wi-Fi enabled/disabled
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION); // Detect when the device connects or disconnects from a network (e.g., Wi-Fi network).
            context.registerReceiver(wifiStateReceiver, filter);
        }
    }

    /// Stops listening to Wi-Fi state changes.
    public void stopListeningWifiState() {
        if (wifiStateReceiver != null) {
            context.unregisterReceiver(wifiStateReceiver);
            wifiStateReceiver = null;
        }
    }
}
