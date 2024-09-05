package com.prism.settings.fragments.themes;

import com.android.internal.logging.nano.MetricsProto;

import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import android.provider.Settings;
import com.android.settings.R;

import java.util.Locale;
import android.text.TextUtils;
import android.view.View;

import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import com.android.settings.preferences.GlobalSettingListPreference;
import com.prism.settings.utils.SystemUtils;

public class Themes extends SettingsPreferenceFragment implements Preference.OnPreferenceChangeListener {

    private static final String KEY_LOCK_SOUND = "lock_sound";
    private static final String KEY_UNLOCK_SOUND = "unlock_sound";

    private GlobalSettingListPreference mLockSound;
    private GlobalSettingListPreference mUnlockSound;

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference == mLockSound || preference == mUnlockSound) {
            SystemUtils.showSystemUiRestartDialog(context);
            return true;
        }
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.PRISM;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.themes_settings, rootKey);

        getActivity().setTitle(R.string.prism_themes_dashboard_title);

        mLockSound = (GlobalSettingListPreference) findPreference(KEY_LOCK_SOUND);
        mLockSound.setOnPreferenceChangeListener(this);
        mUnlockSound = (GlobalSettingListPreference) findPreference(KEY_UNLOCK_SOUND);
        mUnlockSound.setOnPreferenceChangeListener(this);

    }
}
