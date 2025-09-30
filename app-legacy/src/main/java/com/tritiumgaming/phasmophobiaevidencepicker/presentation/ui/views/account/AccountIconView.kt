package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.withStyledAttributes
import com.google.android.material.card.MaterialCardView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser.Companion.getCurrentFirebaseUserDisplayNameInitials
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.icon.AccountIcon
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.OutlineTextView

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
            context.withStyledAttributes(attrs, R.styleable.AccountIconView) {
                setAccountInitials(
                    getResourceId(R.styleable.AccountIconView_accountInitials, 0)
                )
            }
        }
    }

    private fun setDefaults() {
        minimumWidth = 48
        minimumHeight = 48
        setBackgroundColor(context.resources.getColor(R.color.transparent))

        val profileIcon = findViewById<ComposeView>(R.id.profile_icon)
        val user = FirestoreUser.currentFirebaseUser
        profileIcon.setContent {
            AccountIcon(
                user = user,
            )
        }

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
