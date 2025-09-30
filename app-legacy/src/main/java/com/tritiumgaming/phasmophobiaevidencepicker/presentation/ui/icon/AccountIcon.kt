package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser

@Composable
fun AccountIcon(
    modifier: Modifier = Modifier,
    user: FirebaseUser? = null,
    borderColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Unspecified,
    placeholder: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(2.dp, borderColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (user == null) {
            placeholder()
        } else {
            content()
        }
    }
}

@Composable
fun AccountIconPrimaryContent(
    firstName: String? = null,
    lastName: String? = null,
    textStyle: TextStyle,
    showText: Boolean = true,
    content: @Composable () -> Unit = {},
) {
    content()

    if(showText) {
        val firstNameInitial = firstName?.firstOrNull()?.uppercase() ?: ""
        val lastNameInitial = lastName?.firstOrNull()?.uppercase() ?: ""

        val rememberInitials by remember { mutableStateOf("$firstNameInitial$lastNameInitial") }

        Box(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp)
                    .align(Alignment.Center),
                text = rememberInitials,
                style = textStyle,
                autoSize = TextAutoSize.StepBased(minFontSize = 1.sp, stepSize = 5.sp)
            )
        }
    }
}


