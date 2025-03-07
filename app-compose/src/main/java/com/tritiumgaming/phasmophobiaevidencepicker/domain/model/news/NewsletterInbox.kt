package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news

import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


class NewsletterInbox(
    val inboxType: NewsletterRepository.NewsletterInboxType
) {

    var messages = mutableMapOf<String, NewsletterMessage>()

    var ready: Boolean = false

    private val _lastReadDate = MutableStateFlow(formatToEpoch(null))
    val lastReadDate = _lastReadDate.asStateFlow()
    fun setLastReadDate(value: Long) {
        _lastReadDate.value = value.coerceAtLeast(value)
    }

    fun addMessage(msg: NewsletterMessage) {
        messages[msg.id] = msg
    }

    /** @return evaluates the age of one message compared to another
     * 1 if specifiedDate is newer than the lastReadDate,
     * -1 if specifiedDate is older than the lastReadDate,
     * 0 if specifiedDate is ths same age as lastReadDate **/
    fun compareDate(specifiedDate: Long = lastReadDate.value): Int {

        return (specifiedDate - lastReadDate.value).coerceIn(-1L, 1L).toInt()

    }

    fun compareDates(): Boolean {

        messages.forEach { pair ->
            val message = pair.value
            if(compareDate(message.date) > 0L) {
                return true
            }
        }
        return false

    }

    fun updateLastReadDate(message: NewsletterMessage? = messages.toList().firstOrNull()?.second) {

        message?.let {
            setLastReadDate(it.date)
            compareDate()
        }

    }

    override fun toString(): String {

        val t = StringBuilder()

        for (pair in messages) {
            val message = pair.value
            t.append("\n[").append(message.title).append(" ").append(message.date).append(" ")
                .append(message.description).append("]")
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