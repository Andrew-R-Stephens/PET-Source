
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.newsletter.repository.NewsletterRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class NewsletterTesting {

    @Test
    fun runTestLoadInboxPET() = runTest {

        val pet = NewsletterRepository.NewsletterInboxType(
            id = 0,
            title = 0,
            url = "https://raw.githubusercontent.com/Andrew-R-Stephens/PET-MessageCenter/refs/heads/master/Changelog",
            icon = 0,
        )

        testLoadInbox(pet)

    }

    @Test
    fun runTestLoadInboxGeneral() = runTest {

        val general = NewsletterRepository.NewsletterInboxType(
            id = 1,
            title = 0,
            url = "https://raw.githubusercontent.com/Andrew-R-Stephens/PET-MessageCenter/refs/heads/master/GeneralNews",
            icon = 0,
        )

        testLoadInbox(general)

    }

    @Test
    fun runTestLoadInboxPhas() = runTest {

        val phas = NewsletterRepository.NewsletterInboxType(
            id = 2,
            title = 0,
            url = "https://steamcommunity.com/games/739630/rss/",
            icon = 0,
        )

        testLoadInbox(phas)

    }

    private suspend fun testLoadInbox(inbox: NewsletterRepository.NewsletterInboxType) {
        //NewsletterService(inbox).execute()
    }

}
