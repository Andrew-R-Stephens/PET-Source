package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model

data class NewsletterMessage(
    val id: String,
    val title: String? = "",
    val description: String? = "",
    val dateEpoch: Long = 1L,
    val dateFormatted: String? = ""
) {

    /** @return evaluates the age of one message compared to another
     * 1 if specifiedDate is newer than the lastReadDate,
     * -1 if specifiedDate is older than the lastReadDate,
     * 0 if specifiedDate is ths same age as lastReadDate **/
    fun compareDate(lastReadDate: Long): Int {
        return (dateEpoch - lastReadDate).coerceIn(-1L, 1L).toInt()
    }

}
