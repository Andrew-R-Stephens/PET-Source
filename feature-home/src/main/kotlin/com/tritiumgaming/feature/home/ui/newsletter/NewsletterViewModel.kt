package com.tritiumgaming.feature.home.ui.newsletter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsletterInboxUiState
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsletterInboxesUiState
import com.tritiumgaming.feature.home.ui.newsletter.screen.NewsletterRefreshingUiState
import com.tritiumgaming.shared.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SetupNewsletterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class NewsletterViewModel(
    private val setupNewsletterDatastoreUseCase: SetupNewsletterUseCase,
    private val getFlowNewsletterDatastoreUseCase: GetFlowNewsletterDatastoreUseCase,
    private val getFlowNewsletterInboxesUseCase: GetFlowNewsletterInboxesUseCase,
    private val fetchNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    private val saveNewsletterInboxLastReadDateUseCase: SaveNewsletterInboxLastReadDateUseCase,
): ViewModel() {

    /*private val _inboxesUiState = MutableStateFlow<NewsletterInboxesUiState>(
        NewsletterInboxesUiState())
    val inboxesUiState = _inboxesUiState.asStateFlow()*/

    private val _inboxesUiState = getFlowNewsletterInboxesUseCase()
        .combine(getFlowNewsletterDatastoreUseCase()) { inboxes, datastore ->
            Log.d("NewsletterViewModel",
                "Fetched remote flow:\n ${inboxes.map { 
                    "${it.title}: ${it.channel?.messages?.map { message -> message.title }}\n"}}")
            Log.d("NewsletterViewModel",
                "Fetched datastore:\n ${datastore.data.entries.map { 
                    "${it.key} -> ${it.value}\n"}}")

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
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NewsletterInboxesUiState(
                inboxes = emptyList()
            )
        )
    val inboxesUiState = _inboxesUiState

    /*private val _newsletterPreferencesState =
        getFlowNewsletterDatastoreUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = NewsletterDatastore.NewsletterPreferences(
                    data = emptyMap()
                )
            )
    val newsletterPreferencesState = _newsletterPreferencesState*/

    private val _refreshUiState = MutableStateFlow(
        NewsletterRefreshingUiState())
    val refreshUiState = _refreshUiState.asStateFlow()

    fun saveInboxLastReadDate(inboxId: String, date: Long) {
        viewModelScope.launch {
            saveNewsletterInboxLastReadDateUseCase(inboxId, date)
        }
    }

    fun loadInboxes(
        onStart: () -> Unit = {},
        onSuccess: () -> Unit = {},
        onFailure: () -> Unit = {},
    ) {

        viewModelScope.launch {
            onStart()

            try {
                val fetchedInboxes = fetchNewsletterInboxesUseCase().getOrThrow()

                fetchedInboxes.forEach { inbox ->
                    Log.d("NewsletterViewModel",
                        "Fetched inbox: ${inbox.title} ${inbox.channel?.messages?.map { "${ it.title }\n" }}")
                }

                onSuccess()

            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }

        }
    }

    /*fun saveInboxLastReadDate(inboxId: String, date: Long) {
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
    }*/

    /*fun loadInboxes(
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

                getFlowNewsletterDatastoreUseCase { preferences ->
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
    }*/

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
            onSuccess = {
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
        Log.d("NewsletterViewModel", "Initializing datastore...")

        initialDatastoreSetupEvent()

        loadInboxes()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                NewsletterViewModel(
                    setupNewsletterDatastoreUseCase = container.setupNewsletterUseCase,
                    getFlowNewsletterDatastoreUseCase = container.getFlowNewsletterDatastoreUseCase,
                    getFlowNewsletterInboxesUseCase = container.getFlowNewsletterInboxesUseCase,
                    fetchNewsletterInboxesUseCase = container.getNewsletterInboxesUseCase,
                    saveNewsletterInboxLastReadDateUseCase = container.saveNewsletterInboxLastReadDateUseCase
                )
            }
        }

        private val MIN_REFRESH_WAIT_TIME = 30.seconds.inWholeMilliseconds
    }


}