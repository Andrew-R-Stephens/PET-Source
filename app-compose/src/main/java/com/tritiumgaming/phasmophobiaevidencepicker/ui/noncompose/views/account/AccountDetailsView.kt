package com.tritiumgaming.phasmophobiaevidencepicker.ui.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.R

class AccountDetailsView : ConstraintLayout {
    constructor(context: Context) : super(context) {
        init(getContext())
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(getContext())
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(getContext())
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init(getContext())
    }

    fun init(context: Context?) {
        inflate(context, R.layout.layout_account_details, this)

        val user = com.tritiumgaming.phasmophobiaevidencepicker.data.firebase.firestore.transactions.user.FirestoreUser.currentFirebaseUser
        setUsernameInitials(user)

        setContainerVisibility(user)
    }

    private fun setContainerVisibility(user: FirebaseUser?) {
        visibility = if (user == null) { GONE } else { VISIBLE }
    }

    @Throws(Exception::class)
    private fun setUsernameInitials(user: FirebaseUser?) {
        val accountIcon = findViewById<AccountIconView>(R.id.account_icon)
        accountIcon?.createAccountInitials(user?.displayName)
    }
}