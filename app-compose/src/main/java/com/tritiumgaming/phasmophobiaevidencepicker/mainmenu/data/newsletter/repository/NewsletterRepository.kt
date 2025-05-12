package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository

import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model.NewsletterService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsletterRepository(
    context: Context
) {
/*

    val flow: Flow<NewsletterPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }
*/

    private val _inboxes = MutableStateFlow(
        mapOf(
            Pair(
                NewsletterInboxType.InboxType.GENERAL,
                NewsletterInbox(
                    NewsletterInboxType.create(
                        context,
                        NewsletterInboxType.InboxType.GENERAL
                    )
                )
            ),
            Pair(
                NewsletterInboxType.InboxType.PET,
                NewsletterInbox(
                    NewsletterInboxType.create(
                        context,
                        NewsletterInboxType.InboxType.PET
                    )
                )
            ),
            Pair(
                NewsletterInboxType.InboxType.PHASMOPHOBIA,
                NewsletterInbox(
                    NewsletterInboxType.create(
                        context,
                        NewsletterInboxType.InboxType.PHASMOPHOBIA
                    )
                )
            )
        )
    )
    val inboxes = _inboxes.asStateFlow()

    private val _requiresNotify = MutableStateFlow<Boolean>(false)
    val requiresNotify = _requiresNotify.asStateFlow()
    fun requiresNotify(inboxType: NewsletterInboxType.InboxType) = getInbox(inboxType)?.requiresNotify
    fun setRequiresNotify(value: Boolean) {
        _requiresNotify.update { value }
    }

    init {
        Log.d("NewsletterRepository", "Initializing")

        CoroutineScope(context = Dispatchers.IO).launch {
            registerInboxes()
        }
    }

    suspend fun registerInboxes() {
        inboxes.value[NewsletterInboxType.InboxType.PHASMOPHOBIA]?.let {
            Log.d("MessageCenter", "Starting Phasmophobia inbox")
            NewsletterService(it).execute()
        }

        inboxes.value[NewsletterInboxType.InboxType.GENERAL]?.let {
            Log.d("MessageCenter", "Starting General inbox")
            NewsletterService(it).execute()
        }

        inboxes.value[NewsletterInboxType.InboxType.PET]?.let {
            Log.d("MessageCenter", "Starting PET inbox")
            NewsletterService(it).execute()
        }
    }

    fun getInbox(inboxType: NewsletterInboxType.InboxType): NewsletterInbox? {
        return inboxes.value[inboxType]
    }

    fun getLastReadDate(inboxType: NewsletterInboxType.InboxType): Long {
        return getInbox(inboxType)?.lastReadDate?.value ?: NewsletterInbox.Companion.formatToEpoch(
            null
        )
    }

    /*suspend fun fetchInitialPreferences() =
        mapPreferences(dataStore.data.first().toPreferences())

    private fun mapPreferences(preferences: Preferences): NewsletterPreferences {
        return NewsletterPreferences(
            preferences[KEY_INBOX_GENERAL] ?: DATE_DEFAULT,
            preferences[KEY_INBOX_PET] ?: DATE_DEFAULT,
            preferences[KEY_INBOX_PHASMOPHOBIA] ?: DATE_DEFAULT
        )
    }

    companion object PreferenceKeys {

        lateinit var KEY_INBOX_GENERAL: Preferences.Key<Long>
        lateinit var KEY_INBOX_PET: Preferences.Key<Long>
        lateinit var KEY_INBOX_PHASMOPHOBIA: Preferences.Key<Long>

        const val DATE_DEFAULT = 0L
    }*/

    class NewsletterInboxType(
        val id: Int,
        val title: Int,
        val url: String,
        @DrawableRes val icon: Int,
        val inboxType: InboxType? = null
    ) {

        enum class InboxType(
            val id: Int,
            @StringRes val title: Int,
            @StringRes val url: Int,
            @DrawableRes val icon: Int
        ) {
            GENERAL(
                id = 0,
                title = R.string.messagecenter_inbox_general,
                url = R.string.preference_general_news_link,
                icon = R.drawable.ic_news
            ),
            PET(
                id = 1,
                title = R.string.messagecenter_inbox_pet,
                url = R.string.preference_pet_changelog_link,
                icon = R.drawable.icon_logo_app
            ),
            PHASMOPHOBIA(
                id = 2,
                title = R.string.messagecenter_inbox_phas,
                url = R.string.preference_phasmophobia_changelog_link,
                icon = R.drawable.icon_logo_phas
            )
        }

        companion object  {

            fun create(
                context: Context,
                inboxType: InboxType
            ): NewsletterInboxType {

                return NewsletterInboxType(
                    id = inboxType.id,
                    title = inboxType.title,
                    url = context.getString(inboxType.url),
                    icon = inboxType.icon,
                    inboxType = inboxType
                )

            }

            fun getInbox(id: Int): InboxType? {
                return InboxType.entries.firstOrNull { it.id == id }
            }

        }

        enum class NewsletterInboxDefaultType(
            val id: Int,
            val title: Int,
            val url: String,
            @DrawableRes val icon: Int
        ) {
            Default(0, 0, "", 0);

            fun asInboxType(): NewsletterInboxType {
                return NewsletterInboxType(id, title, url, icon)
            }
        }

    }

}