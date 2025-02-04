package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType.GENERAL
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType.PET
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType.PHASMOPHOBIA
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class NewsletterRepository(
    val dataStore: DataStore<Preferences>,
    context: Context
) {

    val flow: Flow<NewsletterPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException) { emit(emptyPreferences()) }
            else { throw exception }
        }
        .map { preferences ->
            mapPreferences(preferences)
        }

    private val _inboxes = MutableStateFlow(
        mapOf(
            Pair(GENERAL, NewsletterInbox(NewsletterInboxType.create(context, GENERAL))),
            Pair(PET, NewsletterInbox(NewsletterInboxType.create(context, PET))),
            Pair(PHASMOPHOBIA, NewsletterInbox(NewsletterInboxType.create(context, PHASMOPHOBIA)))
        )
    )
    val inboxes = _inboxes.asStateFlow()

    suspend fun setLastReadDateGeneral(date: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_INBOX_GENERAL] = date
        }
    }

    suspend fun setLastReadDatePET(date: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_INBOX_PET] = date
        }
    }

    suspend fun setLastReadDatePhasmophobia(date: Long) {
        dataStore.edit { preferences ->
            preferences[KEY_INBOX_PHASMOPHOBIA] = date
        }
    }

    init {
        Log.d("Newsletter Repository", "Initializing")

        KEY_INBOX_GENERAL = longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_general))
        KEY_INBOX_PET = longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_pet))
        KEY_INBOX_PHASMOPHOBIA = longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_phas))

        CoroutineScope(context = Dispatchers.IO).launch {
            registerInboxes()
        }
    }

    //var requiresNotify = false

    suspend fun registerInboxes() {
        Log.d("MessageCenter", "Starting Phasmophobia inbox")
        inboxes.value[PHASMOPHOBIA]?.let {
            NewsletterService(it).execute()
        }

        Log.d("MessageCenter", "Starting General inbox")
        inboxes.value[GENERAL]?.let {
            NewsletterService(it).execute()
        }

        Log.d("MessageCenter", "Starting PET inbox")
        inboxes.value[PET]?.let {
            NewsletterService(it).execute()
        }
    }

    private fun getInbox(inboxType: InboxType): NewsletterInbox? {
        return inboxes.value[inboxType]
    }

    private fun setInboxLastReadDate(inboxType: InboxType, time: Long) {
        getInbox(inboxType)?.lastReadDate = time
    }

    private fun setInboxLastReadDate(inboxType: InboxType): Long? {
        return getInbox(inboxType)?.lastReadDate
    }

    fun compareAllInboxDates() {
        val general = getInbox(GENERAL)?.compareDates()
        val pet = getInbox(PET)?.compareDates()
        val phasmophobia = getInbox(PHASMOPHOBIA)?.compareDates()

        //requiresNotify = general || pet || phasmophobia
    }

    suspend fun fetchInitialPreferences() =
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
    }

    data class NewsletterPreferences(
        val lastReadDateGeneral: Long,
        val lastReadDatePET: Long,
        val lastReadDatePhasmophobia: Long
    )

    class NewsletterInboxType(
        val id: Int,
        val title: String,
        val url: String,
        @DrawableRes val icon: Int
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

        companion object NewsletterInboxFactory{

            fun create(
                context: Context,
                inboxType: InboxType
            ): NewsletterInboxType {
                return NewsletterInboxType(
                    id = inboxType.id,
                    title = context.getString(inboxType.title),
                    url = context.getString(inboxType.url),
                    icon = inboxType.icon,
                )
            }

        }

    }

}
