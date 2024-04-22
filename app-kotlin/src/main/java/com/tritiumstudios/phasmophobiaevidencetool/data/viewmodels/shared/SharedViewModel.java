package com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.shared;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.ViewModel;

import com.tritiumstudios.phasmophobiaevidencetool.R;

/** @noinspection SameParameterValue*/
public abstract class SharedViewModel extends ViewModel {

    protected @StringRes int fileName;

    public abstract boolean init(@NonNull Context context);

    protected SharedPreferences getSharedPreferences(@NonNull Context context) {
        return context.getSharedPreferences(
                context.getResources().getString(fileName),
                Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor getEditor(@NonNull Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getResources().getString(fileName),
                Context.MODE_PRIVATE);
        return sharedPref.edit();
    }

    /**
     * saveToFile method
     *
     * @param context The Activity context.
     */
    public abstract void saveToFile(@NonNull Context context);

    public abstract void setFileName();

}
