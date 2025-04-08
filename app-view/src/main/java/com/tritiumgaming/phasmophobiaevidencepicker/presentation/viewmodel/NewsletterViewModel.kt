package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.NewsletterHandler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsletterViewModel(
    private val newsletterRepository: NewsletterRepository
): ViewModel() {

    private val newsletterHandler: NewsletterHandler = NewsletterHandler(newsletterRepository)

    private fun initialSetupEvent() {
        newsletterHandler.initialSetupEvent()
    }

    init {
        Log.d("NewsletterViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            newsletterHandler.initFlow()
        }
    }

    fun requiresNotify(inboxType: InboxType?): StateFlow<Boolean>? {
        if(inboxType == null) return newsletterRepository.requiresNotify

        return newsletterRepository.requiresNotify(inboxType)
    }

    fun getInbox(inboxType: InboxType) = newsletterRepository.getInbox(inboxType)

    fun setLastReadDate(
        inboxType: InboxType, date: Long,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            newsletterRepository.setLastReadDate(inboxType, date)
            onComplete()
        }
    }

    class NewsletterFactory(
        private val newsletterRepository: NewsletterRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsletterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsletterViewModel(
                    newsletterRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as PETApplication).container
                val newsletterRepository = container.newsletterRepository

                NewsletterViewModel(
                    newsletterRepository = newsletterRepository
                )
            }
        }
    }
}

