package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.local.dto.LocalNewsletterInboxDto
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.source.NewsletterLocalDataSource

class NewsletterLocalDataSource(
    private val applicationContext: Context
): NewsletterLocalDataSource {

    override fun fetchInboxes(): List<LocalNewsletterInboxDto> {
        val resources = applicationContext.resources

        val inboxes: MutableList<LocalNewsletterInboxDto> = mutableListOf()

        val newslettersTypedArray = resources.obtainTypedArray(R.array.newsletters_array)
        val idKey = 0
        val titleKey = 1
        val urlKey = 2
        val iconKey = 3
        for (i in 0 until newslettersTypedArray.length()) {

            val newsletterTypedArrayID = newslettersTypedArray.getResourceId(i, 0)
            val newsletterTypedArray = resources.obtainTypedArray(newsletterTypedArrayID)

            val id = newsletterTypedArray.getResourceId(idKey, 0)
            val title = newsletterTypedArray.getResourceId(titleKey, 0)
            val url = newsletterTypedArray.getResourceId(urlKey, 0)
            val icon = newsletterTypedArray.getResourceId(iconKey, 0)

            inboxes.add(
               LocalNewsletterInboxDto(
                   id = resources.getString(id),
                   title = title,
                   url = resources.getString(url),
                   icon = icon
               )
            )

            newsletterTypedArray.recycle()

        }
        newslettersTypedArray.recycle()

        return inboxes
    }

}