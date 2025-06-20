package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.datastore.DatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore.NewsletterPreferences

interface NewsletterRepository: DatastoreRepository<NewsletterPreferences> {

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    suspend fun synchronizeInboxes(forceUpdate: Boolean = false): Result<Boolean>
    fun getInboxes(): List<NewsletterInbox>

}