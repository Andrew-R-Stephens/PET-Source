
import com.tritiumgaming.shared.data.account.model.AccountCreditTransaction
import com.tritiumgaming.shared.data.account.model.AccountCredits
import com.tritiumgaming.shared.data.account.repository.FirestoreAccountRepository
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AccountTransactionsTesting {

    private lateinit var mockAccountRepository: FirestoreAccountRepository
    private lateinit var addAccountCreditsUseCase: AddAccountCreditsUseCase

    private val testDispatcher = StandardTestDispatcher() // From kotlinx-coroutines-test

    @Before
    fun setUp() {
        // Initialize mocks before each test
        mockAccountRepository = mock()

        // Instantiate the use case with the mock repository
        addAccountCreditsUseCase = AddAccountCreditsUseCase(mockAccountRepository)
    }

    @Test
    fun `invoke with positive credits should call repository and return success`(): Unit =
        runTest(testDispatcher) {
            // Arrange
            val credits = 10L
            val transaction = AccountCreditTransaction(credits = credits)
            val expectedCredits = AccountCredits(earnedCredits = credits, spentCredits = 0L)
            val expectedResult = Result.success(expectedCredits)

            whenever(addAccountCreditsUseCase(credits)).thenReturn(expectedResult)

            // Act
            val actualResult = addAccountCreditsUseCase(credits)

            // Assert
            Assert.assertEquals(expectedResult, actualResult)

            // Verify that the repository's addCredits method was called exactly once with the correct parameter
            verify(mockAccountRepository).addCredits(transaction)
    }

}