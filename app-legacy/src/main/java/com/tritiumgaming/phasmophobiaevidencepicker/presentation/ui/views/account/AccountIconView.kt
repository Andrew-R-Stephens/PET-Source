package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.account

import android.content.Context
import android.util.AttributeSet
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.withStyledAttributes
import com.google.android.material.card.MaterialCardView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.common.prefabicon.AccountIcon
import com.tritiumgaming.core.ui.common.prefabicon.AccountIconPrimaryContent
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser
import com.tritiumgaming.phasmophobiaevidencepicker.data.remote.api.firestore.transactions.user.FirestoreUser.Companion.getCurrentFirebaseUserDisplayNameInitials
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.app.mappers.ToComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.OutlineTextView
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getDrawableFromAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getTextStyleFromAttribute
import com.tritiumgaming.shared.core.domain.icons.IconResources

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
                modifier = Modifier
                    .size(48.dp),
                borderColor =  Color(getColorFromAttribute(
                    context, R.attr.textColorBody)),
                backgroundColor = Color(getColorFromAttribute(
                    context, R.attr.backgroundColorOnBackground)),
                placeholder = {
                    IconResources.IconResource.PERSON.ToComposable(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        colors = IconVectorColors.defaults(
                            fillColor = Color(getColorFromAttribute(
                                context, R.attr.backgroundColor)),
                            strokeColor = Color(getColorFromAttribute(
                                context, R.attr.textColorBody))
                        )
                    )
                },
                content = {
                    val authUser = Firebase.auth.currentUser
                    val authUserName = authUser?.displayName ?: ""

                    val names: List<String?> = (authUserName).split(" ")

                    val textStyle = getTextStyleFromAttribute(
                        context, R.attr.primaryFont_Bold_Auto)

                    AccountIconPrimaryContent(
                        firstName = names.getOrNull(0) ?: "",
                        lastName = names.getOrNull(1) ?: "",
                        textStyle = textStyle.copy(
                            color = Color(getColorFromAttribute(
                                context, R.attr.textColorBody)),
                            textAlign = TextAlign.Center,
                            shadow = Shadow(
                                color = Color(getColorFromAttribute(
                                    context, R.attr.backgroundColorOnBackground)),
                                blurRadius = 8f
                            ),
                        )
                    ){
                        getDrawableFromAttribute(
                            context = context,
                            attribute = R.attr.theme_badge
                        )?.let { drawableRes ->
                            Image(
                                painter = painterResource(id = drawableRes),
                                contentDescription = "",
                                contentScale = ContentScale.Inside,
                                alpha = .5f
                            )
                        }
                    }
                }
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
