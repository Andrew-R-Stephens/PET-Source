package com.tritiumgaming.phasmophobiaevidencepicker.util

import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news.NewsletterInbox
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.net.URL

@Deprecated("Replaced by NewsletterService")
class RSSParserUtils(
    factory: XmlPullParserFactory,
    private val inboxType: NewsletterInboxType?,
    val addInbox: (NewsletterInbox, NewsletterInboxType) -> Unit
) {

    init {
        Log.d("MessageCenter", "RSSThread initializing...")
        inboxType?.let {
            Thread(RSSThread(factory, it.url)).start()
        }
    }

    private fun getInputStream(urlStr: String?): InputStream? {
        try {
            val url = URL(urlStr)
            return url.openConnection().getInputStream()
        }
        catch (e: IOException) { e.printStackTrace() }
        return null
    }

    inner class RSSThread(
        private val factory: XmlPullParserFactory,
        private val urlStr: String?
    ) : Runnable {

        init {
            factory.isNamespaceAware = false
            Log.d("RSSThread", "$urlStr")
        }

        override fun run() {
            Log.d("MessageCenter", "Starting RSSThread parse.")
            try {
                /*val inStream = getInputStream(urlStr)
                    ?: throw IOException("RSSParser: InputStream is null! Cannot retrieve " +
                            "database info. Is the internet connected?")
                val xpp = factory.newPullParser()
                xpp.setInput(inStream, "UTF_8")

                var insideItem = false
                var eventType = xpp.eventType

                val messageList = NewsletterInbox()
                var title = String()
                var date = String()
                var description = String()

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when(eventType) {
                        START_TAG -> {
                            val elementName = xpp.name.lowercase(Locale.getDefault())
                            when (elementName) {
                                "item" -> { insideItem = true }
                                "title" -> { if (insideItem) { title = xpp.nextText() } }
                                "description" -> { if (insideItem) { description = xpp.nextText() } }
                                "pubdate" -> { if (insideItem) { date = xpp.nextText() } }
                            }
                        }
                        END_TAG -> {
                            if (xpp.name.equals("item", ignoreCase = true)) {
                                insideItem = false
                                val message = NewsletterMessage(title, description, date)
                                //Log.d("MessageCenter", "New Message: $message")
                                messageList.addMessage(message)
                                //if (message.hasContent()) { messageList.add(message) }
                            }
                        }
                    }
                    eventType = xpp.next()
                }
                inStream.close()

                inboxType?.let {
                    addInbox(messageList, it)
                    Log.d("MessageCenter", "Adding $inboxType inbox")
                }*/
            }
            catch (e: XmlPullParserException) { e.printStackTrace() }
            catch (e: IOException) { e.printStackTrace() }
        }
    }
}
