package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.news.NewsletterInboxModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.NewsletterViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.NewsletterViewModel.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.utils.RSSParserUtils
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

class NewsletterRepository(
    private val dataStore: DataStore<Preferences>,
    context: Context
) {

    suspend fun getPreferences() = dataStore.data.first().toPreferences()

    var inboxMessageList: ArrayList<NewsletterInboxModel?> = ArrayList(0)
    var requiresNotify = false

    companion object {
        lateinit var URL_INBOX_GENERAL: String
        lateinit var URL_INBOX_PET: String
        lateinit var URL_INBOX_PHASMOPHOBIA: String

        lateinit var KEY_INBOX_GENERAL: Preferences.Key<Long>
        lateinit var KEY_INBOX_PET: Preferences.Key<Long>
        lateinit var KEY_INBOX_PHASMOPHOBIA: Preferences.Key<Long>

        const val MAX_MESSAGE_COUNT: Int = 10
    }

    init {
        URL_INBOX_GENERAL = context.getString(R.string.preference_general_news_link)
        URL_INBOX_PET = context.getString(R.string.preference_pet_changelog_link)
        URL_INBOX_PHASMOPHOBIA = context.getString(R.string.preference_phasmophobia_changelog_link)

        KEY_INBOX_GENERAL = longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_general))
        KEY_INBOX_PET = longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_pet))
        KEY_INBOX_PHASMOPHOBIA = longPreferencesKey(context.getString(R.string.preference_newsletter_lastreaddate_phas))
    }

    fun loadAll() {
        dataStore.data.map { preferences ->
            setLastReadDate(InboxType.GENERAL, preferences[KEY_INBOX_GENERAL] ?: 0L)
            setLastReadDate(InboxType.PET, preferences[KEY_INBOX_PET] ?: 0L)
            setLastReadDate(InboxType.PHASMOPHOBIA, preferences[KEY_INBOX_PHASMOPHOBIA] ?: 0L)
        }
    }

    suspend fun saveAll() {
        dataStore.edit { preferences ->
            preferences[KEY_INBOX_GENERAL] = getLastReadDate(InboxType.GENERAL) ?: 0L
            preferences[KEY_INBOX_PET] = getLastReadDate(InboxType.PET) ?: 0L
            preferences[KEY_INBOX_PHASMOPHOBIA] = getLastReadDate(InboxType.PHASMOPHOBIA) ?: 0L
        }
    }

    suspend fun registerInboxes() {
        try {
            Log.d("MessageCenter", "Starting Phasmophobia inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                URL_INBOX_PHASMOPHOBIA,
                InboxType.PHASMOPHOBIA,
                ::addInbox
            )

            Log.d("MessageCenter", "Starting General inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                URL_INBOX_GENERAL,
                InboxType.GENERAL,
                ::addInbox
            )

            Log.d("MessageCenter", "Starting PET inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                URL_INBOX_PET,
                InboxType.PET,
                ::addInbox
            )
        } catch (e: XmlPullParserException) {
            Log.d("MessageCenter", "Failed registering inboxes")
            e.printStackTrace() }
    }

    private fun addInbox(inbox: NewsletterInboxModel, type: InboxType?) {
        inboxMessageList = inboxMessageList ?: ArrayList(MAX_MESSAGE_COUNT)

        try {
            inbox.compareDate()
            inbox.ready = true
            inbox.inboxType = type

            inboxMessageList?.add(inbox)
        }
        catch (ex: NullPointerException) { ex.printStackTrace() }
        catch (ex: ArrayIndexOutOfBoundsException) { ex.printStackTrace() }
    }

    private fun getInbox(inboxType: InboxType): NewsletterInboxModel? {

        val list = arrayOfNulls<NewsletterInboxModel>(inboxMessageList.size)
        inboxMessageList.toArray(list)

        list.forEachIndexed { index, listItem ->
            if (listItem?.inboxType == inboxType) {
                return inboxMessageList[index]
            }
        }

        return null
    }

    private fun setLastReadDate(inboxType: InboxType, time: Long) {
        getInbox(inboxType)?.lastReadDate = time
    }

    private fun getLastReadDate(inboxType: InboxType): Long? {
        return getInbox(inboxType)?.lastReadDate
    }

    fun compareAllInboxDates() {
        val general = getInbox(InboxType.GENERAL)?.compareDates() ?: false
        val pet = getInbox(InboxType.PET)?.compareDates() ?: false
        val phasmophobia = getInbox(InboxType.PHASMOPHOBIA)?.compareDates() ?: false

        requiresNotify = general || pet || phasmophobia
    }

}
