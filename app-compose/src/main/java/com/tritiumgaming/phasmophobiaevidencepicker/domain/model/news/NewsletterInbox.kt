package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news

import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


class NewsletterInbox(
    val inboxType: NewsletterRepository.NewsletterInboxType
) {

    val messages: ArrayList<NewsletterMessage> = ArrayList()

    var ready: Boolean = false

    var lastReadDate: Long = formatToEpoch(null)
        set(value) {
            field = value.coerceAtLeast(field)
        }

    fun addMessage(msg: NewsletterMessage) {
        messages.add(msg)
    }

    /** @return evaluates the age of one message compared to another
     * 1 if specifiedDate is newer than the lastReadDate,
     * -1 if specifiedDate is older than the lastReadDate,
     * 0 if specifiedDate is ths same age as lastReadDate **/
    fun compareDate(specifiedDate: Long = lastReadDate): Int {

        return (specifiedDate - lastReadDate).coerceIn(-1L, 1L).toInt()

    }

    fun compareDates(): Boolean {

        messages.forEach { message ->
            if(compareDate(message.date) > 0L) {
                return true
            }
        }
        return false

    }

    fun updateLastReadDate(message: NewsletterMessage = messages[0]) {

        lastReadDate = message.date
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

    fun assignData(data: Unit) {

    }

    companion object {

        const val MAX_MESSAGE_COUNT: Int = 10

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

}