package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.model

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
            val dao = client.get(inbox.inboxType.url).body<NewsletterInboxDTO>()

            dao.channel.items?.forEachIndexed {
                index: Int, item: NewsletterInboxDTO.NewsletterChannelDTO.NewsletterMessageDTO ->
                inbox.addMessage(
                    NewsletterInbox.NewsletterMessage(
                        id = item.id ?: "$index",
                        title = item.title,
                        description = item.description,
                        date = item.pubDate
                    )
                )
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

    @Serializable
    @SerialName("rss")
    @XmlSerialName(value = "rss")
    private data class NewsletterInboxDTO(
        val channel: NewsletterChannelDTO
    ) {
        @Serializable
        @SerialName("channel")
        data class NewsletterChannelDTO(
            @XmlElement val language: String? = null,
            @XmlElement @XmlSerialName("item")
            val items: List<NewsletterMessageDTO>? = null
        ) {
            @Serializable
            data class NewsletterMessageDTO(
                @XmlSerialName("id") @XmlElement val id: String? = null,
                @XmlSerialName("title") @XmlElement val title: String? = null,
                @XmlSerialName("pubDate") @XmlElement val pubDate: String? = null,
                @XmlSerialName("description") @XmlElement val description: String? = null
            ) {

                fun toNewsletterMessage(): NewsletterInbox.NewsletterMessage {
                    return NewsletterInbox.NewsletterMessage(
                        id = id ?: "NA",
                        title = title,
                        description = description,
                        date = pubDate
                    )
                }
            }

        }

    }

}
