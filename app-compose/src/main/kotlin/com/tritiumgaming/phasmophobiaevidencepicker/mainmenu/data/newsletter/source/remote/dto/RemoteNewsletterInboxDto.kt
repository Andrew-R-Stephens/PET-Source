package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

@Serializable
@SerialName("rss")
@XmlSerialName(value = "rss")
data class RemoteNewsletterInboxDto(
    val channel: RemoteNewsletterChannelDto
) {
    @Serializable
    @SerialName("channel")
    data class RemoteNewsletterChannelDto(
        @XmlElement val language: String? = null,
        @XmlElement @XmlSerialName("item")
        val items: List<RemoteNewsletterMessageDto>? = null
    ) {
        @Serializable
        data class RemoteNewsletterMessageDto(
            @XmlSerialName("id") @XmlElement val id: String? = null,
            @XmlSerialName("title") @XmlElement val title: String? = null,
            @XmlSerialName("pubDate") @XmlElement val pubDate: String? = null,
            @XmlSerialName("description") @XmlElement val description: String? = null
        )

    }
}
