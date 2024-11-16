package com.shamil.system_state;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.telephony.TelephonyManager;
import android.util.Log;

import io.flutter.plugin.common.EventChannel;

import java.util.HashMap;
import java.util.Map;

public class MobileDataMethod {
    private final Context context;
    private final ConnectivityManager connectivityManager;
    private final TelephonyManager telephonyManager;
    private BroadcastReceiver mobileDataStateReceiver;
    private EventChannel.EventSink eventSink;

    public MobileDataMethod(Context context) {
        this.context = context;
        this.connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        this.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    /// Gets the current mobile data state and SIM details.
    public Map<String, Object> getMobileDataState() {
        Map<String, Object> mobileData = new HashMap<>();
        mobileData.put("isMobileDataEnabled", isMobileDataEnabled());
        mobileData.put("networkOperator", getNetworkOperator());
        mobileData.put("networkType", getNetworkType());
        return mobileData;
    }

    /// Checks if mobile data is enabled.
    private boolean isMobileDataEnabled() {
        try {
            Network activeNetwork = connectivityManager.getActiveNetwork();
            if (activeNetwork != null) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(activeNetwork);
                return capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);
            }
        } catch (Exception e) {
            Log.e("MobileDataMethod", "Error checking mobile data state", e);
        }
        return false;
    }

    /// Gets the name of the sim operator (e.g., Airtel, Jio).
    private String getNetworkOperator() {
        if (telephonyManager != null) {
            String operatorName = telephonyManager.getSimOperatorName();
            return operatorName != null ? operatorName : "Unknown";
        }
        return "Unknown";
    }


    /// Gets the network type (e.g., 4G, 5G).
    private String getNetworkType() {
        if (telephonyManager != null) {
            int networkType = telephonyManager.getNetworkType();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
                case TelephonyManager.NETWORK_TYPE_NR:
                    return "5G";
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "2G";
                default:
                    return "Unknown";
            }
        }
        return "Unknown";
    }

    /// Starts listening to mobile data state changes.
    public void startListeningMobileDataState(EventChannel.EventSink events) {
        this.eventSink = events;
        if (mobileDataStateReceiver == null) {
            mobileDataStateReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Map<String, Object> mobileData = getMobileDataState();
                    if (eventSink != null) {
                        eventSink.success(mobileData);
                    }
                }
            };
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            context.registerReceiver(mobileDataStateReceiver, filter);
        }
    }

    /// Stops listening to mobile data state changes.
    public void stopListeningMobileDataState() {
        if (mobileDataStateReceiver != null) {
            context.unregisterReceiver(mobileDataStateReceiver);
            mobileDataStateReceiver = null;
        }
    }
}
