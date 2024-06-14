package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data

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
        return date.isNotEmpty() ?: false
    }

    private fun hasDescription(): Boolean {
        return description.isNotEmpty() ?: false
    }

    private fun hasTitle(): Boolean {
        return title.isNotEmpty() ?: false
    }
}
