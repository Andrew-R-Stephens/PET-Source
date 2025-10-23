package com.tritiumgaming.data.newsletter.dto.flat

import android.util.Log
import com.tritiumgaming.core.common.util.FontUtils
import com.tritiumgaming.shared.home.domain.newsletter.model.NewsletterMessage
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

data class FlattenedNewsletterMessageDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: Long? = null
)

fun FlattenedNewsletterMessageDto.toExternal(): NewsletterMessage =
    NewsletterMessage(
        id = id ?: "0",
        title = FontUtils.removeXMLImgSrcTags(title),
        description = FontUtils.removeXMLImgSrcTags(description),
        dateFormatted = formatFromEpoch(date),
        dateEpoch = date ?: 1L
    )


fun List<FlattenedNewsletterMessageDto>.toExternal(): List<NewsletterMessage> =
    mapIndexed { index, dto ->

        val newsletterMessage = NewsletterMessage(
            id = dto.id ?: "$index",
            title = dto.title,
            description = dto.description,
            dateFormatted = formatFromEpoch(dto.date),
            dateEpoch = dto.date ?: 1L
        )

        newsletterMessage
    }

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
