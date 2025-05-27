package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.mapper

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.FontUtils
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterChannelDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto.FlattenedNewsletterMessageDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.dto.LocalNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto.RemoteNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto.RemoteNewsletterInboxDto.RemoteNewsletterChannelDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.dto.RemoteNewsletterInboxDto.RemoteNewsletterChannelDto.RemoteNewsletterMessageDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterChannel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterMessage
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.formatToEpoch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.String

@JvmName("localNewsletterInboxDtoToFlattenedNewsletterInboxDto")
fun LocalNewsletterInboxDto.toExternal(): FlattenedNewsletterInboxDto =
    FlattenedNewsletterInboxDto(
        id = id,
        title = title,
        url = url,
        icon = icon
    )

@JvmName("remoteNewsletterInboxDtoToFlattenedNewsletterInboxDto")
fun RemoteNewsletterInboxDto.toInternal(): FlattenedNewsletterInboxDto =
    FlattenedNewsletterInboxDto(
        channel = channel.toExternal()
    )

@JvmName("remoteNewsletterChannelDtoToFlattenedNewsletterChannelDto")
fun RemoteNewsletterChannelDto.toExternal(): FlattenedNewsletterChannelDto =
    FlattenedNewsletterChannelDto(
        language = language,
        messages = items?.toExternal()
    )

@JvmName("remoteNewsletterMessageDtoToFlattenedNewsletterMessageDto")
fun RemoteNewsletterMessageDto.toExternal(): FlattenedNewsletterMessageDto =
    FlattenedNewsletterMessageDto(
        id = id,
        title = FontUtils.removeXMLImgSrcTags(title),
        description = FontUtils.removeXMLImgSrcTags(description),
        date = formatToEpoch(FontUtils.removeXMLPubDateClockTime(pubDate))
    )

@JvmName("remoteNewsletterMessageDtoToNewsletterMessage")
fun RemoteNewsletterMessageDto.toExternal(fallbackId: String): NewsletterMessage =
    NewsletterMessage(
        id = id ?: fallbackId,
        title = title,
        description = description,
        date = formatToEpoch(pubDate)
    )

@JvmName("flattenedNewsletterInboxDtoToNewsletterInbox")
fun FlattenedNewsletterInboxDto.toExternal(): NewsletterInbox =
    NewsletterInbox(
        id = id,
        title = title,
        url = url,
        icon = icon
    )

@JvmName("flattenedNewsletterChannelDtoToNewsletterChannel")
fun FlattenedNewsletterChannelDto.toExternal(): NewsletterChannel {
    return NewsletterChannel(
        language = language,
        messages = messages?.toExternal() ?: listOf()
    )
}
@JvmName("flattenedNewsletterMessageDtoToNewsletterMessage")
fun FlattenedNewsletterMessageDto.toExternal(): NewsletterMessage =
    NewsletterMessage(
        id = id ?: "0",
        title = title,
        description = description,
        date = date
    )

@JvmName("flattenedNewsletterInboxDtoListToNewsletterInboxList")
fun List<FlattenedNewsletterInboxDto>.toExternal(): List<NewsletterInbox> =
    map { dto ->
        NewsletterInbox(
            id = dto.id,
            title = dto.title,
            url = dto.url,
            icon = dto.icon,
            channel = dto.channel?.toExternal() ?: NewsletterChannel()
        )
    }

@JvmName("flattenedNewsletterMessageDtoListToNewsletterMessageList")
fun List<FlattenedNewsletterMessageDto>.toExternal(): List<NewsletterMessage> =
    mapIndexed { index, dto ->
        NewsletterMessage(
            id = dto.id ?: "$index",
            title = dto.title,
            description = dto.description,
            date = dto.date
        )
    }

@JvmName("localNewsletterInboxDtoToFlattenedNewsletterInboxDto")
fun List<LocalNewsletterInboxDto>.toInternal(): List<FlattenedNewsletterInboxDto> =
    map { dto ->
    FlattenedNewsletterInboxDto(
        id = dto.id,
        title = dto.title,
        url = dto.url,
        icon = dto.icon
    )
}

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
