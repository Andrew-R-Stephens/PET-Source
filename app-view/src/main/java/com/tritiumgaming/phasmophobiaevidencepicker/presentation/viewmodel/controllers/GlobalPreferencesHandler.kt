package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers

import android.util.Log
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GlobalPreferencesHandler(
    private val repository: GlobalPreferencesRepository
) {

    fun initialSetupEvent() {
        liveData {
            emit(repository.fetchInitialPreferences())
        }
    }

    suspend fun initFlow() {
        repository.flow.collect { globalPreferences ->
            _disableScreensaver.update { globalPreferences.disableScreenSaver }
            _allowCellularData.update { globalPreferences.allowCellularData }
            _allowHuntWarnAudio.update { globalPreferences.allowHuntWarnAudio }
            _enableGhostReorder.update { globalPreferences.enableGhostReorder }
            _allowIntroduction.update { globalPreferences.allowIntroduction }
            _enableRTL.update { globalPreferences.enableRTL }
            _maxHuntWarnFlashTime.update { globalPreferences.maxHuntWarnFlashTime }

            Log.d("GlobalPreferences", "Collecting from flow: \n" +
                    "\tdisableScreensaver -> ${disableScreensaver.value}\n" +
                    "\tallowCellularData -> ${allowCellularData.value}\n" +
                    "\tallowHuntWarnAudio -> ${allowHuntWarnAudio.value}\n" +
                    "\tenableGhostReorder -> ${enableGhostReorder.value}\n" +
                    "\tallowIntroduction -> ${allowIntroduction.value}\n" +
                    "\tenableRTL -> ${enableRTL.value}\n" +
                    "\tmaxHuntWarnFlashTime -> ${maxHuntWarnFlashTime.value}")
        }
    }

    // Generic settings
    private val _disableScreensaver = MutableStateFlow(false)
    val disableScreensaver = _disableScreensaver.asStateFlow()
    suspend fun setDisableScreenSaver(disable: Boolean) {
        _disableScreensaver.update { disable }
        repository.setDisableScreenSaver(disable)
    }

    private val _allowCellularData = MutableStateFlow(true)
    val allowCellularData = _allowCellularData.asStateFlow()
    suspend fun setAllowCellularData(allow: Boolean) {
        _allowCellularData.value = allow
        repository.setAllowCellularData(allow)
    }

    private val _enableRTL = MutableStateFlow(false)
    val enableRTL = _enableRTL.asStateFlow()
    suspend fun setEnableRTL(enable: Boolean) {
        _enableRTL.value = enable
        repository.setEnableRTL(enable)
    }

    private val _enableGhostReorder = MutableStateFlow(true)
    val enableGhostReorder = _enableGhostReorder.asStateFlow()
    suspend fun setEnableGhostReorder(allow: Boolean) {
        _enableGhostReorder.value = allow
        repository.setEnableGhostReorder(allow)
    }

    private val _allowIntroduction = MutableStateFlow(true)
    val allowIntroduction = _allowIntroduction.asStateFlow()
    suspend fun setAllowIntroduction(allow: Boolean) {
        _allowIntroduction.value = allow
        repository.setAllowIntroduction(allow)
    }

    // Investigation Behaviors
    private val _maxHuntWarnFlashTime = MutableStateFlow(PhaseHandler.INFINITY)
    val maxHuntWarnFlashTime: StateFlow<Long> = _maxHuntWarnFlashTime.asStateFlow()
    suspend fun setHuntWarnFlashTimeMax(maxTime: Long) {
        _maxHuntWarnFlashTime.value = maxTime
        repository.setMaxHuntWarnFlashTime(maxTime)
    }

    private val _allowHuntWarnAudio = MutableStateFlow(true)
    val allowHuntWarnAudio: StateFlow<Boolean> = _allowHuntWarnAudio.asStateFlow()
    suspend fun setAllowHuntWarnAudio(isAllowed: Boolean) {
        _allowHuntWarnAudio.value = isAllowed
        repository.setAllowHuntWarnAudio(isAllowed)
    }

}
