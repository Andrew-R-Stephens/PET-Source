import android.util.Log
import org.jetbrains.annotations.TestOnly
import org.junit.Test

class FirebaseTesting {

    @Test
    fun testFirebaseUserDisplayNameIsNull() {
        val userName = null

        val result = getCurrentFirebaseUserDisplayNameInitials(userName)

        assert(result.isEmpty())
    }

    @Test
    fun testFirebaseUserDisplayNameIsEmpty() {
        val userName = ""

        val result = getCurrentFirebaseUserDisplayNameInitials(userName)

        assert(result.isEmpty())
    }

    @Test
    fun testFirebaseUserDisplayNameHasOneName() {
        val userName = "Andrew"

        val result = getCurrentFirebaseUserDisplayNameInitials(userName)

        assert(result.length == 1) {
            println(result);
        }
    }

    @Test
    fun testFirebaseUserDisplayNameHasTwoNames() {
        val userName = "Andrew Stephens"

        val result = getCurrentFirebaseUserDisplayNameInitials(userName)

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
            val result = getCurrentFirebaseUserDisplayNameInitials(userName)
            assert(result.length <= 2)
            Log.d("Test", result)
        }

    }

    @TestOnly
    private fun getCurrentFirebaseUserDisplayNameInitials(displayName: String?): String {
        val displayInitials = StringBuilder()
        if (displayName != null) {
            val names =
                displayName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            for (name in names) {
                val trimmedName = name.trim { it <= ' ' }
                if (trimmedName.isNotEmpty()) {
                    val initial = trimmedName[0]
                    displayInitials.append(initial)
                    if (displayInitials.length >= 2) { break }
                }
            }
        }
        return displayInitials.toString()
    }
}
