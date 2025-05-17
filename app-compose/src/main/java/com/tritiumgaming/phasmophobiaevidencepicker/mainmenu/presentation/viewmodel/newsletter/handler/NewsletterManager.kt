package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.handler

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepositoryImpl
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.datastore.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.PreferenceKeys.KEY_INBOX_GENERAL
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.PreferenceKeys.KEY_INBOX_PET
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.PreferenceKeys.KEY_INBOX_PHASMOPHOBIA
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewsletterManager(
    repository: NewsletterRepositoryImpl,
    private val datastore: NewsletterDatastore
) {

    fun initialSetupEvent() {
        datastore.initialSetupEvent()
    }

    suspend fun initFlow() {

        datastore.flow.collect { preferences ->

            val general = getInbox(NewsletterInboxType.InboxTypeDTO.GENERAL)
            val pet = getInbox(NewsletterInboxType.InboxTypeDTO.PET)
            val phasmophobia = getInbox(NewsletterInboxType.InboxTypeDTO.PHASMOPHOBIA)

            general?.setLastReadDate( preferences.lastReadDateGeneral )
            pet?.setLastReadDate( preferences.lastReadDatePET )
            phasmophobia?.setLastReadDate( preferences.lastReadDatePhasmophobia )

            Log.d("Newsletter", "Collecting from flow: \n" +
                    "\tlastReadDateGeneral -> ${ general?.lastReadDate?.value }\n" +
                    "\tlastReadDatePET -> ${ pet?.lastReadDate?.value }\n" +
                    "\tlastReadDatePhasmophobia -> ${ phasmophobia?.lastReadDate?.value }\n"
            )

            setRequiresNotify(
                general?.requiresNotify?.value != false ||
                        pet?.requiresNotify?.value != false ||
                        phasmophobia?.requiresNotify?.value != false
            )
        }
    }

    private val inboxes = repository.inboxes

    private val _requiresNotify = MutableStateFlow<Boolean>(false)
    val requiresNotify = _requiresNotify.asStateFlow()
    fun requiresNotify(inboxType: NewsletterInboxType.InboxTypeDTO) = getInbox(inboxType)?.requiresNotify
    fun setRequiresNotify(value: Boolean) {
        _requiresNotify.update { value }
    }

    fun getInbox(inboxType: NewsletterInboxType.InboxTypeDTO): NewsletterInbox? {
        return inboxes.value[inboxType]
    }

    suspend fun setLastReadDate(inboxType: NewsletterInboxType.InboxTypeDTO, date: Long) {
        val key = when(inboxType) {
            NewsletterInboxType.InboxTypeDTO.PHASMOPHOBIA -> KEY_INBOX_PHASMOPHOBIA
            NewsletterInboxType.InboxTypeDTO.PET -> KEY_INBOX_PET
            NewsletterInboxType.InboxTypeDTO.GENERAL -> KEY_INBOX_GENERAL
        }
        datastore.setLastReadDate(key, date)
    }


}