package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class FlattenedNewsletterInboxDto(
    val id: String? = null,
    @StringRes val title: Int? = null,
    val url: String? = null,
    @DrawableRes val icon: Int? = null,
    var channel: FlattenedNewsletterChannelDto? = null
)

data class FlattenedNewsletterChannelDto(
    val language: String? = null,
    val messages: List<FlattenedNewsletterMessageDto>? = null
)

data class FlattenedNewsletterMessageDto(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val date: Long? = null
)
