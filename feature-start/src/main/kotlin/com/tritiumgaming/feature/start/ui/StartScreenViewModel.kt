package com.tritiumgaming.feature.start.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.start.app.container.StartContainerProvider
import com.tritiumgaming.feature.start.ui.introduction.IntroductionUiState
import com.tritiumgaming.feature.start.ui.newsletter.NewsletterInboxUiState
import com.tritiumgaming.feature.start.ui.newsletter.NewsletterInboxesUiState
import com.tritiumgaming.feature.start.ui.reviewpopup.ReviewUiState
import com.tritiumgaming.shared.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.review.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.shared.data.review.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.shared.data.review.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.IncrementAppTimesOpenedByUseCase
import com.tritiumgaming.shared.data.review.usecase.timesopened.SetAppTimesOpenedUseCase
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class StartScreenViewModel(
    // Global Preferences
    private val initFlowUserPreferencesUseCase: InitFlowUserPreferencesUseCase,
    private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    // Review Tracker
    private val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
    private val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
    private val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
    private val incrementAppTimesOpenedUseCase: IncrementAppTimesOpenedByUseCase,
    private val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
    // Newsletter
    private val getFlowNewsletterDatastoreUseCase: GetFlowNewsletterDatastoreUseCase,
    private val getFlowNewsletterInboxesUseCase: GetFlowNewsletterInboxesUseCase,
    private val fetchNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase
) : ViewModel() {

    // User Preferences
    private val _preferencesFlow = initFlowUserPreferencesUseCase()
        .map {
            IntroductionUiState(
                allowIntroduction = it.allowIntroduction
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = IntroductionUiState()
        )
    val preferencesFlow = _preferencesFlow

    // Review Tracker
    private val _reviewFlow = initFlowReviewTrackerUseCase()
        .map { stateFlow ->
            val state = ReviewUiState(
                timesOpened = stateFlow.timesOpened,
                canRequestReview = (!stateFlow.reviewRequested) &&
                        (stateFlow.timesOpened >= MAX_TIMES_OPENED_TARGET)
            )

            state
        }
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = ReviewUiState()
        )
    val reviewFlow = _reviewFlow

    // Newsletter
    private val _inboxesUiState = getFlowNewsletterInboxesUseCase()
        .combine(getFlowNewsletterDatastoreUseCase()) { inboxes, datastore ->
            NewsletterInboxesUiState(
                inboxes = inboxes.map { inbox ->
                    NewsletterInboxUiState(
                        inbox = inbox,
                        lastReadDate = datastore.data.entries.firstOrNull {
                            it.key == inbox.id
                        }?.value ?: 0L
                    )
                }
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = NewsletterInboxesUiState(
                inboxes = emptyList()
            )
        )
    val inboxesUiState = _inboxesUiState

    fun loadInboxes(
        onStart: () -> Unit = {},
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    ) {

        viewModelScope.launch {
            onStart()

            try {
                val fetchedInboxes = fetchNewsletterInboxesUseCase {
                    Log.d("StartViewModel",
                        "Failed to refresh inboxes because it was too soon.")
                }.getOrThrow()

                fetchedInboxes.forEach { inbox ->
                    Log.d("StartViewModel",
                        "Fetched inbox: ${inbox.title} " +
                                "${inbox.channel?.messages?.map { "${ it.title }\n" }}")
                }

                onSuccess()
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }

        }
    }

    /*
     * Introduction Tracker
     */
    fun setIntroductionAllowed(allow: Boolean) {
        viewModelScope.launch {
            setAllowIntroductionUseCase(allow)
        }
    }

    /*
     * Review Tracker
     */
    fun setReviewRequestStatus(status: Boolean) {
        viewModelScope.launch {
            setReviewRequestStatusUseCase(status)
        }
    }

    fun setAppTimeActive(time: Long) {
        viewModelScope.launch {
            setAppTimeAliveUseCase(time)
        }
    }

    /*private fun initialDataStoreSetupEvent() {
        setupNewsletterDatastoreUseCase()
        setupUserPreferencesDataStoreUseCase()
        setupReviewDataStoreUseCase()
    }*/

    init {
        Log.d("StartViewModel", "Initializing...")

        viewModelScope.launch {
            //initialDataStoreSetupEvent()

                Log.d("StartViewModel",
                    "Incrementing times opened from: ${reviewFlow.value.timesOpened}")

                incrementAppTimesOpenedUseCase(
                    count = reviewFlow.value.timesOpened,
                    incrementBy = 1
                )

                Log.d("StartViewModel", "Finish incrementing app times opened")

        }

        loadInboxes()

    }

    companion object {
        internal const val MAX_TIMES_OPENED_TARGET: Int = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as StartContainerProvider).provideStartContainer()

                // Global Preferences
                val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val setAllowIntroductionUseCase: SetAllowIntroductionUseCase = container.setAllowIntroductionUseCase
                // Reviews
                val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase = container.initFlowReviewTrackerUseCase
                val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase = container.setReviewRequestStatusUseCase
                val setAppTimeAliveUseCase: SetAppTimeAliveUseCase = container.setAppTimeAliveUseCase
                val incrementAppTimesOpenedUseCase: IncrementAppTimesOpenedByUseCase = container.incrementAppTimesOpenedUseCase
                val saveAppTimesOpenedUseCase: SetAppTimesOpenedUseCase = container.setAppTimesOpenedUseCase
                // Newsletter
                val getFlowNewsletterDatastoreUseCase = container.getFlowNewsletterDatastoreUseCase
                val getFlowNewsletterInboxesUseCase = container.getFlowNewsletterInboxesUseCase
                val fetchNewsletterInboxesUseCase = container.getNewsletterInboxesUseCase

                StartScreenViewModel(
                    // Global Preferences
                    initFlowUserPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    setAllowIntroductionUseCase = setAllowIntroductionUseCase,
                    // Reviews
                    initFlowReviewTrackerUseCase = initFlowReviewTrackerUseCase,
                    setReviewRequestStatusUseCase = setReviewRequestStatusUseCase,
                    setAppTimeAliveUseCase = setAppTimeAliveUseCase,
                    incrementAppTimesOpenedUseCase = incrementAppTimesOpenedUseCase,
                    setAppTimesOpenedUseCase = saveAppTimesOpenedUseCase,
                    // Newsletter
                    getFlowNewsletterDatastoreUseCase = getFlowNewsletterDatastoreUseCase,
                    getFlowNewsletterInboxesUseCase = getFlowNewsletterInboxesUseCase,
                    fetchNewsletterInboxesUseCase = fetchNewsletterInboxesUseCase
                )
            }
        }

    }

}
