package com.example.findcare.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "FindCareSession";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String KEY_IS_ONBOARDING_COMPLETED = "isOnboardingCompleted";

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void createLoginSession(int userId, String name) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putInt(KEY_USER_ID, userId);
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public int getUserId() {
        return pref.getInt(KEY_USER_ID, -1);
    }

    public String getUserName() {
        return pref.getString(KEY_USER_NAME, "User");
    }

    public void logoutUser() {
        editor.clear();
        editor.apply();
    }

    public void setOnboardingCompleted(boolean isCompleted) {
        editor.putBoolean(KEY_IS_ONBOARDING_COMPLETED, isCompleted);
        editor.apply();
    }

    public boolean isOnboardingCompleted() {
        return pref.getBoolean(KEY_IS_ONBOARDING_COMPLETED, false);
    }

    public void logout() {
        logoutUser();
    }
}
