package com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography


@Composable
@Preview
private fun OperationDropdownPreview() {
    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigDropdown(
            label = stringResource(R.string.map_name_short_prison),
            state = ConfigDropdownUiState(),
            actions = DropdownUiActions()
        )
    }

    SelectiveTheme(ClassicPalette, ClassicTypography) {
        OperationConfigDropdown(
            label = stringResource(R.string.map_name_short_prison),
            state = ConfigDropdownUiState(),
            actions = DropdownUiActions()
        )
    }
}

@Composable
fun OperationConfigDropdown(
    modifier: Modifier = Modifier,
    label: String = "None",
    state: ConfigDropdownUiState,
    @DrawableRes primaryIcon: Int? = null,
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    iconColorFilter: ColorFilter = ColorFilter.tint(Color.Unspecified),
    actions: DropdownUiActions
) {

    Surface(
        modifier = modifier,
        color = containerColor
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            primaryIcon?.let {
                Image(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(12.dp),
                    contentScale = ContentScale.Inside,
                    alignment = Alignment.Center,
                    painter = painterResource(primaryIcon),
                    colorFilter = iconColorFilter,
                    contentDescription = ""
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                contentAlignment = Alignment.Center
            ) {

                DropdownList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    state = state,
                    actions = actions
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DropdownList(
    modifier: Modifier = Modifier,
    state: ConfigDropdownUiState,
    actions: DropdownUiActions,
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .menuAnchor(
                        type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    )
                    .padding(horizontal = 4.dp),
                value = stringResource(state.label),
                textStyle = LocalTypography.current.quaternary.regular.copy(
                    color = LocalPalette.current.onSurface,
                    fontSize = 14.sp
                ),
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .wrapContentHeight(),
                        text = stringResource(state.label),
                        style = LocalTypography.current.quaternary.regular,
                        color = LocalPalette.current.onSurface.copy(alpha = 0.5f),
                        fontSize = 14.sp
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                maxLines = 2,
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    unfocusedTrailingIconColor = LocalPalette.current.onSurface,
                    focusedTrailingIconColor = LocalPalette.current.onSurface
                ),
                onValueChange = {},
                readOnly = true
            )

        }

        ExposedDropdownMenu(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            ),
            scrollState = rememberScrollState(),
            matchAnchorWidth = true,

            ) {

            state.list.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text =  {
                        Text(
                            text = stringResource(item),
                            style = LocalTypography.current.quaternary.regular,
                            color = LocalPalette.current.onSurface,
                            fontSize = 18.sp
                        )
                    },
                    colors = MenuDefaults.itemColors().copy(
                        textColor = LocalPalette.current.onSurface,
                    ),
                    contentPadding = PaddingValues.Zero,
                    onClick = {
                        expanded = false
                        actions.onSelect(index)
                    },
                )
            }

        }
    }
}
