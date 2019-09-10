/*
 * Copyright (C) 2017 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.lineageos.recorder.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.lineageos.recorder.R;
import androidx.annotation.NonNull;

public class OnBoardingHelper {
    private static final String ONBOARD_SETTINGS = "ONBOARD_SETTINGS";
    private static final long ROTATION_DELAY = 500;
    private static final int ROTATION_OPEN_APP_WAIT = 2;

    public static void onBoardScreenSettings(Context context, View view) {
        SharedPreferences prefs = getPrefs(context);
        int appOpenTimes = prefs.getInt(ONBOARD_SETTINGS, 0);

        // Wait for the user to open the app 3 times before exposing this
        if (appOpenTimes <= ROTATION_OPEN_APP_WAIT) {
            prefs.edit().putInt(ONBOARD_SETTINGS, appOpenTimes + 1).apply();
        }

        if (appOpenTimes == ROTATION_OPEN_APP_WAIT) {
            new Handler().postDelayed(rotationAnimation(context, view), ROTATION_DELAY);
        }
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PreferenceUtils.PREFS, 0);
    }

    @NonNull
    private static Runnable rotationAnimation(Context context, View view) {
        return () -> {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.rotation);
            view.startAnimation(animation);
        };
    }
}