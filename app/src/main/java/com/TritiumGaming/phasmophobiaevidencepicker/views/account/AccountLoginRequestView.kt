package com.TritiumGaming.phasmophobiaevidencepicker.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser
import com.google.firebase.auth.FirebaseUser

class AccountLoginRequestView : ConstraintLayout {
    constructor(context: Context) :
            super(context) {
        this.init(getContext())
    }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) {
        this.init(getContext())
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        this.init(getContext())
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        this.init(getContext())
    }

    fun init(context: Context?) {
        inflate(context, R.layout.layout_account_login_request, this)

        var user: FirebaseUser? = null
        try {
            user = FirestoreUser.currentFirebaseUser
        } catch (e: Exception) {
            e.printStackTrace()
        }

        setContainerVisibility(user)
    }

    fun setMessage(@StringRes message: Int) {
        setMessage(context.getString(message))
    }

    fun setMessage(message: String?) {
        val textView = findViewById<AppCompatTextView>(R.id.settings_requestlogin_title)
        textView?.text = message
    }

    private fun setContainerVisibility(user: FirebaseUser?) {
        visibility = if (user == null) { VISIBLE } else { GONE }
    }
}