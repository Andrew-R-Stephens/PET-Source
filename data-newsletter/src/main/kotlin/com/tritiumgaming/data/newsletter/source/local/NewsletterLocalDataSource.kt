package com.tritiumgaming.data.newsletter.source.local

interface NewsletterLocalDataSource {

    fun fetchInboxes(): Result<List<com.tritiumgaming.data.newsletter.dto.local.LocalNewsletterInboxDto>>

}