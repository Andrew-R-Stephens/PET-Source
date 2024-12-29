package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.news.NewsletterInboxModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.news.NewsletterMessageModel
import com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.datastore.repository.NewsletterRepository
import kotlinx.coroutines.launch

class NewsletterViewModel(
    private val newsletterRepository: NewsletterRepository
): ViewModel() {

    val inboxMessageList: ArrayList<NewsletterInboxModel?>
        get() = newsletterRepository.inboxMessageList

    var requiresNotify = newsletterRepository.requiresNotify

    val currentInbox: NewsletterInboxModel?
        get() {
            inboxMessageList.let { inboxMessageList ->
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

            var isUpToDate = true
            inboxMessageList.let { inboxMessageList ->
                for (inbox in inboxMessageList) {
                    if (inbox?.ready == false) {
                        isUpToDate = false
                        break
                    }
                } }

            return isUpToDate
        }

    init {
        viewModelScope.launch {
            newsletterRepository.registerInboxes()
        }
    }

    /*
    fun init(context: Context) {
        setFileName()

        val sharedPref = getSharedPreferences(context)

        KEY_INBOX_GENERAL = context.getString(R.string.preference_newsletter_lastreaddate_general)
        KEY_INBOX_PET = context.getString(R.string.preference_newsletter_lastreaddate_pet)
        KEY_INBOX_PHASMOPHOBIA = context.getString(R.string.preference_newsletter_lastreaddate_phas)

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

    fun init(context: Context) {

        val sharedPref = getSharedPreferences(context)

        KEY_INBOX_GENERAL = context.getString(R.string.preference_newsletter_lastreaddate_general)
        KEY_INBOX_PET = context.getString(R.string.preference_newsletter_lastreaddate_pet)
        KEY_INBOX_PHASMOPHOBIA = context.getString(R.string.preference_newsletter_lastreaddate_phas)

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

        //saveToFile(context)
    }
    */

    /*
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
    */

    /*
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
    */

    fun getInbox(inboxType: InboxType): NewsletterInboxModel? {

        val list = arrayOfNulls<NewsletterInboxModel>(inboxMessageList.size)
        inboxMessageList.toArray(list)

        list.forEachIndexed { index, listItem ->
            if (listItem?.inboxType == inboxType) {
                return inboxMessageList[index]
            }
        }

        return null
    }

    /*
    private fun getLastReadDate(inboxType: InboxType): Long? {
        return getInbox(inboxType)?.lastReadDate
    }
    */

    /*
    private fun setLastReadDate(inboxType: InboxType, time: Long) {
        getInbox(inboxType)?.lastReadDate = time
    }
    */

    /*
    fun compareAllInboxDates() {
        val general = getInbox(InboxType.GENERAL)?.compareDates() ?: false
        val pet = getInbox(InboxType.PET)?.compareDates() ?: false
        val phasmophobia = getInbox(InboxType.PHASMOPHOBIA)?.compareDates() ?: false

        requiresNotify = general || pet || phasmophobia
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
    */

    /*
    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        saveLastReadDate(editor, false, InboxType.GENERAL)
        saveLastReadDate(editor, false, InboxType.PHASMOPHOBIA)
        saveLastReadDate(editor, false, InboxType.PET)

        editor.apply()

        Log.d("MessageCenter", "Saving all inboxes...")
    }
    */

    /*
    fun saveToFile(context: Context, inboxType: InboxType) {
        val editor = getEditor(context)

        saveLastReadDate(editor, false, inboxType)

        editor.apply()

        Log.d("MessageCenter", "Saving [" + inboxType.name + "]...")
    }
    */

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

        inboxMessageList.let { inboxMessageList ->
            for (data in inboxMessageList) { stringBuilder.append(data).append("\n") }
        }

        return stringBuilder.toString()
    }

    class NewsletterFactory(
        private val newsletterRepository: NewsletterRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsletterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsletterViewModel(
                    newsletterRepository
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                //val savedStateHandle = createSavedStateHandle()
                val myRepository = (this[APPLICATION_KEY] as PETApplication).container.newsletterRepository
                NewsletterViewModel(
                    newsletterRepository = myRepository,
                    /*savedStateHandle = savedStateHandle*/
                )
            }
        }
    }
}
