package com.TritiumGaming.phasmophobiaevidencepicker.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.compose.ui.platform.ComposeView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser.Companion.getCurrentFirebaseUserDisplayNameInitials
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.AccountIcon
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.OutlineTextView
import com.google.android.material.card.MaterialCardView

class AccountIconView : MaterialCardView {
    constructor(context: Context) :
            super(context) {
        this.init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) :
            super(context, attrs) {
        this.init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        this.init(context, null)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.item_account_icon, this)
        setDefaults()
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AccountIconView)
            setAccountInitials(
                a.getResourceId(R.styleable.AccountIconView_accountInitials, 0))
            a.recycle()
        }
    }

    private fun setDefaults() {
        minimumWidth = 48
        minimumHeight = 48
        setBackgroundColor(context.resources.getColor(R.color.transparent))

        val profileIcon = findViewById<ComposeView>(R.id.profile_icon)
        profileIcon.setContent { AccountIcon() }

        val user = FirestoreUser.currentFirebaseUser
        createAccountInitials(user?.displayName)
    }

    fun createAccountInitials(displayName: String?) {
        val displayInitials = getCurrentFirebaseUserDisplayNameInitials(displayName)

        setAccountInitials(displayInitials)
    }

    private fun setAccountInitials(initials: Int) {
        setAccountInitials(context.getString(initials))
    }

    private fun setAccountInitials(initials: String) {
        val outlineTextView = findViewById<OutlineTextView>(R.id.label_username_initials)
        outlineTextView?.text = initials
    }

}
