package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers

import android.util.Log
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewsletterHandler(
    private val repository: NewsletterRepository
) {

    fun initialSetupEvent() {
        liveData {
            emit(repository.fetchInitialPreferences())
        }
    }

    suspend fun initFlow() {

        repository.flow.collect { preferences ->

            val general = repository.getInbox(InboxType.GENERAL)
            val pet = repository.getInbox(InboxType.PET)
            val phasmophobia = repository.getInbox(InboxType.PHASMOPHOBIA)

            general?.setLastReadDate( preferences.lastReadDateGeneral )
            pet?.setLastReadDate( preferences.lastReadDatePET )
            phasmophobia?.setLastReadDate( preferences.lastReadDatePhasmophobia )

            Log.d("Newsletter", "Collecting from flow: \n" +
                    "\tlastReadDateGeneral -> ${ general?.lastReadDate?.value }\n" +
                    "\tlastReadDatePET -> ${ pet?.lastReadDate?.value }\n" +
                    "\tlastReadDatePhasmophobia -> ${ phasmophobia?.lastReadDate?.value }\n"
            )

            repository.setRequiresNotify(
                    general?.requiresNotify?.value != false ||
                    pet?.requiresNotify?.value != false ||
                    phasmophobia?.requiresNotify?.value != false
            )
        }

    }

}
