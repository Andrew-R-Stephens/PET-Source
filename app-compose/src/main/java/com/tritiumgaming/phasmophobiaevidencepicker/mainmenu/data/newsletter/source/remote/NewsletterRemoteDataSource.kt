package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterRemoteDataSource
import io.ktor.http.Url

class NewsletterRemoteDataSource: NewsletterRemoteDataSource {

    override suspend fun fetchInbox(inbox: NewsletterInbox) {
        NewsletterService.fetchInbox(
            Url(inbox.inboxType.url),
            onSuccess = { inboxDto ->
                val channel = inboxDto.channel
                Log.d("Newsletter", "${channel.items?.size}")
                channel.items?.forEachIndexed { index, messageDto ->
                    val message = messageDto.toNewsletterMessage(fallbackId = "$index")
                    message.language = channel.language
                    inbox.addMessage(message)
                }
            }
        )
    }

}

