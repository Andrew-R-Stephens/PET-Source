package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.evidence.views.popups

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidencePopupRepository
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedStyleType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.AutoResizedText
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavHeaderComposableParams
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.NavigationHeaderComposable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.common.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.InvestigationViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.FontUtils.replaceHTMLFontColor
import com.tritiumgaming.phasmophobiaevidencepicker.util.TextCase

@Composable
@Preview
private fun PopupPreviews(
    investigationViewModel: InvestigationViewModel? = null
) {

    SelectiveTheme(
        palette = ClassicPalette
    ) {
        EvidencePopup()
    }

}

@Composable
fun InvestigationPopup(
    content: @Composable () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = LocalPalette.current.surface.color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavigationHeaderComposable(
                NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    rightType = PETImageButtonType.FORWARD
                )
            )
            content()
            // TODO Banner Ad
        }
    }
}

@Composable
fun EvidencePopup(
    evidenceRecord: EvidencePopupRepository.EvidencePopupRecord? = null
) {

    InvestigationPopup {
        EvidencePopupContent(evidenceRecord)
    }

}

@Composable
private fun EvidencePopupContent(
    evidenceRecord: EvidencePopupRepository.EvidencePopupRecord? = null
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        var primaryPageActive = remember { mutableIntStateOf(0) }

        Row(
            modifier = Modifier
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            PrimaryPageButton(
                pageType = PrimaryPageType.OVERVIEW,
                activePage = primaryPageActive,
                onClick = {
                    primaryPageActive.intValue = PrimaryPageType.OVERVIEW.pageIndex
                }
            )

            PrimaryPageButton(
                pageType = PrimaryPageType.EQUIPMENT,
                activePage = primaryPageActive,
                onClick = {
                    primaryPageActive.intValue = PrimaryPageType.EQUIPMENT.pageIndex
                }
            )
        }

        when(primaryPageActive.intValue) {
            PrimaryPageType.OVERVIEW.pageIndex -> OverviewPage(evidenceRecord)
            PrimaryPageType.EQUIPMENT.pageIndex -> EquipmentPage()
        }
    }

}

@Composable
fun RowScope.PrimaryPageButton(
    modifier: Modifier = Modifier,
    pageType: PrimaryPageType,
    activePage: MutableIntState,
    @StringRes textRes: Int = pageType.label,
    onClick: () -> Unit = { }
    ) {

    val rememberActivePage by remember { activePage }

    val context = LocalContext.current

    Button(
        modifier = modifier
            .padding(8.dp)
            .weight(1f)
            .fillMaxWidth(.35f)
            .heightIn(max = 48.dp)
            .padding(2.dp),
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = LocalPalette.current.selectedColor2,
            containerColor = LocalPalette.current.surface.color
        ),
        onClick = {
            onClick()
            Toast.makeText(context, "$rememberActivePage", Toast.LENGTH_SHORT).show()
        },
        enabled = rememberActivePage != pageType.pageIndex
    ) {
        AutoResizedText(
            containerModifier = Modifier
                .fillMaxSize(),
            text = stringResource(textRes),
            color = LocalPalette.current.textFamily.body,
            style = LocalTypography.current.secondary.regular,
            autoResizeStyle = AutoResizedStyleType.SQUEEZE,
            textCase = TextCase.Uppercase,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun OverviewPage(
    evidenceRecord: EvidencePopupRepository.EvidencePopupRecord? = null
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier
                    .padding(PaddingValues(top = 8.dp))
                    .wrapContentHeight(),
                text = "${integerResource(R.integer.equipment_requirement_buycost_dots)}",
                fontSize = 30.sp,
                color = LocalPalette.current.textFamily.body,
                style = LocalTypography.current.primary.regular
            )
            
            val details = AnnotatedString.Companion.fromHtml(
                replaceHTMLFontColor(
                    stringResource(R.string.shop_equipment_dots_data_info_1),
                    "#CC3C3C", LocalPalette.current.textFamily.emphasis
                )
            )

            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight(),
                text = details,
                fontSize = 20.sp,
                color = LocalPalette.current.textFamily.body,
                style = LocalTypography.current.quaternary.regular
            )
        }

        AnimatedGif(
            modifier = Modifier
                .fillMaxHeight(.4f)
                .weight(1f),
            animatedGifRes = /*evidenceRecord?.animations[0] ?: */R.drawable.anim_dots_1
        )

    }
}

@Composable
private fun EquipmentPage() {
    Column {

    }
}

@Composable
fun GhostPopup() {

    InvestigationPopup {
        GhostPopupContent()
    }

}

@Composable
private fun GhostPopupContent() {
    Text(text="Ghost Popup", color = LocalPalette.current.textFamily.body)
}

enum class PrimaryPageType(val pageIndex: Int, @StringRes val label: Int) {
    OVERVIEW(0, R.string.evidence_section_overview),
    EQUIPMENT(1, R.string.equipment_section_equipment)
}

@Composable
fun AnimatedGif(
    modifier: Modifier = Modifier,
    @DrawableRes animatedGifRes: Int,
    contentScale: ContentScale = ContentScale.FillWidth
) {

    AsyncImage(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        model = animatedGifRes,
        contentDescription = "Gradient background",
        contentScale = contentScale
    )

}