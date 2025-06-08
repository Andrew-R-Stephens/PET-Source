package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.FontUtils
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterChannelDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterMessageDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto.RemoteNewsletterInboxDto.RemoteNewsletterChannelDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto.RemoteNewsletterInboxDto.RemoteNewsletterChannelDto.RemoteNewsletterMessageDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterMessage
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.formatToEpoch
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

fun RemoteNewsletterInboxDto.toInternal(): FlattenedNewsletterInboxDto =
    FlattenedNewsletterInboxDto(
        channel = channel.toExternal()
    )

fun RemoteNewsletterChannelDto.toExternal(): FlattenedNewsletterChannelDto =
    FlattenedNewsletterChannelDto(
        language = language,
        messages = items?.toExternal()
    )

fun RemoteNewsletterMessageDto.toExternal(): FlattenedNewsletterMessageDto =
    FlattenedNewsletterMessageDto(
        id = id,
        title = FontUtils.removeXMLImgSrcTags(title),
        description = FontUtils.removeXMLImgSrcTags(description),
        date = formatToEpoch(FontUtils.removeXMLPubDateClockTime(pubDate))
    )

fun RemoteNewsletterMessageDto.toExternal(fallbackId: String): NewsletterMessage =
    NewsletterMessage(
        id = id ?: fallbackId,
        title = title,
        description = description,
        date = formatToEpoch(pubDate)
    )

@JvmName("remoteNewsletterInboxDtoListToFlattenedNewsletterInboxDtoList")
fun List<RemoteNewsletterInboxDto>.toExternal(): List<FlattenedNewsletterInboxDto> =
    map { dto ->
        FlattenedNewsletterInboxDto(
            channel = dto.channel.toExternal()
        )
    }

@JvmName("remoteNewsletterChannelDtoListToFlattenedNewsletterChannelDtoList")
fun List<RemoteNewsletterChannelDto>.toExternal(): List<FlattenedNewsletterChannelDto> =
    map { dto ->
        FlattenedNewsletterChannelDto(
            language = dto.language,
            messages = dto.items?.toExternal()
        )
    }

@JvmName("remoteNewsletterMessageDtoListToFlattenedNewsletterMessageDtoList")
fun List<RemoteNewsletterMessageDto>.toExternal(): List<FlattenedNewsletterMessageDto> =
    map { dto ->
        FlattenedNewsletterMessageDto(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            date = formatToEpoch(dto.pubDate)
        )
    }
