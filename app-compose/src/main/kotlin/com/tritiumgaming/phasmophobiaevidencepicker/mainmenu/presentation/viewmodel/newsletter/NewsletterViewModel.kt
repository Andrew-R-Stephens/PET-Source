package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter

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
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.usecase.SyncNewsletterInboxesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsletterViewModel(
    private val setupNewsletterUseCase: SetupNewsletterUseCase,
    private val initFlowNewsletterUseCase: InitFlowNewsletterUseCase,
    private val fetchNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
    private val synchronizeNewsletterInboxesUseCase: SyncNewsletterInboxesUseCase,
    private val saveNewsletterInboxLastReadDateUseCase: SaveNewsletterInboxLastReadDateUseCase,
): ViewModel() {

    private val _inboxes = MutableStateFlow<List<NewsletterInbox>>(fetchNewsletterInboxesUseCase())
    val inboxes = _inboxes.asStateFlow()
    private fun getInbox(inboxId: String): NewsletterInbox? {
        return inboxes.value.find { inbox -> inbox.id == inboxId }
    }

    private val _mainNotificationState = MutableStateFlow<Boolean>(false)
    val mainNotificationState = _mainNotificationState.asStateFlow()
    fun setMainNotificationState(value: Boolean) {
        _mainNotificationState.update { value }
    }

    fun saveInboxLastReadDate(inboxId: String, date: Long) {
        viewModelScope.launch {
            saveNewsletterInboxLastReadDateUseCase(inboxId, date)
        }
    }

    private fun initialSetupEvent() {
        setupNewsletterUseCase()
    }

    init {
        Log.d("NewsletterViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            synchronizeNewsletterInboxesUseCase()

            _inboxes.update { fetchNewsletterInboxesUseCase() }

            initFlowNewsletterUseCase { preferences ->
                inboxes.value.forEach {
                    it.inboxLastReadDate = (preferences.data[it.id] ?: 0L)
                    Log.d("NewsletterViewModel", "ID ${it.id}: lastReadDate: ${it.inboxLastReadDate}")
                }
            }
        }
    }

    class NewsletterFactory(
        private val setupNewsletterUseCase: SetupNewsletterUseCase,
        private val initFlowNewsletterUseCase: InitFlowNewsletterUseCase,
        private val fetchNewsletterInboxesUseCase: FetchNewsletterInboxesUseCase,
        private val syncNewsletterInboxesUseCase: SyncNewsletterInboxesUseCase,
        private val saveNewsletterInboxLastReadDateUseCase: SaveNewsletterInboxLastReadDateUseCase,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsletterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsletterViewModel(
                    setupNewsletterUseCase = setupNewsletterUseCase,
                    initFlowNewsletterUseCase = initFlowNewsletterUseCase,
                    fetchNewsletterInboxesUseCase = fetchNewsletterInboxesUseCase,
                    synchronizeNewsletterInboxesUseCase = syncNewsletterInboxesUseCase,
                    saveNewsletterInboxLastReadDateUseCase = saveNewsletterInboxLastReadDateUseCase
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {

                val container =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).mainMenuContainer

                NewsletterViewModel(
                    setupNewsletterUseCase = container.setupNewsletterUseCase,
                    initFlowNewsletterUseCase = container.initFlowNewsletterUseCase,
                    fetchNewsletterInboxesUseCase = container.getNewsletterInboxesUseCase,
                    synchronizeNewsletterInboxesUseCase = container.syncNewsletterInboxesUseCase,
                    saveNewsletterInboxLastReadDateUseCase = container.saveNewsletterInboxLastReadDateUseCase
                )
            }
        }
    }
}
