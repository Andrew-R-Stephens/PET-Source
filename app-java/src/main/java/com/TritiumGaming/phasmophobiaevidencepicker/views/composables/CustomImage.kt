package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser

@Composable
@Preview
fun AccountIcon() {

    val borderColor =
        Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.theme_colorPrimary))
    val backgroundColorResId =
        Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.backgroundColorOnBackground))
    val personTint =
        Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.textColorBody))

    val size = 48.dp
    val borderWidth = 4.dp / 200.dp * size

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColorResId)
            .border(borderWidth, borderColor, CircleShape)
    ) {
        val contentScale = ContentScale.Inside
        val contentDescription = "Outer Box"

        if (FirestoreUser.getCurrentFirebaseUser() == null) {
            Image(
                painter = painterResource(id = R.drawable.ic_person),
                colorFilter = ColorFilter.tint(personTint),
                contentDescription = contentDescription,
                contentScale = contentScale,
                modifier = Modifier
                    .padding(4.dp)
            )
        } else {
            val typedValue = TypedValue()
            LocalContext.current.theme.resolveAttribute(R.attr.theme_badge, typedValue, true)
            val imageResId = typedValue.resourceId

            Image(
                painter = painterResource(id = imageResId),
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }
    }
}
