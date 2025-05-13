package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.source.remote.api.dto.NewsletterInboxDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.xml.xml
import nl.adaptivity.xmlutil.ExperimentalXmlUtilApi
import nl.adaptivity.xmlutil.XmlDeclMode
import nl.adaptivity.xmlutil.serialization.XML
import nl.adaptivity.xmlutil.serialization.XmlConfig

data object NewsletterService {

    @OptIn(ExperimentalXmlUtilApi::class)
    @Throws(Exception::class)
    suspend fun fetchInbox(
        inboxURL: Url,
        onSuccess: (inboxDto: NewsletterInboxDTO) -> Unit
    ) {
        val format = XML {
            xmlDeclMode = XmlDeclMode.Charset
            unknownChildHandler = XmlConfig.Companion.IGNORING_UNKNOWN_CHILD_HANDLER
        }

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
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
            val inboxDto = client.get(inboxURL).body<NewsletterInboxDTO>()
            onSuccess(inboxDto)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

    }

}