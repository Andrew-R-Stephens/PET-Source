package com.tritiumgaming.core.ui.common.other

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import org.jetbrains.annotations.TestOnly

@Composable
fun PETImageButton(
    modifier: Modifier = Modifier,
    type: PETImageButtonType = PETImageButtonType.NONE,
    @DrawableRes imageRes: Int = type.imageRes,
    tint: Color = Color.White
) {

    Box(
        modifier = modifier
            .heightIn(0.dp, 48.dp)
            .padding(8.dp)
    ) {

        if(imageRes != 0) {
            Image(
                modifier = Modifier
                    .aspectRatio(1f)
                    .fillMaxSize(),
                painter = painterResource(imageRes),
                colorFilter = ColorFilter.tint(tint),
                contentDescription = ""
            )
        } else {
            Box(
                modifier = modifier
                    .aspectRatio(1f)
                    .fillMaxSize(),
            )
        }
    }
}

@Composable
@TestOnly
@Preview
private fun TestPreview() {
    PETImageButton(
        type = PETImageButtonType.BACK
    )
}

enum class PETImageButtonType(
    @param:DrawableRes val imageRes: Int = 0,
    @param:StringRes val labelRes: Int = 0
) {
    NONE(0, 0),
    BACK(R.drawable.ic_arrow_60_left),
    FORWARD(R.drawable.ic_arrow_60_right),
    CONFIRM(
        R.drawable.ic_selector_pos_unsel,
        R.string.settings_confirm),
    CANCEL(
        R.drawable.ic_selector_neg_unsel,
        R.string.settings_cancel),
    PLAY(R.drawable.ic_control_play),
    PAUSE(R.drawable.ic_control_pause)
}