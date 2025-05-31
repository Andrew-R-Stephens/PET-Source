package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore
import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow

interface NewsletterRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<NewsletterDatastore.NewsletterPreferences>

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    suspend fun getLocalInboxes(): List<FlattenedNewsletterInboxDto>
    suspend fun fetchRemoteInbox(url: Url): FlattenedNewsletterInboxDto

    suspend fun synchronizeInboxes()
    fun getInboxes(): List<NewsletterInbox>

}