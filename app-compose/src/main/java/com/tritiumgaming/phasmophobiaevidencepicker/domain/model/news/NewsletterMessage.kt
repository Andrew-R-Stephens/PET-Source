package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news

import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox.Companion.formatFromEpoch
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox.Companion.formatToEpoch
import com.tritiumgaming.phasmophobiaevidencepicker.util.FontUtils

class NewsletterMessage(
    val id: String,
    title: String? = "",
    description: String? = "",
    date: String? = ""
){

    var title: String = FontUtils.removeXMLImgSrcTags(title)
    var description: String = FontUtils.removeXMLImgSrcTags(description)
    var date: Long = formatToEpoch(FontUtils.removeXMLPubDateClockTime(date))

    fun getDateFormatted(): String {
        return formatFromEpoch(date) ?: "NA"
    }

}
