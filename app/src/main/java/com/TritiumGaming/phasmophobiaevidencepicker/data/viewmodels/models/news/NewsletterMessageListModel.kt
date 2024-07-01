package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news

import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


class NewsletterMessageListModel {

    companion object {
        fun formatToEpoch(stringDate: String?): Long {
            stringDate ?: return 1L

            val dateFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)
            var time = 1L
            try { time = dateFormatter.parse(stringDate)?.time ?: time }
            catch (e: ParseException) { e.printStackTrace() }

            return time
        }

        fun formatFromEpoch(time: Long?): String? {
            time ?: return formatFromEpoch(1L)

            val parser = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)
            var stringDate: String? = null
            try { stringDate = parser.format(time) }
            catch (e: IllegalArgumentException) { e.printStackTrace() }

            return stringDate
        }
    }

    var ready: Boolean = false
    private var requiresNotify: Boolean = false

    var lastReadDate: Long = formatToEpoch(null)
    private var newestMessageDate: Long = formatToEpoch(null)

    var inboxType: NewsletterViewModel.InboxType? = null
    val messages: ArrayList<NewsletterMessageModel> = ArrayList()

    fun add(msg: NewsletterMessageModel) {
        newestMessageDate = msg.date

        messages.add(msg)
    }

    /** @return 1 if newestMessageDate is newer than parameter, -1 if older, and 0 if == **/
    fun compareDates(specifiedDate: Long = newestMessageDate): Int {
        val out = (specifiedDate - lastReadDate).toInt().coerceIn(-1, 1)
        requiresNotify = out > 0
        return out
    }

    fun updateLastReadDate() {
        lastReadDate = newestMessageDate

        compareDates()
    }

    override fun toString(): String {
        val t = StringBuilder()

        for (m in messages) {
            t.append("\n[").append(m.title).append(" ").append(m.date).append(" ")
                .append(m.description).append("]")
        }
        return t.toString()
    }

}

typealias SDFDate = java.util.Date
