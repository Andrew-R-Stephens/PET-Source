package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api.dto

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox.NewsletterMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@SerialName("rss")
@XmlSerialName(value = "rss")
data class NewsletterInboxDTO(
    val channel: NewsletterChannelDTO
) {
    @Serializable
    @SerialName("channel")
    data class NewsletterChannelDTO(
        @XmlElement val language: String? = null,
        @XmlElement @XmlSerialName("item")
        val items: List<NewsletterMessageDTO>? = null
    ) {
        @Serializable
        data class NewsletterMessageDTO(
            @XmlSerialName("id") @XmlElement val id: String? = null,
            @XmlSerialName("title") @XmlElement val title: String? = null,
            @XmlSerialName("pubDate") @XmlElement val pubDate: String? = null,
            @XmlSerialName("description") @XmlElement val description: String? = null
        ) {

            fun toNewsletterMessage(fallbackId: String): NewsletterMessage {
                return NewsletterMessage(
                    id = id ?: fallbackId,
                    title = title,
                    description = description,
                    date = pubDate
                )
            }

        }

    }
}