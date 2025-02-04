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
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.NewsletterHandler
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

    val inboxes = newsletterRepository.inboxes

    fun getInbox(inboxType: NewsletterRepository.NewsletterInboxType.InboxType) =
        newsletterHandler.getInbox(inboxType)

    /*class NewsletterFactory(
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
    }*/

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                //val savedStateHandle = createSavedStateHandle()
                val myRepository = (this[APPLICATION_KEY] as PETApplication).container.newsletterRepository
                NewsletterViewModel(
                    newsletterRepository = myRepository,
                    /*savedStateHandle = savedStateHandle*/
                )
            }
        }
    }
}
