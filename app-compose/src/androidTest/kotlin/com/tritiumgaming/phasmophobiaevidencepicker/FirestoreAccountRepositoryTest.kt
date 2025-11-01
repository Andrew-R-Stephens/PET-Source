package com.tritiumgaming.phasmophobiaevidencepicker

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

const val DUMMY_PROJECT_ID = "[DEFAULT]"

class FirestoreAccountRepositoryTest {

    private lateinit var mockContext: Context

    private lateinit var firestore: FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val EMULATOR_HOST = "10.0.2.2"
        private const val FIRESTORE_EMULATOR_PORT = 8080
        private const val AUTH_EMULATOR_PORT = 9099

        private const val FIREBASE_AUTH_TEST_EMAIL = "test@gmail.com"
        private const val FIREBASE_AUTH_TEST_PASSWORD = "tester"
    }

    @Before
    fun setup() {

        mockContext = InstrumentationRegistry.getInstrumentation().targetContext

        try {
            Firebase.firestore(DUMMY_PROJECT_ID).useEmulator(EMULATOR_HOST, FIRESTORE_EMULATOR_PORT)
            firestore = Firebase.firestore

            println("Firestore Emulator connected for JVM test at $EMULATOR_HOST:$FIRESTORE_EMULATOR_PORT")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            // Point Firebase Auth to the emulator
            Firebase.auth.useEmulator(EMULATOR_HOST, AUTH_EMULATOR_PORT)
            firebaseAuth = Firebase.auth

            println("Firebase Auth Emulator connected for JVM test at $EMULATOR_HOST:$AUTH_EMULATOR_PORT")
        } catch (e: IllegalStateException) {
            println("Emulators might already be configured or error during setup.")
            e.printStackTrace()
        }

        // clearFirestoreData()
    }

    @After
    fun tearDown() {
        // clearFirestoreData()
        // clearAuthUsers()
    }

    /*private fun clearFirestoreData() = runBlocking { // Use runBlocking for suspend funcs in JVM tests

        // Example: Deleting all documents in a "test_items" collection
        val collectionRef = firestore.collection("test_items")
        val querySnapshot = collectionRef.get().await()
        val batch = firestore.batch()
        for (document in querySnapshot.documents) {
            batch.delete(document.reference)
        }
        batch.commit().await()
        println("Cleared 'test_items' collection in Firestore Emulator.")
    }*/

    @Test
    fun getCurrentUserReturnsUserAfterSignIn() = runBlocking {

        val credentialRaw = StringBuilder()
        credentialRaw.apply {
            append("{")
            append("\"sub\": \"abc123\", ")
            append("\"email\": \"$FIREBASE_AUTH_TEST_EMAIL\", ")
            append("\"email_verified\": true")
            append("}")
        }

        val credential = GoogleAuthProvider.getCredential(credentialRaw.toString(), null)
        println("Created FAKE Google credential.")

        try {
            val authResult = Firebase.auth.signInWithCredential(credential).await()
            //val authResult = firebaseAuth.signInAnonymously().await()
            println("Signed in with FAKE Google credential. User: ${authResult?.user?.uid}")

            // Act
            val currentUser = firebaseAuth.currentUser // This will be the user from the emulator

            // Assert
            assertEquals(FIREBASE_AUTH_TEST_EMAIL, currentUser)
            // You can get the UID and use it for further operations
            // println("Emulator User UID: ${currentUser?.uid}")

            // Cleanup: Sign out or clear users if needed for other tests
            firebaseAuth.signOut()

        } catch (e: Exception) {
            println("Failed to sign in with fake Google credential")
            e.printStackTrace()

            assert(false)
        }

    }

    // --- Example Test Method ---
    @Test
    fun addItem_shouldStoreItemInFirestoreEmulator() = runBlocking {
        // Arrange
        val itemId = "jvmItem123"
        val itemData = hashMapOf(
            "name" to "Test Item from JVM",
            "value" to 100
        )

        // Act
        firestore.collection("test_items").document(itemId).set(itemData).await()

        // Assert
        val snapshot = firestore.collection("test_items").document(itemId).get().await()
        assertTrue("Document should exist in emulator", snapshot.exists())
        assertEquals("Test Item from JVM", snapshot.getString("name"))
        assertEquals(100L, snapshot.getLong("value"))
    }

    // Add more tests for your repository or data source methods...
}