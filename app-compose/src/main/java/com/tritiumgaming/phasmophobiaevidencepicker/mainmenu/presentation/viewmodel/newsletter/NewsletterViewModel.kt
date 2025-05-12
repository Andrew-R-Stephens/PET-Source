package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.handler.NewsletterManager
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsletterViewModel(
    private val repository: NewsletterRepository,
    private val datastore: NewsletterDatastore
): ViewModel() {

    val newsletterManager: NewsletterManager = NewsletterManager(
        repository, datastore
    )

    private fun initialSetupEvent() {
        datastore.initialSetupEvent()
    }

    init {
        Log.d("NewsletterViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            newsletterManager.initFlow()
        }
    }

    fun requiresNotify(inboxType: NewsletterRepository.NewsletterInboxType.InboxType?): StateFlow<Boolean>? {
        if(inboxType == null) return repository.requiresNotify

        return repository.requiresNotify(inboxType)
    }

    fun getInbox(inboxType: NewsletterRepository.NewsletterInboxType.InboxType) = repository.getInbox(inboxType)

    fun setLastReadDate(
        inboxType: NewsletterRepository.NewsletterInboxType.InboxType, date: Long,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            newsletterManager.setLastReadDate(inboxType, date)
            onComplete()
        }
    }

    class NewsletterFactory(
        private val newsletterRepository: NewsletterRepository,
        private val newsletterDatastore: NewsletterDatastore
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsletterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsletterViewModel(
                    repository = newsletterRepository,
                    datastore = newsletterDatastore
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).container

                val newsletterRepository = container.newsletterRepository
                val newsletterDatastore = container.newsletterDatastore

                NewsletterViewModel(
                    repository = newsletterRepository,
                    datastore = newsletterDatastore
                )
            }
        }
    }
}
