package com.tritiumgaming.feature.operation.ui.missions.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.app.mappers.ghostname.toStringResource
import com.tritiumgaming.feature.operation.ui.missions.ObjectivesViewModel
import com.tritiumgaming.shared.operation.domain.ghostname.mappers.GhostNameResources
import com.tritiumgaming.shared.operation.domain.ghostname.model.GhostName

@Composable
fun GhostNameContent(
    modifier: Modifier = Modifier,
    objectivesViewModel: ObjectivesViewModel,
) {

    val ghostDetails = objectivesViewModel.ghostDetailsUiState.collectAsStateWithLifecycle()

    val allFirstNames = objectivesViewModel.fetchAllFirstNames().sortedBy { it.name }
    val allSurnames = objectivesViewModel.fetchAllSurnamesNames()

    val ghostFirstName = ghostDetails.value.firstName
    val ghostSurname = ghostDetails.value.surname

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 8.dp, horizontal = 12.dp),
        verticalArrangement = Arrangement.Top
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            text = stringResource(R.string.objectives_title_ghost_name),
            style = LocalTypography.current.quaternary.regular,
            color = LocalPalette.current.textFamily.body,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {

            NameWrapper(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .wrapContentHeight(),
                state = ghostFirstName,
                dropdownList = allFirstNames,
                placeholder = "First Name",
            ) { 
                objectivesViewModel.setGhostFirstName(it)
            }

            NameWrapper(
                modifier = Modifier
                    .weight(1f, fill = false)
                    .wrapContentHeight(),
                state = ghostSurname,
                dropdownList = allSurnames,
                placeholder = "Surname",
            ) {
                objectivesViewModel.setGhostLastName(it)
            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameWrapper(
    modifier: Modifier = Modifier,
    state: GhostName?,
    dropdownList: List<GhostName>,
    placeholder: String,
    onChange: (ghostName: GhostName) -> Unit
) {

    val name = state?.let { stringResource(it.name.toStringResource()) } ?: ""
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        color = LocalPalette.current.surface.onColor
    ) {

        ExposedDropdownMenuBox(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
        ) {

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
                    .wrapContentHeight()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                value = name,
                textStyle = LocalTypography.current.quaternary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .wrapContentHeight(),
                        text = placeholder,
                        style = LocalTypography.current.quaternary.regular,
                        color = LocalPalette.current.textFamily.body.copy(alpha = 0.5f),
                        fontSize = 18.sp
                    )
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                onValueChange = {},
                readOnly = true,
            )

            ExposedDropdownMenu(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = LocalPalette.current.surface.onColor,
                shape = RoundedCornerShape(
                    bottomStart = 8.dp,
                    bottomEnd = 8.dp
                ),
                scrollState = rememberScrollState(),
                matchAnchorWidth = true,
            ) {

                dropdownList.forEach { it ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = stringResource(it.name.toStringResource()),
                                style = LocalTypography.current.quaternary.regular,
                                color = LocalPalette.current.textFamily.body,
                                fontSize = 18.sp
                            )
                        },
                        colors = MenuDefaults.itemColors().copy(
                            textColor = LocalPalette.current.textFamily.body,
                        ),
                        onClick = {
                            expanded = false
                            onChange(it)
                        },
                    )
                }

            }
        }

    }

}

@Composable
@Preview
fun GhostNameWrapperPreview(
    objectivesViewModel: ObjectivesViewModel = viewModel(factory = ObjectivesViewModel.Factory)
) {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        NameWrapper(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = GhostName(
                name = GhostNameResources.Name.ALEX,
                priority = GhostName.NamePriority.FIRST,
                gender = GhostName.Gender.MALE
            ),
            dropdownList = objectivesViewModel.fetchAllFirstNames(),
            placeholder = "First Name"
        ) { }
    }
}

@Composable
@Preview
fun GhostNameContentPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        GhostNameContent(
            objectivesViewModel = viewModel(factory = ObjectivesViewModel.Factory),
        )
    }
}