package com.isaac.ggmanager.core.utils;

import android.content.SharedPreferences;

public class UserPreferencesUtils {
    private static final String PREF_NAME = "user_prefs";
    private static final String KEY_USER_ID = "user_id";

    private final SharedPreferences sharedPreferences;

    public UserPreferencesUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveUserId(String userId) {
        sharedPreferences.edit().putString(KEY_USER_ID, userId).apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(KEY_USER_ID, null);
    }

    public void clear() {
        sharedPreferences.edit().clear().apply();
    }
}
