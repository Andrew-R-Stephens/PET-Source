package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.util.Log
import android.util.TypedValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.TritiumGaming.phasmophobiaevidencepicker.R

@Composable
@Preview
fun AccountIcon() {
    // [START android_compose_clip_image]
    val context = LocalContext.current;
    val typedValue = TypedValue()
    context.theme.resolveAttribute(R.attr.theme_badge, typedValue, true)
    val imageResId = typedValue.resourceId
    context.theme.resolveAttribute(R.attr.theme_colorPrimary, typedValue, true)
    val colorResId = if (typedValue.resourceId != 0)
        colorResource(id = typedValue.resourceId) else Color(typedValue.data)
    Log.d("CustomImageColor", "$colorResId");

    val size = 48.dp
    val borderWidth = 4.dp / 200.dp * size
    val borderColor = colorResId/*Color(context.theme.resources.getColor(colorResId))*/

    Image(
        /*painter = painterResource(id = iconResource),*/
        painter = painterResource(id = imageResId),
        contentDescription = stringResource(id = R.string.codex_label_gh_ost),
        contentScale = ContentScale.Inside,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(borderWidth, borderColor, CircleShape)
    )
    // [END android_compose_clip_image]
}
