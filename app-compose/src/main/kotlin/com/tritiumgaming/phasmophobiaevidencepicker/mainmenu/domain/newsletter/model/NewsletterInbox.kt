package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterTitle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class NewsletterInbox(
    val id: String?,
    val title: NewsletterTitle?,
    val url: String?,
    val icon: NewsletterIcon?,
    channel: NewsletterChannel? = null
) {
    val channel: NewsletterChannel = channel ?: NewsletterChannel()

    private val _inboxNotificationState = MutableStateFlow(true)
    val inboxNotificationState = _inboxNotificationState.asStateFlow()
    private fun updateRequiresNotify() {
        _inboxNotificationState.update { compareDates() }
    }

    private val _inboxLastReadDate = MutableStateFlow(formatToEpoch(null))
    val inboxLastReadDate = _inboxLastReadDate.asStateFlow()
    fun setInboxLastReadDate(value: Long) {
        _inboxLastReadDate.update { value.coerceAtLeast(inboxLastReadDate.value) }

        updateRequiresNotify()
    }

    /** @return evaluates the age of one message compared to another
     * 1 if specifiedDate is newer than the lastReadDate,
     * -1 if specifiedDate is older than the lastReadDate,
     * 0 if specifiedDate is ths same age as lastReadDate **/
    fun compareDate(specifiedDate: Long = inboxLastReadDate.value): Int {
        return (specifiedDate - inboxLastReadDate.value).coerceIn(-1L, 1L).toInt()
    }

    fun compareDates(): Boolean {

        channel.messages.value.forEach { message ->
            if(compareDate(message.date) > 0L) {
                return true
            }
        }
        return false

    }

    override fun toString(): String {

        val t = StringBuilder()

        channel.messages.value.forEach { message ->
            t.append("\n[").append(message.title).append(" ").append(message.date).append(" ")
                .append(message.description).append("]")
        }
        return t.toString()

    }
}

fun formatToEpoch(stringDate: String?): Long {

    stringDate ?: return 1L

    val dateFormatter = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.ENGLISH)
    var time = 1L
    try {
        time = dateFormatter.parse(stringDate)?.time ?: time
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return time

}
