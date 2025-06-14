package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat.FlattenedNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterDatastore
import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow

interface NewsletterRepository {

    fun initialSetupEvent()
    suspend fun initFlow(): Flow<NewsletterDatastore.NewsletterPreferences>

    suspend fun saveInboxLastReadDate(id: String, date: Long)

    fun getLocalInboxes(): Result<List<FlattenedNewsletterInboxDto>>
    suspend fun fetchRemoteInbox(url: Url): Result<FlattenedNewsletterInboxDto>

    suspend fun synchronizeInboxes(forceUpdate: Boolean = false): Result<Boolean>
    fun getInboxes(): List<NewsletterInbox>

}