package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.noncompose.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseUser
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser

@Deprecated("Migrate to Jetpack Compose")
class AccountLoginRequestView : ConstraintLayout {
    constructor(context: Context) : super(context) { this.init(getContext()) }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) { this.init(getContext()) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) { this.init(getContext()) }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) { this.init(getContext())}

    fun init(context: Context?) {
        inflate(context, R.layout.layout_account_login_request, this)

        val user: FirebaseUser? = FirestoreUser.currentFirebaseUser

        setContainerVisibility(user)
    }

    fun setMessage(@StringRes message: Int) {
        setMessage(context.getString(message))
    }

    private fun setMessage(message: String?) {
        val textView = findViewById<AppCompatTextView>(R.id.settings_requestlogin_title)
        textView?.text = message
    }

    private fun setContainerVisibility(user: FirebaseUser?) {
        visibility = if (user == null) { VISIBLE } else { GONE }
    }
}