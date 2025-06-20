package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.datastore.DatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.NewsletterDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.NewsletterDatastore.NewsletterPreferences
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import kotlinx.coroutines.flow.Flow

interface NewsletterRepository: DatastoreRepository<NewsletterPreferences> {

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    suspend fun synchronizeInboxes(forceUpdate: Boolean = false): Result<Boolean>
    fun getInboxes(): List<NewsletterInbox>

}