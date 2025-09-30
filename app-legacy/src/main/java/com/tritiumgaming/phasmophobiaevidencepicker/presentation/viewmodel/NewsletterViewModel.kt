package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.DrawableRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInboxModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterMessageModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.RSSParserUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

class NewsletterViewModel(application: Application): SharedViewModel(application) {

    companion object {
        var KEY_INBOX_GENERAL: String? = null
        var KEY_INBOX_PET: String? = null
        var KEY_INBOX_PHASMOPHOBIA: String? = null

        const val MAX_MESSAGE_COUNT: Int = 10
    }

    private var inboxMessageList: ArrayList<NewsletterInboxModel?>? = null

    private var _requiresNotify = MutableStateFlow(false)
    val requiresNotify = _requiresNotify.asStateFlow()

    val currentInbox: NewsletterInboxModel?
        get() {
            inboxMessageList?.let { inboxMessageList ->
                for (i in inboxMessageList.indices) {
                    if (inboxMessageList[i]?.inboxType == currentInboxType) {
                        return inboxMessageList[i] }
                } }
            return null
        }
    var currentInboxType: InboxType = InboxType.GENERAL

    var currentMessageIndex: Int? = null
    val currentMessage: NewsletterMessageModel?
        get() { return currentMessageIndex?.let { id -> currentInbox?.messages?.get(id) } }

    val isUpToDate: Boolean
        get() {
            if(inboxMessageList == null) { return false }

            var isUpToDate = true
            inboxMessageList?.let { inboxMessageList ->
                try {
                    for (inbox in inboxMessageList) {
                        if (inbox?.ready == false) {
                            isUpToDate = false
                            break
                        }
                    }
                } catch (e: ConcurrentModificationException) {
                    e.printStackTrace()
                    isUpToDate = false
                }
            }

            return isUpToDate
        }

    fun addInbox(inbox: NewsletterInboxModel, type: InboxType?) {
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

    override fun setFileName() {
        fileName = com.tritiumgaming.core.resources.R.string.preferences_newsletterFile_name
    }

    fun init(context: Context) {
        setFileName()

        val sharedPref = getSharedPreferences(context)

        KEY_INBOX_GENERAL = context.getString(com.tritiumgaming.core.resources.R.string.preference_newsletter_lastreaddate_general)
        KEY_INBOX_PET = context.getString(com.tritiumgaming.core.resources.R.string.preference_newsletter_lastreaddate_pet)
        KEY_INBOX_PHASMOPHOBIA = context.getString(com.tritiumgaming.core.resources.R.string.preference_newsletter_lastreaddate_phas)

        getInbox(InboxType.GENERAL)?.let{ inbox ->
            val date = sharedPref.getLong(KEY_INBOX_GENERAL, inbox.lastReadDate)
            setLastReadDate(InboxType.GENERAL, date)
        }

        getInbox(InboxType.PET)?.let { inbox ->
            val date = sharedPref.getLong(KEY_INBOX_PET, inbox.lastReadDate)
            setLastReadDate(InboxType.PET, date)
        }

        getInbox(InboxType.PHASMOPHOBIA)?.let { inbox ->
            val date = sharedPref.getLong(KEY_INBOX_PHASMOPHOBIA, inbox.lastReadDate)
            setLastReadDate(InboxType.PHASMOPHOBIA, date)
        }

        saveToFile(context)
    }

    fun registerInboxes(context: Context) {
        try {
            Log.d("MessageCenter", "Starting Phasmophobia inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(com.tritiumgaming.core.resources.R.string.link_news_phasmophobiaOfficial),
                InboxType.PHASMOPHOBIA, this
            )

            Log.d("MessageCenter", "Starting General inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(com.tritiumgaming.core.resources.R.string.link_news_general),
                InboxType.GENERAL, this
            )

            Log.d("MessageCenter", "Starting PET inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(com.tritiumgaming.core.resources.R.string.link_news_changelog),
                InboxType.PET, this
            )
        } catch (e: XmlPullParserException) {
            Log.d("MessageCenter", "Failed registering inboxes")
            e.printStackTrace() }
    }

    fun getInbox(inboxType: InboxType): NewsletterInboxModel? {
        if (inboxMessageList == null) {
            return null
        }

        val list = arrayOfNulls<NewsletterInboxModel>(inboxMessageList?.size ?: 0)
        inboxMessageList?.toArray(list)

        list.forEachIndexed { index, listItem ->
            if (listItem?.inboxType == inboxType) {
                return inboxMessageList?.get(index)
            }
        }

        return null
    }

    private fun getLastReadDate(inboxType: InboxType): Long? {
        return getInbox(inboxType)?.lastReadDate
    }

    private fun setLastReadDate(inboxType: InboxType, time: Long) {
        getInbox(inboxType)?.lastReadDate = time
    }

    fun compareAllInboxDates() {
        val general = getInbox(InboxType.GENERAL)?.compareDates() ?: false
        val pet = getInbox(InboxType.PET)?.compareDates() ?: false
        val phasmophobia = getInbox(InboxType.PHASMOPHOBIA)?.compareDates() ?: false

        _requiresNotify.update { general || pet || phasmophobia }
    }

    private fun saveLastReadDate(
        editor: SharedPreferences.Editor, localApply: Boolean, inboxType: InboxType
    ) {

        val target: String? = when (inboxType) {
            InboxType.PET -> KEY_INBOX_PET
            InboxType.PHASMOPHOBIA -> KEY_INBOX_PHASMOPHOBIA
            InboxType.GENERAL -> KEY_INBOX_GENERAL
        }
        getLastReadDate(inboxType)?.let { epochTime ->
            target?.let { target ->
                save(target, epochTime, editor) } }

        if (localApply) { editor.apply() }
    }

    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        saveLastReadDate(editor, false, InboxType.GENERAL)
        saveLastReadDate(editor, false, InboxType.PHASMOPHOBIA)
        saveLastReadDate(editor, false, InboxType.PET)

        editor.apply()

        Log.d("MessageCenter", "Saving all inboxes...")
    }

    fun saveToFile(context: Context, inboxType: InboxType) {
        val editor = getEditor(context)

        saveLastReadDate(editor, false, inboxType)

        editor.apply()

        Log.d("MessageCenter", "Saving [" + inboxType.name + "]...")
    }

    enum class InboxType(val id: Int) {
        GENERAL(0), PET(1), PHASMOPHOBIA(2);

        fun getName(context: Context): String {
            val name = context.resources.getStringArray(R.array.messagecenter_inboxtitles)

            return name[id]
        }

        fun getIcon(context: Context): Int {
            val typedInboxIcons =
                context.resources.obtainTypedArray(R.array.messagecenter_inboxicons)

            @DrawableRes val icon = typedInboxIcons.getResourceId(id, 0)
            typedInboxIcons.recycle()

            return icon
        }
    }

    override fun toString(): String {
        val stringBuilder = StringBuilder()

        inboxMessageList?.let { inboxMessageList ->
            for (data in inboxMessageList) { stringBuilder.append(data).append("\n") }
        }

        return stringBuilder.toString()
    }
}
