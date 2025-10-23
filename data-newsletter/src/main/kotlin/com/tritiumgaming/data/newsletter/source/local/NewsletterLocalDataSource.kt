package com.tritiumgaming.data.newsletter.source.local

import com.tritiumgaming.data.newsletter.dto.local.LocalNewsletterInboxDto

interface NewsletterLocalDataSource {

    fun fetchInboxes(): Result<List<LocalNewsletterInboxDto>>

}