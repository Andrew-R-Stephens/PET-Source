package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.DrawableRes
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data.NewsletterMessageData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data.NewsletterMessagesData
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.RSSParserUtils
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory

class NewsletterViewModel : SharedViewModel() {
    private var requiresNotify = false

    var currentInboxType: InboxType = InboxType.GENERAL
    private var currentMessageID = 0
    private var inboxMessageList: ArrayList<NewsletterMessagesData?>? = null

    override fun setFileName() {
        fileName = R.string.preferences_newsletterFile_name
    }

    override fun init(context: Context): Boolean {
        setFileName()

        val sharedPref = getSharedPreferences(context)

        var inbox: NewsletterMessagesData? = getInbox(InboxType.GENERAL)
        if(inbox != null) {
            setLastReadDate(
                InboxType.GENERAL,
                sharedPref.getString(
                    context.getString(R.string.preference_newsletter_lastreaddate_general),
                    inbox.getLastReadDate()
                )
            )
        }

        inbox = getInbox(InboxType.PET)
        if (inbox != null) {
            setLastReadDate(
                InboxType.PET,
                sharedPref.getString(
                    context.getString(R.string.preference_newsletter_lastreaddate_pet),
                    inbox.getLastReadDate()
                )
            )
        }

        inbox = getInbox(InboxType.PHASMOPHOBIA)
        if (inbox != null) {
            setLastReadDate(
                InboxType.PHASMOPHOBIA,
                sharedPref.getString(
                    context.getString(R.string.preference_newsletter_lastreaddate_phas),
                    inbox.getLastReadDate()
                )
            )
        }

        saveToFile(context)

        return true
    }

    fun registerInboxes(context: Context) {
        try {
            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_phasmophobia_changelog_link),
                InboxType.PHASMOPHOBIA, this
            )

            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_general_news_link),
                InboxType.GENERAL, this
            )

            RSSParserUtils(
                XmlPullParserFactory.newInstance(),
                context.getString(R.string.preference_pet_changelog_link),
                InboxType.PET, this
            )
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        }
    }

    val isUpToDate: Boolean
        get() {
            if (inboxMessageList == null) {
                return false
            }

            var isUpToDate = true
            for (inbox in inboxMessageList!!) {
                if (!inbox!!.ready) {
                    isUpToDate = false
                    break
                }
            }
            return isUpToDate
        }

    fun addInbox(inbox: NewsletterMessagesData, type: InboxType?) {
        if (inboxMessageList == null) {
            inboxMessageList = ArrayList(10)
        }

        try {
            inbox.compareDates()
            inbox.ready = true
            inbox.inboxType = type

            inboxMessageList!!.add(inbox)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
        } catch (ex: ArrayIndexOutOfBoundsException) {
            ex.printStackTrace()
        }
    }

    val currentInbox: NewsletterMessagesData?
        get() {
            if (inboxMessageList == null) {
                return null
            }

            for (i in inboxMessageList!!.indices) {
                if (inboxMessageList!![i] != null &&
                    inboxMessageList!![i]!!.inboxType == currentInboxType
                ) {
                    return inboxMessageList!![i]
                }
            }

            return null
        }

    fun getInboxType(pos: Int): InboxType {
        return InboxType.entries[pos]
    }

    fun getInbox(inboxType: InboxType): NewsletterMessagesData? {
        if (inboxMessageList == null) {
            return null
        }

        val list = arrayOfNulls<NewsletterMessagesData>(
            inboxMessageList!!.size
        )
        inboxMessageList!!.toArray(list)

        for (i in list.indices) {
            if (list[i]!!.inboxType == inboxType) {
                return inboxMessageList!![i]
            }
        }

        return null
    }

    fun setCurrentMessageId(position: Int) {
        currentMessageID = position
    }

    val currentMessage: NewsletterMessageData?
        get() {
            if (currentInbox == null) {
                return null
            }
            return currentInbox!!.messages[currentMessageID]
        }

    fun setLastReadDate(inboxType: InboxType, date: String?) {
        if (getInbox(inboxType) == null) {
            return
        }

        getInbox(inboxType)!!.setLastReadDate(date)
    }

    fun getLastReadDate(inboxType: InboxType): String {
        if (getInbox(inboxType) == null) {
            return "NA"
        }

        return getInbox(inboxType)!!.getLastReadDate()
    }

    fun compareAllInboxDates() {
        val general = (getInbox(InboxType.GENERAL) != null &&
                getInbox(InboxType.GENERAL)!!.compareDates())
        val pet = (getInbox(InboxType.PET) != null &&
                getInbox(InboxType.PET)!!.compareDates())
        val phasmophobia = (getInbox(InboxType.PHASMOPHOBIA) != null &&
                getInbox(InboxType.PHASMOPHOBIA)!!.compareDates())

        requiresNotify = general || pet || phasmophobia
    }

    fun requiresNotify(): Boolean {
        return requiresNotify
    }

    /** @noinspection SameParameterValue
     */
    private fun saveLastReadDate(
        c: Context, editor: SharedPreferences.Editor?, localApply: Boolean,
        inboxType: InboxType
    ) {
        var editor = editor
        if (editor == null && (getEditor(c).also { editor = it }) == null) {
            return
        }

        var target = ""
        target = when (inboxType) {
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
        editor!!.putString(
            target,
            getLastReadDate(inboxType)
        )

        if (localApply) {
            editor!!.apply()
        }
    }

    /**
     * saveToFile method
     */
    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        saveLastReadDate(context, editor, false, InboxType.GENERAL)
        saveLastReadDate(context, editor, false, InboxType.PHASMOPHOBIA)
        saveLastReadDate(context, editor, false, InboxType.PET)

        editor!!.apply()

        Log.d("MessageCenter", "Saving all inboxes...")
    }

    /**
     * saveToFile method
     */
    fun saveToFile(context: Context, inboxType: InboxType) {
        val editor = getEditor(context)

        saveLastReadDate(context, editor, false, inboxType)

        editor!!.apply()

        Log.d("MessageCenter", "Saving [" + inboxType.name + "]...")
    }

    enum class InboxType(val id: Int) {
        GENERAL(0), PET(1), PHASMOPHOBIA(2);

        fun getName(context: Context): String {
            val name =
                context.resources.getStringArray(R.array.messagecenter_inboxtitles)

            return name[id]
        }

        fun getIcon(context: Context): Int {
            val typed_inbox_icons =
                context.resources.obtainTypedArray(R.array.messagecenter_inboxicons)

            @DrawableRes val icon = typed_inbox_icons.getResourceId(id, 0)
            typed_inbox_icons.recycle()

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
