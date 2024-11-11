package com.shamil.system_state;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.provider.Settings;
import io.flutter.plugin.common.EventChannel;
import java.util.HashMap;
import java.util.Map;

/// Manages volume-related functionality and provides volume level information.
public class VolumeMethod {
    private final Context context;
    private final AudioManager audioManager;
    private ContentObserver volumeObserver;
    private EventChannel.EventSink eventSink;

    public VolumeMethod(Context context) {
        this.context = context;
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    /// Retrieves the current volume level as a map containing 'level' key.
    public Map<String, Object> getVolume() {
        Map<String, Object> volumeData = new HashMap<>();
        if (audioManager != null) {
            int volumeLevel = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            volumeData.put("level", volumeLevel);
        } else {
            volumeData.put("level", 0); // Default to 0 if AudioManager is null
        }
        return volumeData;
    }

    /// Sets the system volume to a specified level.
    public void setVolume(int volume) {
        if (audioManager != null) {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int volumeToSet = Math.min(volume, maxVolume);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeToSet, 0);
        }
    }

    /// Starts listening for volume changes.
    public void startListeningVolume(EventChannel.EventSink events) {
        this.eventSink = events;

        if (volumeObserver == null) {
            volumeObserver = new ContentObserver(new Handler()) {
                @Override
                public void onChange(boolean selfChange) {
                    super.onChange(selfChange);
                    if (eventSink != null) {
                        Map<String, Object> volumeData = getVolume();
                        eventSink.success(volumeData); // Send updated volume level to Flutter
                    }
                }
            };
            context.getContentResolver().registerContentObserver(
                    Settings.System.CONTENT_URI, true, volumeObserver);
        }
    }

    /// Stops listening for volume changes.
    public void stopListeningVolume() {
        if (volumeObserver != null) {
            context.getContentResolver().unregisterContentObserver(volumeObserver);
            volumeObserver = null;
        }
    }
}
