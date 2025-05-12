package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.viewmodel.newsletter.handler

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepository.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.PreferenceKeys.KEY_INBOX_GENERAL
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.PreferenceKeys.KEY_INBOX_PET
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.NewsletterDatastoreInterface.PreferenceKeys.KEY_INBOX_PHASMOPHOBIA

class NewsletterManager(
private val repository: NewsletterRepository,
private val datastore: NewsletterDatastore
) {

    fun initialSetupEvent() {
        datastore.initialSetupEvent()
    }

    suspend fun initFlow() {

        datastore.flow.collect { preferences ->

            val general = repository.getInbox(NewsletterInboxType.InboxType.GENERAL)
            val pet = repository.getInbox(NewsletterInboxType.InboxType.PET)
            val phasmophobia = repository.getInbox(NewsletterInboxType.InboxType.PHASMOPHOBIA)

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

    suspend fun setLastReadDate(inboxType: NewsletterInboxType.InboxType, date: Long) {
        val key = when(inboxType) {
            NewsletterInboxType.InboxType.PHASMOPHOBIA -> KEY_INBOX_PHASMOPHOBIA
            NewsletterInboxType.InboxType.PET -> KEY_INBOX_PET
            NewsletterInboxType.InboxType.GENERAL -> KEY_INBOX_GENERAL
        }
        datastore.setLastReadDate(key, date)
    }


}