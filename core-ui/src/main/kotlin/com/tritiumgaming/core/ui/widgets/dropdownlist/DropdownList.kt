package com.tritiumgaming.core.ui.widgets.dropdownlist

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(
    modifier: Modifier = Modifier,
    options: List<Int>,
    enabled: Boolean = true,
    label: Int,
    onSelect: (Int) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    onColor: Color = Color.Unspecified
) {

    var expanded by remember { mutableStateOf(false) }

    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides 0.dp
    ) {
        ExposedDropdownMenuBox(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            expanded = expanded,
            onExpandedChange = {
                expanded = if(enabled) !expanded else false
            },
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val interactionSource = remember { MutableInteractionSource() }

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        ),
                    value = stringResource(label),
                    onValueChange = {},
                    readOnly = true,
                    textStyle = textStyle.copy(
                        fontSize = 18.sp,
                        color = onColor,
                    ),
                    enabled = enabled,
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = stringResource(label),
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            trailingIcon = {
                                if(enabled) {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                }
                            },
                            placeholder = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    text = stringResource(label),
                                    style = textStyle,
                                    color = onColor.copy(alpha = 0.5f),
                                    fontSize = 14.sp
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                unfocusedTrailingIconColor = onColor,
                                focusedTrailingIconColor = onColor
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                            container = {
                                TextFieldDefaults.Container(
                                    enabled = true,
                                    isError = false,
                                    interactionSource = interactionSource,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        disabledContainerColor = Color.Transparent,
                                        errorContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        errorIndicatorColor = Color.Transparent,
                                    ),
                                    shape = TextFieldDefaults.shape,
                                )
                            }
                        )
                    }
                )

            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = color,
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                ),
                scrollState = rememberScrollState(),
                matchAnchorWidth = false
            ) {

                options.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(item),
                                style = textStyle,
                                fontSize = 18.sp
                            )
                        },
                        colors = MenuDefaults.itemColors().copy(
                            textColor = onColor,
                        ),
                        contentPadding = PaddingValues.Zero,
                        onClick = {
                            expanded = false
                            onSelect(index)
                        },
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownList(
    modifier: Modifier = Modifier,
    options: List<String>,
    enabled: Boolean = true,
    label: String,
    onSelect: (Int) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    color: Color = Color.Unspecified,
    onColor: Color = Color.Unspecified,
    selectionFontSize: TextUnit = textStyle.fontSize,
    optionsFontSize: TextUnit = textStyle.fontSize
) {

    var expanded by remember { mutableStateOf(false) }

    CompositionLocalProvider(
        LocalMinimumInteractiveComponentSize provides 0.dp
    ) {
        ExposedDropdownMenuBox(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            expanded = expanded,
            onExpandedChange = {
                expanded = if(enabled) !expanded else false
            },
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val interactionSource = remember { MutableInteractionSource() }

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(
                            type = ExposedDropdownMenuAnchorType.PrimaryNotEditable,
                            enabled = true
                        ),
                    value = label,
                    onValueChange = {},
                    readOnly = true,
                    textStyle = textStyle.copy(
                        fontSize = selectionFontSize,
                        color = onColor,
                    ),
                    enabled = enabled,
                    interactionSource = interactionSource,
                    decorationBox = { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = label,
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = true,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            trailingIcon = {
                                if(enabled) {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                }
                            },
                            placeholder = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    text = label,
                                    style = textStyle,
                                    color = onColor.copy(alpha = 0.5f),
                                    fontSize = optionsFontSize
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                                unfocusedTrailingIconColor = onColor,
                                focusedTrailingIconColor = onColor
                            ),
                            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 2.dp),
                            container = {
                                TextFieldDefaults.Container(
                                    enabled = true,
                                    isError = false,
                                    interactionSource = interactionSource,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        disabledContainerColor = Color.Transparent,
                                        errorContainerColor = Color.Transparent,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent,
                                        disabledIndicatorColor = Color.Transparent,
                                        errorIndicatorColor = Color.Transparent,
                                    ),
                                    shape = TextFieldDefaults.shape,
                                )
                            }
                        )
                    }
                )

            }

            ExposedDropdownMenu(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = color,
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                ),
                scrollState = rememberScrollState(),
                matchAnchorWidth = false
            ) {

                options.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                style = textStyle,
                                fontSize = 18.sp
                            )
                        },
                        colors = MenuDefaults.itemColors().copy(
                            textColor = onColor,
                        ),
                        contentPadding = PaddingValues.Zero,
                        onClick = {
                            expanded = false
                            onSelect(index)
                        },
                    )
                }

            }
        }
    }
}
