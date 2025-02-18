package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class AThemeHandler(
    protected val defaultUUID: String
) {

    protected val _currentUUID : MutableStateFlow<String> = MutableStateFlow(defaultUUID)
    val currentUUID = _currentUUID.asStateFlow()
    suspend fun setCurrentUUID(uuid: String) {
        /*_currentUUID.update { uuid }.also { saveCurrentUUID() }*/
        _currentUUID.value = uuid
        saveCurrentUUID()
        Log.d("Settings", "$uuid -> ${_currentUUID.value} == ${this.currentUUID.value}")
    }

    abstract fun findNextAvailable(
        direction: IncrementDirection,
        uuid: String? = currentUUID.value
    ): String

    abstract suspend fun saveCurrentUUID()

    enum class IncrementDirection(val value: Int) {
        FORWARD(1),
        BACKWARD(-1)
    }
}
