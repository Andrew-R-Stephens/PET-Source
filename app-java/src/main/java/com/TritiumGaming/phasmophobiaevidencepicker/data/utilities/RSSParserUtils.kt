package com.TritiumGaming.phasmophobiaevidencepicker.data.utilities

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data.NewsletterMessageData
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.data.NewsletterMessagesData
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel.InboxType
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.Locale

class RSSParserUtils(
    factory: XmlPullParserFactory,
    urlStr: String?,
    private val inboxType: InboxType,
    private val newsLetterViewModel: NewsletterViewModel
) {
    init {
        Thread(RSSThread(factory, urlStr)).start()
    }

    fun getInputStream(urlStr: String?): InputStream? {
        try {
            val url = URL(urlStr)
            return url.openConnection().getInputStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    inner class RSSThread(private val factory: XmlPullParserFactory, urlStr: String?) : Runnable {
        private val urlStr: String?

        init {
            factory.isNamespaceAware = false

            this.urlStr = urlStr
        }

        override fun run() {
            try {
                val inStream = getInputStream(urlStr)
                    ?: throw IOException(
                        "RSSParser: InputStream is null! Cannot retrieve " +
                                "database info. Is the internet connected?"
                    )
                val xpp = factory.newPullParser()
                xpp.setInput(inStream, "UTF_8")

                var insideItem = false
                var eventType = xpp.eventType

                val messageList = NewsletterMessagesData()
                var title: String? = null
                var date: String? = null
                var description: String? = null

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        val elementName = xpp.name.lowercase(Locale.getDefault())
                        when (elementName) {
                            "item" -> {
                                insideItem = true
                            }

                            "title" -> {
                                if (insideItem) {
                                    title = xpp.nextText()
                                }
                            }

                            "description" -> {
                                if (insideItem) {
                                    description = xpp.nextText()
                                }
                            }

                            "pubdate" -> {
                                if (insideItem) {
                                    date = xpp.nextText()
                                }
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.name.equals(
                            "item",
                            ignoreCase = true
                        )
                    ) {
                        insideItem = false
                        val message = NewsletterMessageData(
                            title, description, date
                        )
                        if (message.hasContent()) {
                            messageList.add(message)
                        }
                        title = null
                        date = null
                        description = null
                    }
                    eventType = xpp.next()
                }
                inStream.close()

                newsLetterViewModel.addInbox(messageList, inboxType)
            } catch (e: XmlPullParserException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
