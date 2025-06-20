package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.remote.RemoteNewsletterInboxDto
import io.ktor.http.Url

interface NewsletterRemoteDataSource {

    suspend fun fetchInbox(url: Url): Result<RemoteNewsletterInboxDto>

}