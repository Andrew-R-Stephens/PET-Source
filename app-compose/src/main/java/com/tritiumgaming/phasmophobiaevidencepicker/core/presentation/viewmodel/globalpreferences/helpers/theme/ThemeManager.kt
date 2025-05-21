package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme

interface ThemeManager {

    val defaultUUID: String

    suspend fun setCurrentUUID(uuid: String)
    suspend fun saveCurrentUUID()

}