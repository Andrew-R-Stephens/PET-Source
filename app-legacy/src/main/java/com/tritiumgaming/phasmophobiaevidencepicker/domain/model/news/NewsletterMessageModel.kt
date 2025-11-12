package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news

import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInboxModel.Companion.formatFromEpoch
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInboxModel.Companion.formatToEpoch
import com.tritiumgaming.phasmophobiaevidencepicker.util.FontUtils

class NewsletterMessageModel(title: String, description: String, date: String) {

    var title: String = title
    var description: String = description
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
