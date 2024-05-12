package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data

import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FontUtils

class NewsletterMessageData(title: String?, description: String?, date: String?) {
    @JvmField
    var title: String? = null
    @JvmField
    var description: String? = null
    @JvmField
    var date: String? = null

    init {
        this.title = FontUtils.removeXMLImgSrcTags(title)
        this.description = FontUtils.removeXMLImgSrcTags(description)
        this.date = FontUtils.removeXMLPubDateClockTime(FontUtils.removeXMLPubDateClockTime(date))
    }

    fun hasContent(): Boolean {
        return hasTitle() || hasDescription() || hasDate()
    }

    private fun hasDate(): Boolean {
        return date != null && !date!!.isEmpty()
    }

    private fun hasDescription(): Boolean {
        return description != null && !description!!.isEmpty()
    }

    private fun hasTitle(): Boolean {
        return title != null && !title!!.isEmpty()
    }
}
