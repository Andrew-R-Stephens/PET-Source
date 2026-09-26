package com.tritiumgaming.data.newsletter.source.remote.api

import com.tritiumgaming.data.newsletter.dto.remote.RemoteNewsletterInboxDto
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

class NewsletterService {

    @OptIn(ExperimentalXmlUtilApi::class)
    suspend fun fetchInbox(
        inboxURL: Url
    ): Result<RemoteNewsletterInboxDto> {
        val format = XML {
            xmlDeclMode = XmlDeclMode.Charset
            defaultPolicy {
                unknownChildHandler = XmlConfig.IGNORING_UNKNOWN_CHILD_HANDLER
            }
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
                xml(
                    contentType = ContentType.Application.Xml,
                    format = format
                )
                xml(
                    contentType = ContentType.Application.Rss,
                    format = format
                )
            }
        }

        return try {
                val inboxDto = client.get(inboxURL).body<RemoteNewsletterInboxDto>()
                Result.success(inboxDto)
            } catch (ex: Exception) {
                Result.failure(ex)
            }

    }

}