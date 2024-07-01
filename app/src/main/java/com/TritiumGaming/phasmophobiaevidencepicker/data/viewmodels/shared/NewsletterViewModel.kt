package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.DrawableRes
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterMessageListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterMessageListModel.Companion.formatFromEpoch
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterMessageListModel.Companion.formatToEpoch
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.news.NewsletterMessageModel
import com.TritiumGaming.phasmophobiaevidencepicker.utils.RSSParserUtils
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

class NewsletterViewModel : SharedViewModel() {
    private var requiresNotify = false

    var currentInboxType: InboxType = InboxType.GENERAL
    private var currentMessageID = 0
    private var inboxMessageList: ArrayList<NewsletterMessageListModel?>? = null

    override fun setFileName() {
        fileName = R.string.preferences_newsletterFile_name
    }

    override fun init(context: Context): Boolean {
        setFileName()

        val sharedPref = getSharedPreferences(context)

        getInbox(InboxType.GENERAL)?.let{ inbox ->
            setLastReadDate(InboxType.GENERAL, sharedPref.getString(
                    context.getString(R.string.preference_newsletter_lastreaddate_general), formatFromEpoch(inbox.lastReadDate))
            )
        }

        getInbox(InboxType.PET)?.let { inbox ->
            setLastReadDate(InboxType.PET, sharedPref.getString(
                    context.getString(R.string.preference_newsletter_lastreaddate_pet), formatFromEpoch(inbox.lastReadDate))
            )
        }

        getInbox(InboxType.PHASMOPHOBIA)?.let { inbox ->
            setLastReadDate(InboxType.PHASMOPHOBIA, sharedPref.getString(
                    context.getString(R.string.preference_newsletter_lastreaddate_phas), formatFromEpoch(inbox.lastReadDate))
            )
        }

        saveToFile(context)

        return true
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

    fun addInbox(inbox: NewsletterMessageListModel, type: InboxType?) {
        inboxMessageList = inboxMessageList ?: ArrayList(10)

        try {
            inbox.compareDates()
            inbox.ready = true
            inbox.inboxType = type

            inboxMessageList?.add(inbox)
        }
        catch (ex: NullPointerException) { ex.printStackTrace() }
        catch (ex: ArrayIndexOutOfBoundsException) { ex.printStackTrace() }
    }

    val currentInbox: NewsletterMessageListModel?
        get() {
            inboxMessageList?.let { inboxMessageList ->
                for (i in inboxMessageList.indices) {
                    if (inboxMessageList[i]?.inboxType == currentInboxType) {
                        return inboxMessageList[i]
                    }
                }
            }
            return null
        }

    fun getInboxType(pos: Int): InboxType {
        return InboxType.entries[pos]
    }

    fun getInbox(inboxType: InboxType): NewsletterMessageListModel? {
        if (inboxMessageList == null) {
            return null
        }

        val list = arrayOfNulls<NewsletterMessageListModel>(inboxMessageList?.size ?: 0)
        inboxMessageList?.toArray(list)

        list.forEachIndexed { index, listItem ->
            if (listItem?.inboxType == inboxType) {
                return inboxMessageList?.get(index)
            }
        }

        return null
    }

    fun setCurrentMessageId(position: Int) {
        currentMessageID = position
    }

    val currentMessage: NewsletterMessageModel?
        get() { return currentInbox?.messages?.get(currentMessageID) }

    private fun setLastReadDate(inboxType: InboxType, date: String?) {
        getInbox(inboxType)?.lastReadDate = formatToEpoch(date)
    }

    private fun getLastReadDate(inboxType: InboxType): String? {
        return formatFromEpoch(getInbox(inboxType)?.lastReadDate)
    }

    fun compareAllInboxDates() {
        val general = (getInbox(InboxType.GENERAL)?.compareDates() ?: 0) > 0
        val pet = (getInbox(InboxType.PET)?.compareDates() ?: 0) > 0
        val phasmophobia = (getInbox(InboxType.PHASMOPHOBIA)?.compareDates() ?: 0) > 0

        requiresNotify = general || pet || phasmophobia
    }

    fun requiresNotify(): Boolean {
        return requiresNotify
    }

    /** @noinspection SameParameterValue
     */
    private fun saveLastReadDate(
        c: Context, editor: SharedPreferences.Editor?, localApply: Boolean, inboxType: InboxType) {

        val target: String = when (inboxType) {
            InboxType.PET -> c.resources.getString(
                R.string.preference_newsletter_lastreaddate_pet
            )
            InboxType.PHASMOPHOBIA -> c.resources.getString(
                R.string.preference_newsletter_lastreaddate_phas
            )
            InboxType.GENERAL -> c.resources.getString(
                R.string.preference_newsletter_lastreaddate_general
            )
        }
        editor?.putString(target, getLastReadDate(inboxType))

        if (localApply) { editor?.apply() }
    }

    /**
     * saveToFile method
     */
    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        saveLastReadDate(context, editor, false, InboxType.GENERAL)
        saveLastReadDate(context, editor, false, InboxType.PHASMOPHOBIA)
        saveLastReadDate(context, editor, false, InboxType.PET)

        editor.apply()

        Log.d("MessageCenter", "Saving all inboxes...")
    }

    /**
     * saveToFile method
     */
    fun saveToFile(context: Context, inboxType: InboxType) {
        val editor = getEditor(context)

        saveLastReadDate(context, editor, false, inboxType)

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

        for (data in inboxMessageList!!) {
            stringBuilder.append(data).append("\n")
        }

        return stringBuilder.toString()
    }
}
