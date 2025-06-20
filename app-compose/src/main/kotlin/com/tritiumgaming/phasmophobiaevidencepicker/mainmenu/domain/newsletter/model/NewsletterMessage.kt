package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model

class NewsletterMessage(
    val id: String,
    val title: String? = "",
    val description: String? = "",
    val dateEpoch: Long = 1L,
    val dateFormatted: String? = ""
)
