/*
 * Copyright (C) 2025 EuclidOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.prism.settings.fragments.lockscreen;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;
import com.android.internal.util.euclid.OmniJawsClient;

import java.util.List;

@SearchIndexable
public class Lockscreen extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "Lockscreen";
    private static final String KEY_FINGERPRINT_CATEGORY = "lock_screen_fingerprint_category";
    private static final String KEY_AUTHENTICATION_SUCCESS = "fp_success_vibrate";
    private static final String KEY_AUTHENTICATION_ERROR = "fp_error_vibrate";
    private static final String KEY_WEATHER = "lockscreen_weather_enabled";

    private PreferenceCategory mFingerprintCategory;
    private Preference mWeather;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.lockscreen_settings);

        final Context context = requireContext();
        final ContentResolver resolver = context.getContentResolver();
        final PreferenceScreen prefScreen = getPreferenceScreen();
        final Resources res = context.getResources();

        mFingerprintCategory = (PreferenceCategory) findPreference(KEY_FINGERPRINT_CATEGORY);

        FingerprintManager fingerprintManager = (FingerprintManager)
                getActivity().getSystemService(Context.FINGERPRINT_SERVICE);
        if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
            prefScreen.removePreference(mFingerprintCategory);
        }

        mWeather = findPreference(KEY_WEATHER);
        updateWeatherSettings();

        requireActivity().setTitle(R.string.prism_lockscreen_dashboard_title);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final Context context = requireContext();
        final ContentResolver resolver = context.getContentResolver();
        return false;
    }

    private void updateWeatherSettings() {
        if (mWeather == null) return;

        boolean weatherEnabled = OmniJawsClient.get().isOmniJawsEnabled(getContext());
        mWeather.setEnabled(weatherEnabled);
        mWeather.setSummary(weatherEnabled
                ? R.string.lockscreen_weather_summary
                : R.string.lockscreen_weather_enabled_info);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateWeatherSettings();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.PRISM;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider(R.xml.lockscreen_settings) {
            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                final Resources res = context.getResources();

                FingerprintManager fingerprintManager = (FingerprintManager)
                    context.getSystemService(Context.FINGERPRINT_SERVICE);

                if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
                    keys.add(KEY_AUTHENTICATION_SUCCESS);
                    keys.add(KEY_AUTHENTICATION_ERROR);
                }
                return keys;
            }
        };
}
