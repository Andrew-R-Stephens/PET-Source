package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.FontUtils
import java.text.SimpleDateFormat
import java.util.Locale

class NewsletterMessage(
    val id: String,
    title: String? = "",
    description: String? = "",
    date: Long? = 0L
){

    val title: String = FontUtils.removeXMLImgSrcTags(title)
    val description: String = FontUtils.removeXMLImgSrcTags(description)
    val date: Long = date ?: 0L

    @Deprecated("Unused")
    fun getDateFormatted(): String {
        return formatFromEpoch(date) ?: "NA"
    }

}

fun formatFromEpoch(time: Long?): String? {

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