package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterIcon
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.mapper.NewsletterResources.NewsletterTitle

data class NewsletterInbox(
    val id: String? = "",
    val title: NewsletterTitle = NewsletterTitle.GENERAL_NEWS,
    val url: String? = "",
    val icon: NewsletterIcon = NewsletterIcon.GENERAL_NEWS,
    val channel: NewsletterChannel? = null
) {

    /** @return evaluates the age of one message compared to another
     * 1 if specifiedDate is newer than the lastReadDate,
     * -1 if specifiedDate is older than the lastReadDate,
     * 0 if specifiedDate is ths same age as lastReadDate **/
    fun compareDate(checkDate: Long, lastReadDate: Long): Int {
        return (checkDate - lastReadDate).coerceIn(-1L, 1L).toInt()
    }

    fun compareDates(lastReadDate: Long): Boolean {

        channel ?: return false

        channel.messages.forEach { message ->
            if(message.compareDate(lastReadDate) > 0L) {
                return true
            }
        }
        return false

    }

    override fun toString(): String {

        channel ?: return "No channels in Inbox"

        val t = StringBuilder()

        channel.messages.forEach { message ->
            t.append("\n[").append(message.title).append(" ").append(message.dateFormatted).append(" ")
                .append(message.description).append("]")
        }
        return t.toString()

    }
}
