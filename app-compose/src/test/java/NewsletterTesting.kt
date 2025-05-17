
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.newsletter.repository.NewsletterRepository.NewsletterInboxType
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NewsletterTesting {

    @Test
    fun runTestLoadInboxPET() = runTest {

        val pet = NewsletterInboxType(
            id = 0,
            title = 0,
            url = "https://raw.githubusercontent.com/Andrew-R-Stephens/PET-MessageCenter/refs/heads/master/Changelog",
            icon = 0,
        )

        testLoadInbox(pet)

    }

    @Test
    fun runTestLoadInboxGeneral() = runTest {

        val general = NewsletterInboxType(
            id = 1,
            title = 0,
            url = "https://raw.githubusercontent.com/Andrew-R-Stephens/PET-MessageCenter/refs/heads/master/GeneralNews",
            icon = 0,
        )

        testLoadInbox(general)

    }

    @Test
    fun runTestLoadInboxPhas() = runTest {

        val phas = NewsletterInboxType(
            id = 2,
            title = 0,
            url = "https://steamcommunity.com/games/739630/rss/",
            icon = 0,
        )

        testLoadInbox(phas)

    }

    private suspend fun testLoadInbox(inbox: NewsletterInboxType) {
        //NewsletterService(inbox).execute()
    }

}
