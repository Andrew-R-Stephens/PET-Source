package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel

/** @noinspection SameParameterValue
 */
abstract class SharedViewModel : ViewModel() {
    @StringRes
    protected var fileName: Int = 0

    abstract fun init(context: Context): Boolean

    protected fun getSharedPreferences(context: Context): SharedPreferences {
        val fileTitle = context.resources.getString(fileName)

        return context.getSharedPreferences(
            fileTitle,
            Context.MODE_PRIVATE
        )
    }

    protected fun getEditor(context: Context): SharedPreferences.Editor {
        val sharedPreferences = getSharedPreferences(context)

        return sharedPreferences.edit()
    }

    /**
     * saveToFile method
     *
     * @param context The Activity context.
     */
    abstract fun saveToFile(context: Context)

    abstract fun setFileName()
}
