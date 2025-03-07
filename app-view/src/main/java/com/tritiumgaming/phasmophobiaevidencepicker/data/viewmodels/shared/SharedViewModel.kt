package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel

abstract class SharedViewModel(application: Application) : AndroidViewModel(application) {

    @StringRes protected var fileName: Int = 0

    //abstract fun init(context: Context): Boolean

    protected fun getSharedPreferences(context: Context): SharedPreferences {
        val fileTitle = context.resources.getString(fileName)

        return context.getSharedPreferences(fileTitle, Context.MODE_PRIVATE)
    }

    protected fun getEditor(context: Context): SharedPreferences.Editor {
        return getSharedPreferences(context).edit()
    }

    protected fun read(key: String, value: Any, sharedPref: SharedPreferences): Any? {
        return when(value) {
            is Boolean -> { sharedPref.getBoolean(key, value) }
            is Int -> { sharedPref.getInt(key, value) }
            is Long -> { sharedPref.getLong(key, value) }
            is Float -> { sharedPref.getFloat(key, value) }
            is String -> { sharedPref.getString(key, value) }
            else -> { null }
        }
    }

    protected fun save(
        key: String, value: Any, editor: SharedPreferences.Editor, localApply: Boolean = false) {
        when(value) {
            is Boolean -> { editor.putBoolean(key, value) }
            is Int -> { editor.putInt(key, value) }
            is Long -> { editor.putLong(key, value) }
            is Float -> { editor.putFloat(key, value) }
            is String -> { editor.putString(key, value) }
        }

        if (localApply) { editor.apply() }
    }

    abstract fun saveToFile(context: Context)

    abstract fun setFileName()
}
