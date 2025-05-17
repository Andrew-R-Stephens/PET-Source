import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.source.remote.api.firestore.transactions.user.FirestoreUser
import org.junit.Test

class FirebaseTesting {

    @Test
    fun testFirebaseUserDisplayNameIsNull() {
        val userName = null

        val result = FirestoreUser.getCurrentFirebaseUserDisplayNameInitials(userName)

        assert(result.isEmpty())
    }

    @Test
    fun testFirebaseUserDisplayNameIsEmpty() {
        val userName = ""

        val result = FirestoreUser.getCurrentFirebaseUserDisplayNameInitials(userName)

        assert(result.isEmpty())
    }

    @Test
    fun testFirebaseUserDisplayNameHasOneName() {
        val userName = "Andrew"

        val result = FirestoreUser.getCurrentFirebaseUserDisplayNameInitials(userName)

        assert(result.length == 1) {
            println(result);
        }
    }

    @Test
    fun testFirebaseUserDisplayNameHasTwoNames() {
        val userName = "Andrew Stephens"

        val result = FirestoreUser.getCurrentFirebaseUserDisplayNameInitials(userName)

        assert(result.length == 2) {
            println(result);
        }
    }

    @Test
    fun testFirebaseUserDisplayNameHasMoreThanTwoNames() {
        val userNames = listOf(
            "Bandrew Yephens Is",
            "Candrew Jephens Is A",
            "Pandrew Lephens Is A Wierd",
            "Randrew Gephens Is A Wierd Dude")

        for(userName in userNames) {
            val result = FirestoreUser.getCurrentFirebaseUserDisplayNameInitials(userName)
            assert(result.length <= 2)
            Log.d("Test", result)
        }

    }

}
