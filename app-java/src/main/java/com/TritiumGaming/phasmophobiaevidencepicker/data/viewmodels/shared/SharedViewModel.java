package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.ReviewTrackingData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeControl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/** @noinspection SameParameterValue*/
public abstract class SharedViewModel extends ViewModel {

    protected @StringRes int fileName;

    public abstract boolean init(@NonNull Context context);

    protected SharedPreferences getSharedPreferences(@NonNull Context context) {
        String fileTitle = context.getResources().getString(fileName);

        return context.getSharedPreferences(
                fileTitle,
                Context.MODE_PRIVATE);
    }

    protected @Nullable SharedPreferences.Editor getEditor(@NonNull Context context)
            throws NullPointerException
    {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        if(sharedPreferences == null) {
            throw new NullPointerException("SharedPreferences object was null.");
        }

        return sharedPreferences.edit();
    }

    /**
     * saveToFile method
     *
     * @param context The Activity context.
     */
    public abstract void saveToFile(@NonNull Context context);

    public abstract void setFileName();

}
