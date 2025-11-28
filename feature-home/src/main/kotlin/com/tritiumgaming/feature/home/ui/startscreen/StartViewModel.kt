package com.tritiumgaming.feature.home.ui.startscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.shared.data.preferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.preferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.data.review.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.shared.data.review.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.shared.data.review.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.SetAppTimesOpenedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class StartViewModel(
    // Global Preferences
    private val initGlobalPreferencesDataStoreUseCase: SetupGlobalPreferencesUseCase,
    private val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
    private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    // Review Tracker
    private val initReviewTrackerDataStoreUseCase: SetupReviewTrackerUseCase,
    private val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
    private val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
    private val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase,
    private val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase,
    private val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
    private val getAppTimeAliveUseCase: GetAppTimeAliveUseCase,
    private val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase,
    private val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
    private val getAppTimesOpenedUseCase: GetAppTimesOpenedUseCase,
    private val loadAppTimesOpenedUseCase: LoadAppTimesOpenedUseCase
) : ViewModel() {

    private val _introductionPermissionPreference = MutableStateFlow(true)
    val introductionPermissionPreference = _introductionPermissionPreference.asStateFlow()
    fun setIntroductionPermissionPreference(allow: Boolean) {
        _introductionPermissionPreference.update { allow }
        viewModelScope.launch {
            setAllowIntroductionUseCase(allow)
        }
    }

    /**
     * ReviewTracker
     */
    private val _wasReviewRequested = MutableStateFlow(false)
    val wasReviewRequested = _wasReviewRequested.asStateFlow()
    fun setReviewRequestStatus(status: Boolean) {
        viewModelScope.launch {
            setReviewRequestStatusUseCase(status)
        }
        _wasReviewRequested.value = status
    }
    fun loadReviewRequestStatus() {
        viewModelScope.launch {
            loadReviewRequestStatusUseCase()
        }
    }

    private val _appTimeActive = MutableStateFlow(0L)
    val appTimeActive = _appTimeActive.asStateFlow()
    fun setAppTimeActive(time: Long) {
        viewModelScope.launch {
            setAppTimeAliveUseCase(time)
        }
        _appTimeActive.value = time
    }
    fun loadAppTimeActive() {
        viewModelScope.launch {
            loadAppTimeAliveUseCase()
        }
    }
    fun getAppTimeActive() {
        _appTimeActive.update { getAppTimeAliveUseCase() }
    }

    private val _timesOpened = MutableStateFlow(0)
    val timesOpened = _timesOpened.asStateFlow()
    fun incrementAppTimesOpened() {
        viewModelScope.launch {
            _timesOpened.update { it + 1 }
            setAppTimesOpenedUseCase(timesOpened.value)
        }
    }
    fun setTimesOpened(count: Int) {
        _timesOpened.update { count }
    }
    fun loadAppTimesOpened() {
        viewModelScope.launch {
            loadAppTimesOpenedUseCase
        }
    }
    fun getAppTimesOpened() {
        _timesOpened.update { getAppTimesOpenedUseCase() }
    }

    fun canRequestReview(): Boolean {
        return (!wasReviewRequested.value) && (timesOpened.value >= MAX_TIMES_OPENED_TARGET)
    }

    fun canShowReviewButton(): Boolean {
        return (wasReviewRequested.value) && (timesOpened.value >= MAX_TIMES_OPENED_TARGET)
    }

    private fun initialDataStoreSetupEvent() {
        initGlobalPreferencesDataStoreUseCase()
        initReviewTrackerDataStoreUseCase()
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialDataStoreSetupEvent()

        // Review Tracker
        viewModelScope.launch {
            initFlowReviewTrackerUseCase { preferences ->
                _wasReviewRequested.update { preferences.allowRequestReview }
                _appTimeActive.update { preferences.timeActive }
                _timesOpened.update { preferences.timesOpened }
            }
        }

        // Global Preferences
        viewModelScope.launch {
            initFlowGlobalPreferencesUseCase { preferences ->
                _introductionPermissionPreference.update { preferences.allowIntroduction }
            }
        }

    }

    companion object {
        internal const val MAX_TIMES_OPENED_TARGET: Int = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                // Global Preferences
                val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase = container.setupGlobalPreferencesUseCase
                val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val setAllowIntroductionUseCase: SetAllowIntroductionUseCase = container.setAllowIntroductionUseCase
                // Reviews
                val initReviewTrackerDataStoreUseCase: SetupReviewTrackerUseCase = container.initReviewTrackerDataStoreUseCase
                val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase = container.initFlowReviewTrackerUseCase
                val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase = container.setReviewRequestStatusUseCase
                val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase = container.getReviewRequestStatusUseCase
                val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase = container.loadReviewRequestStatusUseCase
                val setAppTimeAliveUseCase: SetAppTimeAliveUseCase = container.setAppTimeAliveUseCase
                val getAppTimeAliveUseCase: GetAppTimeAliveUseCase = container.getAppTimeAliveUseCase
                val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase = container.loadAppTimeAliveUseCase
                val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase = container.setAppTimesOpenedUseCase
                val getAppTimesOpenedUseCase: GetAppTimesOpenedUseCase = container.getAppTimesOpenedUseCase
                val loadAppTimesOpenedUseCase: LoadAppTimesOpenedUseCase = container.loadAppTimesOpenedUseCase

                StartViewModel(
                    // Global Preferences
                    initGlobalPreferencesDataStoreUseCase = setupGlobalPreferencesUseCase,
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    setAllowIntroductionUseCase = setAllowIntroductionUseCase,
                    // Reviews
                    initReviewTrackerDataStoreUseCase = initReviewTrackerDataStoreUseCase,
                    initFlowReviewTrackerUseCase = initFlowReviewTrackerUseCase,
                    setReviewRequestStatusUseCase = setReviewRequestStatusUseCase,
                    getReviewRequestStatusUseCase = getReviewRequestStatusUseCase,
                    loadReviewRequestStatusUseCase = loadReviewRequestStatusUseCase,
                    setAppTimeAliveUseCase = setAppTimeAliveUseCase,
                    getAppTimeAliveUseCase = getAppTimeAliveUseCase,
                    loadAppTimeAliveUseCase = loadAppTimeAliveUseCase,
                    setAppTimesOpenedUseCase = setAppTimesOpenedUseCase,
                    getAppTimesOpenedUseCase = getAppTimesOpenedUseCase,
                    loadAppTimesOpenedUseCase = loadAppTimesOpenedUseCase,
                )
            }
        }

        const val MIN_TIME = 0L
        const val MAX_TIME = 300000L

        const val FOREVER = 300000L

        private var DEFAULT_LANGUAGE = Locale.ENGLISH.language

    }

}
