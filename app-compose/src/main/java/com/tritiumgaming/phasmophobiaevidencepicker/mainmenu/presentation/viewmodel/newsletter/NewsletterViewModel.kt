package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.model.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.handler.NewsletterManager
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsletterViewModel(
    private val newsletterManager: NewsletterManager
): ViewModel() {

    private fun initialSetupEvent() {
        newsletterManager.initialSetupEvent()
    }

    init {
        Log.d("NewsletterViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            newsletterManager.createInboxes()
        }

        viewModelScope.launch {
            newsletterManager.initFlow()
        }
    }

    val inboxes = newsletterManager.inboxes

    fun requiresNotify(inboxType: NewsletterInboxType.NewsletterInboxTypeDTO?): StateFlow<Boolean>? {
        if(inboxType == null) return newsletterManager.requiresNotify

        return newsletterManager.requiresNotify(inboxType)
    }

    fun getInbox(inboxType: NewsletterInboxType.NewsletterInboxTypeDTO) =
        newsletterManager.getInbox(inboxType)

    fun setLastReadDate(
        inboxType: NewsletterInboxType.NewsletterInboxTypeDTO, date: Long,
        onComplete: () -> Unit = {}
    ) {
        viewModelScope.launch {
            newsletterManager.setLastReadDate(inboxType, date)
            onComplete()
        }
    }

    class NewsletterFactory(
        private val newsletterManager: NewsletterManager
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsletterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsletterViewModel(
                    newsletterManager = newsletterManager
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

                val newsletterManager: NewsletterManager = container.newsletterManager

                NewsletterViewModel(
                    newsletterManager = newsletterManager
                )
            }
        }
    }
}
