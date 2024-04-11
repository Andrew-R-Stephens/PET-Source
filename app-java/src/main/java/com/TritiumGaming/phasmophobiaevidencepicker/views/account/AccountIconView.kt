package com.TritiumGaming.phasmophobiaevidencepicker.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.compose.ui.platform.ComposeView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser
import com.TritiumGaming.phasmophobiaevidencepicker.views.OutlineTextView
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.AccountIcon
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseUser

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
        if (outlineTextView != null) {
            outlineTextView.text = initials
        }
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
        val displayInitials = StringBuilder()
        if (displayName != null) {
            val names =
                displayName.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (name in names) {
                val trimmedName = name.trim { it <= ' ' }
                if (trimmedName.isNotEmpty()) {
                    val initial = trimmedName[0]
                    displayInitials.append(initial)
                    if (displayInitials.length >= 2) {
                        break
                    }
                }
            }
            if (displayInitials.isNotEmpty()) {
                setAccountInitials(displayInitials.toString())
            }
        }
    }
}
