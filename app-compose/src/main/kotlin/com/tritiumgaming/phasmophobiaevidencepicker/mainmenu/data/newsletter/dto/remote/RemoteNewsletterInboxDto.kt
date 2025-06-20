package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.remote

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.FontUtils
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat.FlattenedNewsletterChannelDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat.FlattenedNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.flat.FlattenedNewsletterMessageDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.remote.RemoteNewsletterInboxDto.RemoteNewsletterChannelDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.remote.RemoteNewsletterInboxDto.RemoteNewsletterChannelDto.RemoteNewsletterMessageDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterMessage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

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
        language = language ?: Locale.ENGLISH.language,
        messages = items?.toExternal()
    )

fun RemoteNewsletterMessageDto.toExternal(): FlattenedNewsletterMessageDto =
    FlattenedNewsletterMessageDto(
        id = id,
        title = title,
        description = description,
        date = formatToEpoch(FontUtils.removeXMLPubDateClockTime(pubDate))
    )

fun RemoteNewsletterMessageDto.toExternal(fallbackId: String): NewsletterMessage =
    NewsletterMessage(
        id = id ?: fallbackId,
        title = title,
        description = description,
        dateFormatted = pubDate
    )

@JvmName("remoteNewsletterInboxDtoListToFlattenedNewsletterInboxDtoList")
fun List<RemoteNewsletterInboxDto>.toExternal(): List<FlattenedNewsletterInboxDto> =
    map { dto -> dto.toInternal() }

@JvmName("remoteNewsletterChannelDtoListToFlattenedNewsletterChannelDtoList")
fun List<RemoteNewsletterChannelDto>.toExternal(): List<FlattenedNewsletterChannelDto> =
    map { dto -> dto.toExternal() }

@JvmName("remoteNewsletterMessageDtoListToFlattenedNewsletterMessageDtoList")
fun List<RemoteNewsletterMessageDto>.toExternal(): List<FlattenedNewsletterMessageDto> =
    map { dto -> dto.toExternal() }

private fun formatFromEpoch(time: Long?): String? {

    time ?: return formatFromEpoch(1L)

    val parser = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)
    var stringDate: String? = null
    try {
        stringDate = parser.format(time)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }

    return stringDate

}

private fun formatToEpoch(stringDate: String?): Long {

    stringDate ?: return 1L

    val dateFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)
    var time = 1L
    try {
        time = dateFormatter.parse(stringDate)?.time ?: time
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return time

}
