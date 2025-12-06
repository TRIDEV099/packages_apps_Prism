/*
 * Copyright (C) 2025 EuclidOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.prism.settings.fragments.quicksettings;

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

import com.prism.settings.utils.SystemUtils;

import java.util.List;

@SearchIndexable
public class QuickSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "QuickSettings";

    private static final String KEY_QS_COMPACT_PLAYER = "qs_compact_media_player_mode";

    private Preference mQsCompactPlayer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.quicksettings_settings);

        final Context context = requireContext();
        final ContentResolver resolver = context.getContentResolver();
        final PreferenceScreen screen = getPreferenceScreen();
        final Resources res = context.getResources();

        mQsCompactPlayer = findPreference(KEY_QS_COMPACT_PLAYER);
        if (mQsCompactPlayer != null) {
            mQsCompactPlayer.setOnPreferenceChangeListener(this);
        }

        requireActivity().setTitle(R.string.prism_qs_dashboard_title);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final Context context = requireContext();

        if (preference == mQsCompactPlayer) {
            SystemUtils.showSystemUiRestartDialog(context);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.PRISM;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider(R.xml.quicksettings_settings) {
            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                final Resources res = context.getResources();
                return keys;
            }
        };
}
