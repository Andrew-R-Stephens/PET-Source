package com.tritiumgaming.phasmophobiaevidencepicker.ui.views.account

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestoreException
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit
import com.tritiumgaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.properties.FirestoreAccountCredit.Companion.creditsDocument

class AccountCreditsView : ConstraintLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    init {
        inflate(context, R.layout.layout_account_credits, this)
        setCredits()
    }

    private fun setCredits() {

        val creditsTextView: AppCompatTextView? = findViewById(R.id.label_credits_actual)

        try {
            val creditDoc = creditsDocument
            creditDoc.get()
                .addOnSuccessListener { task: DocumentSnapshot ->
                    val userCredits =
                        task.get(FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                    creditsTextView?.text = "$userCredits"
                }
                .addOnFailureListener { e: Exception ->
                    Log.e("Firestore", "Could get user's account credit count!")
                    e.printStackTrace()
                }

            creditDoc.addSnapshotListener {
                documentSnapshot: DocumentSnapshot?, _: FirebaseFirestoreException? ->
                if (documentSnapshot == null) { return@addSnapshotListener }
                val userCredits = documentSnapshot.get(
                    FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long::class.java) ?: 0
                creditsTextView?.text = "$userCredits"
            }
        } catch (e: Exception) { e.printStackTrace() }
    }

}
