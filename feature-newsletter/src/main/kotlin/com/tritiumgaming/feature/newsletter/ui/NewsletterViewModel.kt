package com.tritiumgaming.feature.newsletter.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.feature.newsletter.app.container.NewsletterContainerProvider
import com.tritiumgaming.feature.newsletter.ui.screen.NewsletterInboxUiState
import com.tritiumgaming.feature.newsletter.ui.screen.NewsletterInboxesUiState
import com.tritiumgaming.feature.newsletter.ui.screen.NewsletterRefreshUiState
import com.tritiumgaming.shared.data.newsletter.usecase.FetchNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterDatastoreUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.GetFlowNewsletterInboxesUseCase
import com.tritiumgaming.shared.data.newsletter.usecase.SaveNewsletterInboxLastReadDateUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class NewsletterViewModel(
    private val getFlowNewsletterDatastoreUseCase: GetFlowNewsletterDatastoreUseCase,
    private val getFlowNewsletterInboxesUseCase: GetFlowNewsletterInboxesUseCase,
    private val fetchNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    private val saveNewsletterInboxLastReadDateUseCase: SaveNewsletterInboxLastReadDateUseCase,
): ViewModel() {

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

    private val _refreshUiState = MutableStateFlow(
        NewsletterRefreshUiState()
    )
    val refreshUiState = _refreshUiState.asStateFlow()

    fun saveInboxLastReadDate(inboxId: String) {
        viewModelScope.launch {
            val inbox = inboxesUiState.value.inboxes.first { inbox -> inbox.inbox.id == inboxId }
            val newDate = inbox.inbox.channel?.messages?.get(0)?.dateEpoch ?:
                System.currentTimeMillis()

            if(inbox.lastReadDate < newDate) {
                saveNewsletterInboxLastReadDateUseCase(inboxId, newDate)
            }
        }
    }

    fun saveInboxLastReadDate(inboxId: String, date: Long) {
        viewModelScope.launch {
            val inbox = inboxesUiState.value.inboxes.first { inbox -> inbox.inbox.id == inboxId }

            if(inbox.lastReadDate < date) {
                saveNewsletterInboxLastReadDateUseCase(inboxId, date)
            }
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

    fun refreshInboxes(
        onSuccess: () -> Unit = {},
        onFailure: (message: String) -> Unit = {}
    ) {

        if(System.currentTimeMillis() - refreshUiState.value.lastRefreshEpoch < MIN_REFRESH_WAIT_TIME) {
            onFailure("Please wait to refresh.")
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

    init {
        Log.d("NewsletterViewModel", "Initializing datastore...")

        loadInboxes()
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as NewsletterContainerProvider).provideNewsletterContainer()

                NewsletterViewModel(
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