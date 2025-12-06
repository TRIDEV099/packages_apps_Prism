/*
 * Copyright (C) 2025 EuclidOS
 * SPDX-License-Identifier: Apache-2.0
 */

package com.prism.settings.fragments.themes;

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

import com.android.settings.preferences.GlobalSettingListPreference;
import com.prism.settings.utils.SystemUtils;

import java.util.List;

@SearchIndexable
public class Themes extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    private static final String TAG = "Themes";

    private static final String KEY_LOCK_SOUND = "lock_sound";
    private static final String KEY_UNLOCK_SOUND = "unlock_sound";

    private GlobalSettingListPreference mLockSound;
    private GlobalSettingListPreference mUnlockSound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.themes_settings);

        final Context context = requireContext();
        final ContentResolver resolver = context.getContentResolver();
        final PreferenceScreen screen = getPreferenceScreen();
        final Resources res = context.getResources();

        requireActivity().setTitle(R.string.prism_themes_dashboard_title);

        mLockSound = findPreference(KEY_LOCK_SOUND);
        if (mLockSound != null) {
            mLockSound.setOnPreferenceChangeListener(this);
        }

        mUnlockSound = findPreference(KEY_UNLOCK_SOUND);
        if (mUnlockSound != null) {
            mUnlockSound.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final Context context = requireContext();

        if (preference == mLockSound || preference == mUnlockSound) {
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
        new BaseSearchIndexProvider(R.xml.themes_settings) {
            @Override
            public List<String> getNonIndexableKeys(Context context) {
                List<String> keys = super.getNonIndexableKeys(context);
                final Resources res = context.getResources();
                return keys;
            }
        };
}
