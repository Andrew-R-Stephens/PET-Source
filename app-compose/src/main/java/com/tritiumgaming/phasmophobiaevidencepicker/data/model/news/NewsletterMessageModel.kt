package com.tritiumgaming.phasmophobiaevidencepicker.data.model.news

import com.tritiumgaming.phasmophobiaevidencepicker.data.model.news.NewsletterInboxModel.Companion.formatFromEpoch
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.news.NewsletterInboxModel.Companion.formatToEpoch
import com.tritiumgaming.phasmophobiaevidencepicker.utils.FontUtils

class NewsletterMessageModel(title: String, description: String, date: String) {

    var title: String = FontUtils.removeXMLImgSrcTags(title)
    var description: String = FontUtils.removeXMLImgSrcTags(description)
    var date: Long = formatToEpoch(FontUtils.removeXMLPubDateClockTime(date))

    fun hasContent(): Boolean {
        return description.isNotEmpty() || title.isNotEmpty()
    }

    fun getDateFormatted(): String {
        return formatFromEpoch(date) ?: "NA"
    }

    override fun toString(): String {
        return "Title: $title\nDate: $date\nDescription: $description"
    }
}
