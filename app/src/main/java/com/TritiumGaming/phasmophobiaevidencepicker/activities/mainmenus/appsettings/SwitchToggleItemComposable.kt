package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.appsettings

import android.util.TypedValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.ds.GlobalPreferencesViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.theme.SelectiveTheme
import com.TritiumGaming.phasmophobiaevidencepicker.theme.types.Classic


@Composable
@Preview
fun Test() {
    LabeledSwitch()
}

@Composable
fun LabeledSwitch(
    globalPreferencesViewModel: GlobalPreferencesViewModel? = null,
    label: String = "Default Label",
    checked: Boolean = false
) {

    val rememberLabel by remember { mutableStateOf(label) }
    var rememberChecked by remember { mutableStateOf(checked) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {

        val context = LocalContext.current

        val typedValue = TypedValue()
        context.theme.resolveAttribute(
            R.attr.primaryFont_Regular_Manual,
            typedValue,
            true)

        context.theme.resolveAttribute(
            R.attr.textColorBody,
            typedValue,
            true)
        val color = Color(typedValue.data)

        Switch(
            rememberChecked,
            {
                rememberChecked = !rememberChecked
            }
        )

        SelectiveTheme {

        }
        Text(
            text = rememberLabel,
            color = color,
            fontSize = 16.sp,
            maxLines = 1,
            style = Classic.primary.regular
        )

    }

}