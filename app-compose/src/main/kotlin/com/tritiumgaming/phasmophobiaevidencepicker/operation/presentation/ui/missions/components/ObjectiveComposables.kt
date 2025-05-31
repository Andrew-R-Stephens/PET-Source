package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.components

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.viewmodel.ObjectivesViewModel

@Preview
@Composable
private fun MissionsPreview() {

    Column {
        ObjectiveWrapper(
            titleRes = R.string.objectives_title_optional_objective_1
        )
        ObjectiveWrapper(titleRes = R.string.objectives_title_optional_objective_2)
        ObjectiveWrapper(titleRes = R.string.objectives_title_optional_objective_3)
    }

}

@Preview
@Composable
private fun ObjectiveSelectorPreview() {

    ObjectiveWrapper()

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjectiveWrapper(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel? = null,
    @StringRes titleRes: Int = R.string.objectives_title_optional_objective_1
) {

    val theList = listOf<Int>(
            R.string.objective_info_salt,
            R.string.objective_info_crucifix,
            R.string.objective_info_emfreader,
            R.string.objective_info_lowsanity,
            R.string.objective_info_escapehunt
        )
    var selectedItem = 0

    var checked by remember {
        mutableStateOf(false)
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Text(
            text = stringResource(titleRes),
            style = LocalTypography.current.primary.regular,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .padding(4.dp)
        ) {

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .weight(1f),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {

                Text(
                    modifier = Modifier,
                    text = stringResource(theList[selectedItem])
                )

                ExposedDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    containerColor = LocalPalette.current.surface.onColor
                ) {

                    /*if(objectivesViewModel?.missionsListModel != null) {
                        objectivesViewModel.missionsListModel?.getMissionsBy(false)
                            ?.forEach { mission: Mission ->
                                DropdownMenuItem(
                                    text =  {
                                        Text(text = stringResource(mission.contentRes))
                                    },
                                    onClick = { expanded = false },
                                )
                            }
                    } else {*/
                        theList.forEach { it ->
                            DropdownMenuItem(
                                text =  {
                                    Text(text = stringResource(it))
                                },
                                onClick = {
                                    expanded = false
                                    selectedItem = it
                                },
                            )
                        }
                    /*}*/
                }
            }

            PETImageButton(
                modifier = Modifier
                    .size(48.dp)
                    .clickable(onClick = { checked = !checked }),
                type = if(!checked) PETImageButtonType.CONFIRM else PETImageButtonType.CANCEL
            )

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun ObjectiveDropdownMenu(
    options: List<Int> = listOf<Int>(
        R.string.objective_info_salt,
        R.string.objective_info_crucifix,
        R.string.objective_info_emfreader,
        R.string.objective_info_lowsanity,
        R.string.objective_info_escapehunt
    )
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val fieldState = rememberTextFieldState(stringResource(options[0]))

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
    ) {

        /*TextField(
            value = TextFieldValue,
            onValueChange = {},
            modifier = Modifier,
            enabled = true,
            readOnly = false,
            textStyle = LocalTypography.current.primary,
            label = null,
            placeholder = null,
            leadingIcon = null,
            trailingIcon = null,
            prefix = null,
            suffix = null,
            supportingText = null,
            isError = false,
            visualTransformation = VisualTransformation.None,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
            singleLine = false,
            maxLines = 2,
            minLines = 1,
            interactionSource = null,
            shape = TextFieldDefaults.shape,
            colors = TextFieldDefaults.colors()
        )*/

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                val detail = stringResource(option)

                DropdownMenuItem(
                    text = { Text(text = detail) },
                    onClick = {
                        fieldState.setTextAndPlaceCursorAtEnd(detail)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}
