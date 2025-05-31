package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.dto

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class LocalNewsletterInboxDto(
    val id: String,
    @StringRes val title: Int,
    val url: String,
    @DrawableRes val icon: Int
)