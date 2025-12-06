/*
 * Copyright (C) 2025 EuclidOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.prism.settings.fragments.lockscreen;

import android.content.Context;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

import java.util.List;

@SearchIndexable
public class Lockscreen extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "Lockscreen";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.lockscreen_settings);

        final Context context = requireContext();
        final ContentResolver resolver = context.getContentResolver();
        final PreferenceScreen screen = getPreferenceScreen();
        final Resources res = context.getResources();

        requireActivity().setTitle(R.string.prism_lockscreen_dashboard_title);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final Context context = requireContext();
        final ContentResolver resolver = context.getContentResolver();
        return false;
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
                return keys;
            }
        };
}
