package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news

import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


class NewsletterInboxModel {

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
        set(value) {
            field = value.coerceAtLeast(field)
        }

    var inboxType: NewsletterViewModel.InboxType? = null
    val messages: ArrayList<NewsletterMessageModel> = ArrayList()

    fun add(msg: NewsletterMessageModel) {
        messages.add(msg)
    }

    /** @return 1 if newestMessageDate is newer than parameter, -1 if older, and 0 if == **/
    fun compareDate(specifiedDate: Long = lastReadDate): Int {
        val out = (specifiedDate - lastReadDate).coerceIn(-1L, 1L).toInt()
        /*
        Log.d("MessageCenter",
        "[Comparing] specifiedDate: $specifiedDate; specifiedDate: $specifiedDate; " +
                "savedDate: $lastReadDate; difference: $out")
        */
        return out
    }

    fun compareDates(): Boolean {
        messages.forEach { message ->
            if(compareDate(message.date) > 0) {
                return true
            }
        }
        return false
    }

    fun updateLastReadDate(message: NewsletterMessageModel) {
        lastReadDate = message.date
        compareDate()
    }

    fun updateLastReadDate() {
        lastReadDate = messages[0].date
        compareDate()
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