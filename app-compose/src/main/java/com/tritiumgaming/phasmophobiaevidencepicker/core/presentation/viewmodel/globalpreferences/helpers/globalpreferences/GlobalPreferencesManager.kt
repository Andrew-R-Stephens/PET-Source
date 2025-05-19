package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.globalpreferences

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.globalpreferences.source.datastore.GlobalPreferencesDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GlobalPreferencesManager(
    repository: GlobalPreferencesRepository,
    private val datastore: GlobalPreferencesDatastore
) {

    fun initialSetupEvent() {
        datastore.initialSetupEvent()
    }

    suspend fun initFlow() {
        datastore.flow.collect { globalPreferences ->
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
    var disableScreensaver = _disableScreensaver.asStateFlow()
    suspend fun setDisableScreenSaver(disable: Boolean) {
        _disableScreensaver.update { disable }
        datastore.setDisableScreenSaver(disable)
    }

    private val _allowCellularData = MutableStateFlow(true)
    var allowCellularData = _allowCellularData.asStateFlow()
    suspend fun setAllowCellularData(allow: Boolean) {
        _allowCellularData.value = allow
        datastore.setAllowCellularData(allow)
    }

    private val _enableRTL = MutableStateFlow(false)
    val enableRTL = _enableRTL.asStateFlow()
    suspend fun setEnableRTL(enable: Boolean) {
        _enableRTL.value = enable
        datastore.setEnableRTL(enable)
    }

    private val _enableGhostReorder = MutableStateFlow(true)
    val enableGhostReorder = _enableGhostReorder.asStateFlow()
    suspend fun setEnableGhostReorder(allow: Boolean) {
        _enableGhostReorder.value = allow
        datastore.setEnableGhostReorder(allow)
    }

    private val _allowIntroduction = MutableStateFlow(true)
    val allowIntroduction = _allowIntroduction.asStateFlow()
    suspend fun setAllowIntroduction(allow: Boolean) {
        _allowIntroduction.value = allow
        datastore.setAllowIntroduction(allow)
    }

    // Investigation Behaviors
    private val _maxHuntWarnFlashTime = MutableStateFlow(PhaseHandler.Time.FOREVER)
    val maxHuntWarnFlashTime: StateFlow<Long> = _maxHuntWarnFlashTime.asStateFlow()
    suspend fun setHuntWarnFlashTimeMax(maxTime: Long) {
        val time = maxTime.coerceIn(PhaseHandler.Time.MIN_TIME, PhaseHandler.Time.MAX_TIME)
        datastore.setMaxHuntWarnFlashTime(time)
        _maxHuntWarnFlashTime.update { time }
    }

    private val _allowHuntWarnAudio = MutableStateFlow(true)
    val allowHuntWarnAudio: StateFlow<Boolean> = _allowHuntWarnAudio.asStateFlow()
    suspend fun setAllowHuntWarnAudio(isAllowed: Boolean) {
        _allowHuntWarnAudio.value = isAllowed
        datastore.setAllowHuntWarnAudio(isAllowed)
    }

}