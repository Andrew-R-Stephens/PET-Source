package com.tritiumstudios.phasmophobiaevidencetool.android.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.compose.ui.platform.ComposeView
import com.tritiumstudios.phasmophobiaevidencetool.R
import com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.user.FirestoreUser
import com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.transactions.user.FirestoreUser.getCurrentFirebaseUserDisplayNameInitials
import com.tritiumstudios.phasmophobiaevidencetool.android.views.OutlineTextView
import com.tritiumstudios.phasmophobiaevidencetool.android.views.composables.AccountIcon
import com.google.android.material.card.MaterialCardView

class AccountIconView : MaterialCardView {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, null)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.item_account_icon, this)
        setDefaults()
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.AccountIconView)
            setAccountInitials(
                a.getResourceId(R.styleable.AccountIconView_accountInitials, 0)
            )
            a.recycle()
        }
    }

    private fun setAccountInitials(initials: Int) {
        setAccountInitials(context.getString(initials))
    }

    private fun setAccountInitials(initials: String) {
        val outlineTextView = findViewById<OutlineTextView>(R.id.label_username_initials)
        outlineTextView?.text = initials
    }

    private fun setDefaults() {
        setMinimumWidth(48)
        setMinimumHeight(48)
        setBackgroundColor(context.resources.getColor(R.color.transparent))

        val profileIcon = findViewById<ComposeView>(R.id.profile_icon)
        profileIcon.setContent { AccountIcon() }

        val user = FirestoreUser.getCurrentFirebaseUser()
        createAccountInitials(user?.displayName)
    }

    fun createAccountInitials(displayName: String?) {
        val displayInitials = getCurrentFirebaseUserDisplayNameInitials(displayName)

        setAccountInitials(displayInitials?:"")
    }
}
