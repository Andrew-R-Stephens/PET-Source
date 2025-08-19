package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.newsletter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.InitFlowNewsletterUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.SetupNewsletterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

class NewsletterViewModel(
    private val setupNewsletterDatastoreUseCase: SetupNewsletterUseCase,
    private val initFlowNewsletterUseCase: InitFlowNewsletterUseCase,
    private val fetchNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    private val saveNewsletterInboxLastReadDateUseCase: SaveNewsletterInboxLastReadDateUseCase,
): ViewModel() {

    private val _inboxesUiState = MutableStateFlow<NewsletterInboxesUiState>(
        NewsletterInboxesUiState())
    val inboxesUiState = _inboxesUiState.asStateFlow()

    private val _refreshUiState = MutableStateFlow<NewsletterRefreshingUiState>(
        NewsletterRefreshingUiState())
    val refreshUiState = _refreshUiState.asStateFlow()

    fun saveInboxLastReadDate(inboxId: String, date: Long) {
        viewModelScope.launch {

            try {
                _inboxesUiState.update { inboxesUi ->

                    val updatedInboxes = inboxesUi.inboxes.map { inbox ->
                        if(inbox.inbox.id == inboxId) {
                            if(date <= inbox.lastReadDate) {
                                Log.d(
                                    "NewsletterViewModel",
                                    "Not updating inbox: $inboxId | $date is older than ${inbox.lastReadDate}"
                                )
                                inbox
                            } else {
                                Log.d(
                                    "NewsletterViewModel",
                                    "Updating inbox: $inboxId | From ${inbox.lastReadDate} to $date"
                                )
                                saveNewsletterInboxLastReadDateUseCase(inboxId, date)

                                inbox.copy(
                                    lastReadDate = date
                                )
                            }
                        } else {
                            Log.d("NewsletterViewModel", "No inbox found: $inboxId")
                            inbox
                        }
                    }

                    inboxesUi.copy(
                        inboxes = updatedInboxes
                    )
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.d("NewsletterViewModel", "Updated inbox dates: ${inboxesUiState.value.inboxes.map { it.lastReadDate }}")

        }
    }

    fun loadInboxes(
        onStart: () -> Unit = {},
        onComplete: () -> Unit = {},
    ) {

        viewModelScope.launch {

            onStart()

            try {
                val fetchedInboxes = fetchNewsletterInboxesUseCase().getOrThrow()

                _inboxesUiState.update {
                    it.copy(
                        inboxes = fetchedInboxes.map { inbox ->
                            NewsletterInboxUiState(
                                inbox = inbox,
                                lastReadDate = 0L
                            )
                        }
                    )
                }

                initFlowNewsletterUseCase { preferences ->
                    Log.d("NewsletterViewModel", "Datastore flow triggered")

                    _inboxesUiState.update { inboxes ->
                        val updatedInboxes = inboxes.inboxes.map { inbox ->
                            inbox.copy(
                                lastReadDate = (preferences.data[inbox.inbox.id] ?: inbox.lastReadDate)
                            )
                        }

                        inboxes.copy(
                            inboxes = updatedInboxes
                        )
                    }

                    onComplete()

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            onComplete()

        }
    }

    fun refreshInboxes(
        onSuccess: () -> Unit = {},
        onFailure: (message: String) -> Unit = {}
    ) {

        if(System.currentTimeMillis() - refreshUiState.value.lastRefreshEpoch < MIN_REFRESH_WAIT_TIME) {
            onFailure("Please wait.")
            return // Don't refresh if it's too soon
        }

        loadInboxes(
            onStart = {
                _refreshUiState.update {
                    it.copy(
                        isRefreshing = true
                    )
                }
            },
            onComplete = {
                _refreshUiState.update {
                    it.copy(
                        isRefreshing = false,
                        lastRefreshEpoch = System.currentTimeMillis()
                    )
                }
                onSuccess()
            }
        )

    }

    private fun initialDatastoreSetupEvent() {
        setupNewsletterDatastoreUseCase()
    }

    init {
        Log.d("NewsletterViewModel", "Initializing...")

        initialDatastoreSetupEvent()

        loadInboxes()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val container =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).mainMenuContainer

                NewsletterViewModel(
                    setupNewsletterDatastoreUseCase = container.setupNewsletterUseCase,
                    initFlowNewsletterUseCase = container.initFlowNewsletterUseCase,
                    fetchNewsletterInboxesUseCase = container.getNewsletterInboxesUseCase,
                    saveNewsletterInboxLastReadDateUseCase = container.saveNewsletterInboxLastReadDateUseCase
                )
            }
        }

        private val MIN_REFRESH_WAIT_TIME = 30.seconds.inWholeMilliseconds
    }

    data class NewsletterRefreshingUiState(
        val isRefreshing: Boolean = false,
        val lastRefreshEpoch: Long = 0L
    )

    data class NewsletterInboxesUiState(
        val inboxes: List<NewsletterInboxUiState> = emptyList()
    )

    data class NewsletterInboxUiState (
        val inbox: NewsletterInbox,
        val lastReadDate: Long = 0L
    )

}