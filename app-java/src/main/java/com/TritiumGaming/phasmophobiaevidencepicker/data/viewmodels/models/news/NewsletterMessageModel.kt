package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news

import com.TritiumGaming.phasmophobiaevidencepicker.utils.FontUtils

class NewsletterMessageModel(title: String, description: String, date: String) {

    var title: String =
        FontUtils.removeXMLImgSrcTags(title)
    var description: String =
        FontUtils.removeXMLImgSrcTags(description)
    var date: String =
        FontUtils.removeXMLPubDateClockTime(FontUtils.removeXMLPubDateClockTime(date))

    fun hasContent(): Boolean {
        return hasTitle() || hasDescription() || hasDate()
    }

    private fun hasDate(): Boolean {
        return date.isNotEmpty()
    }

    private fun hasDescription(): Boolean {
        return description.isNotEmpty()
    }

    private fun hasTitle(): Boolean {
        return title.isNotEmpty()
    }
}
