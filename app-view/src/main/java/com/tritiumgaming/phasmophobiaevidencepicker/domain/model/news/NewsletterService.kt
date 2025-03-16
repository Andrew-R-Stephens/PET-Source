package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.news

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.xml.xml
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig.Companion.IGNORING_UNKNOWN_CHILD_HANDLER
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName

class NewsletterService(
    private val inbox: NewsletterInbox
) {

    @OptIn(ExperimentalXmlUtilApi::class)
    suspend fun execute() {
        val format = XML {
            xmlDeclMode = XmlDeclMode.Charset
            unknownChildHandler = IGNORING_UNKNOWN_CHILD_HANDLER
        }

        val client = HttpClient(CIO) {
            install(ContentNegotiation){
                xml(
                    contentType = ContentType.Text.Plain,
                    format = format
                )
                xml(
                    contentType = ContentType.Text.Xml,
                    format = format
                )
            }
        }

        try {
            val dao = client.get(inbox.inboxType.url).body<NewsletterInboxDAO>()

            dao.channel.items?.forEachIndexed {
                    index: Int, item: NewsletterInboxDAO.NewsletterChannel.NewsletterMessage ->
                /*Log.d("MessageCenter", "Adding message: $item")*/

                inbox.addMessage(
                    NewsletterMessage(
                        id = item.id ?: "$index",
                        title = item.title,
                        description = item.description,
                        date = item.pubDate
                    )
                )
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e("MessageCenter", "Adding message failed!")
        }

    }

    @Serializable
    @SerialName("rss")
    @XmlSerialName(value = "rss")
    data class NewsletterInboxDAO(
        val channel: NewsletterChannel,
    ) {

        @Serializable
        @SerialName("channel")
        data class NewsletterChannel(
            @XmlElement val language: String? = null,
            @XmlElement @XmlSerialName("item")
            val items: List<NewsletterMessage>? = null,
        ) {

            @Serializable
            data class NewsletterMessage(
                @XmlSerialName("id") @XmlElement
                val id: String? = null,
                @XmlSerialName("title") @XmlElement
                val title: String? = null,
                @XmlSerialName("pubDate") @XmlElement
                val pubDate: String? = null,
                @XmlSerialName("description") @XmlElement
                val description: String? = null,
            ) {

                override fun toString(): String {
                    return "\n$title | $id | $pubDate\n$description"
                }

            }

            override fun toString(): String {
                val stringB = StringBuilder()

                if(language?.isNotEmpty() == true) { stringB.append("Language: $language\n") }
                items?.forEach { stringB.append(it.toString()) }

                return stringB.toString()
            }

        }

        override fun toString(): String {
            return channel.toString()
        }

    }

}
