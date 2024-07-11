package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.lifecycle.AndroidViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterInboxModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterMessageModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.RSSParserUtils
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

/*
class NewsletterViewModel(application: Application): /*AndroidViewModel(application)*/ SharedViewModel(application) {

    companion object {
        var KEY_INBOX_GENERAL: String? = null
        var KEY_INBOX_PET: String? = null
        var KEY_INBOX_PHASMOPHOBIA: String? = null

        const val MAX_MESSAGE_COUNT: Int = 10
    }

    private var inboxMessageList: ArrayList<NewsletterInboxModel?>? = null

    var requiresNotify = false

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
                for (inbox in inboxMessageList) {
                    if (inbox?.ready == false) {
                        isUpToDate = false
                        break
                    }
                } }

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

    init {
        //setFileName()

        //val sharedPref = getSharedPreferences(application)

        KEY_INBOX_GENERAL = application.getString(R.string.preference_newsletter_lastreaddate_general)
        KEY_INBOX_PET = application.getString(R.string.preference_newsletter_lastreaddate_pet)
        KEY_INBOX_PHASMOPHOBIA = application.getString(R.string.preference_newsletter_lastreaddate_phas)

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

        saveToFile(application)
    }

    fun registerInboxes(context: Context) {
        try {
            Log.d("MessageCenter", "Starting Phasmophobia inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_phasmophobia_changelog_link),
                InboxType.PHASMOPHOBIA, this
            )

            Log.d("MessageCenter", "Starting General inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_general_news_link),
                InboxType.GENERAL, this
            )

            Log.d("MessageCenter", "Starting PET inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_pet_changelog_link),
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

        requiresNotify = general || pet || phasmophobia
    }

    private fun saveLastReadDate(
        editor: SharedPreferences.Editor, localApply: Boolean, inboxType: InboxType) {

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
*/

class NewsletterViewModel(application: Application): SharedViewModel(application) {

    companion object {
        var KEY_INBOX_GENERAL: String? = null
        var KEY_INBOX_PET: String? = null
        var KEY_INBOX_PHASMOPHOBIA: String? = null

        const val MAX_MESSAGE_COUNT: Int = 10
    }

    private var inboxMessageList: ArrayList<NewsletterInboxModel?>? = null

    var requiresNotify = false

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
                for (inbox in inboxMessageList) {
                    if (inbox?.ready == false) {
                        isUpToDate = false
                        break
                    }
                } }

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
        fileName = R.string.preferences_newsletterFile_name
    }

    init {
        setFileName()

        val sharedPref = getSharedPreferences(application)

        KEY_INBOX_GENERAL = application.getString(R.string.preference_newsletter_lastreaddate_general)
        KEY_INBOX_PET = application.getString(R.string.preference_newsletter_lastreaddate_pet)
        KEY_INBOX_PHASMOPHOBIA = application.getString(R.string.preference_newsletter_lastreaddate_phas)

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

        saveToFile(application)
    }

    fun registerInboxes(context: Context) {
        try {
            Log.d("MessageCenter", "Starting Phasmophobia inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_phasmophobia_changelog_link),
                InboxType.PHASMOPHOBIA, this
            )

            Log.d("MessageCenter", "Starting General inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_general_news_link),
                InboxType.GENERAL, this
            )

            Log.d("MessageCenter", "Starting PET inbox")
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_pet_changelog_link),
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

        requiresNotify = general || pet || phasmophobia
    }

    private fun saveLastReadDate(
        editor: SharedPreferences.Editor, localApply: Boolean, inboxType: InboxType) {

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
