package com.tritiumgaming.phasmophobiaevidencepicker.util

import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.NewsletterRepository.NewsletterInboxType.InboxType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.NewsletterViewModel
import org.xmlpull.v1.XmlPullParserFactory

@Deprecated("Use NewsletterService instead.")
class RSSParserUtils(
    factory: XmlPullParserFactory,
    urlStr: String?,
    private val inboxType: InboxType,
    private val newsLetterViewModel: NewsletterViewModel
) {
    /*init {
        Log.d("MessageCenter", "RSSThread initializing...")
        Thread(RSSThread(factory, urlStr)).start()
    }

    fun getInputStream(urlStr: String?): InputStream? {
        try {
            val url = URL(urlStr)
            return url.openConnection().getInputStream()
        }
        catch (e: IOException) { e.printStackTrace() }
        return null
    }

    inner class RSSThread(
        private val factory: XmlPullParserFactory, urlStr: String?
    ) : Runnable {

        private val urlStr: String?

        init {
            factory.isNamespaceAware = false
            this.urlStr = urlStr
        }

        override fun run() {
            Log.d("MessageCenter", "Starting RSSThread parse.")
            try {
                val inStream = getInputStream(urlStr)
                    ?: throw IOException("RSSParser: InputStream is null! Cannot retrieve " +
                            "database info. Is the internet connected?")
                val xpp = factory.newPullParser()
                xpp.setInput(inStream, "UTF_8")

                var insideItem = false
                var eventType = xpp.eventType

                val messageList = NewsletterInboxModel()
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
                                val message = NewsletterMessageModel(title, description, date)
                                //Log.d("MessageCenter", "New Message: $message")
                                messageList.add(message)
                                //if (message.hasContent()) { messageList.add(message) }
                            }
                        }
                    }
                    eventType = xpp.next()
                }
                inStream.close()

                newsLetterViewModel.addInbox(messageList, inboxType)
                Log.d("MessageCenter", "Adding $inboxType inbox")
            }
            catch (e: XmlPullParserException) { e.printStackTrace() }
            catch (e: IOException) { e.printStackTrace() }
        }
    }*/
}
