package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api.NewsletterService
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.remote.NewsletterRemoteDataSourceInterface
import io.ktor.http.Url

class NewsletterRemoteDataSource: NewsletterRemoteDataSourceInterface {

    override suspend fun fetchRemoteInboxData(inbox: NewsletterInbox) {
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

