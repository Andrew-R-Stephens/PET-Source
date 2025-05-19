package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class AThemeManager(
    protected val defaultUUID: String
) {

    protected val _currentUUID : MutableStateFlow<String> = MutableStateFlow(defaultUUID)
    val currentUUID = _currentUUID.asStateFlow()
    suspend fun setCurrentUUID(uuid: String) {
        _currentUUID.update { uuid }
        saveCurrentUUID()
        Log.d("Settings", "$uuid -> ${_currentUUID.value} == ${this.currentUUID.value}")
    }

    abstract suspend fun saveCurrentUUID()

}
