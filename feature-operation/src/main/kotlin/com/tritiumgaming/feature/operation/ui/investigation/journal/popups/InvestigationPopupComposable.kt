package com.tritiumgaming.feature.operation.ui.investigation.journal.popups

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
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.tritiumgaming.core.common.util.FontUtils.replaceHTMLFontColor
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.common.menus.NavHeaderComposableParams
import com.tritiumgaming.core.ui.common.menus.NavigationHeaderComposable
import com.tritiumgaming.core.ui.common.menus.PETImageButtonType
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.app.mappers.toDrawableResource
import com.tritiumgaming.feature.operation.app.mappers.toStringResource
import com.tritiumgaming.shared.operation.domain.popup.model.EvidencePopupRecord
import com.tritiumgaming.shared.operation.domain.popup.model.GhostPopupRecord
import kotlinx.coroutines.flow.StateFlow

@Composable
fun InvestigationPopup(
    onDismiss: () -> Unit = {},
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
                params = NavHeaderComposableParams(
                    leftType = PETImageButtonType.BACK,
                    leftOnClick = { onDismiss() }
                )
            )

            content()

            // TODO Banner Ad
        }
    }
}

@Composable
fun EvidencePopup(
    state: StateFlow<InvestigationPopupUiState>,
    onDismiss: () -> Unit
) {
    val recordState by state.collectAsStateWithLifecycle()
    if(!recordState.isShown || recordState.evidencePopupRecord == null) return

    InvestigationPopup(
        onDismiss = { onDismiss() }
    ) {
        EvidencePopupContent(recordState.evidencePopupRecord!!)
    }
}

@Composable
fun GhostPopup(
    state: StateFlow<InvestigationPopupUiState>,
    onDismiss: () -> Unit
) {
    val recordState by state.collectAsStateWithLifecycle()
    if(!recordState.isShown || recordState.ghostPopupRecord == null) return

    InvestigationPopup(
        onDismiss = { onDismiss() }
    ) {
        GhostPopupContent(recordState.ghostPopupRecord!!)
    }
}

@Composable
private fun EvidencePopupContent(
    evidenceRecord: EvidencePopupRecord
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            modifier = Modifier
                .padding(PaddingValues(top = 8.dp))
                .wrapContentHeight(),
            text = stringResource(evidenceRecord.name.toStringResource()),
            fontSize = 30.sp,
            color = LocalPalette.current.textFamily.body,
            style = LocalTypography.current.primary.regular
        )

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
private fun GhostPopupContent(
    ghostRecord: GhostPopupRecord
) {
    ghostRecord

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            modifier = Modifier
                .padding(PaddingValues(top = 8.dp))
                .wrapContentHeight(),
            text = stringResource(ghostRecord.name.toStringResource()),
            fontSize = 30.sp,
            color = LocalPalette.current.textFamily.body,
            style = LocalTypography.current.primary.regular
        )

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
        BasicText(
            text = stringResource(textRes).uppercase(),
            style = LocalTypography.current.secondary.regular.copy(
                color = LocalPalette.current.textFamily.body,
                textAlign = TextAlign.Center
            ),
            autoSize = TextAutoSize.StepBased(minFontSize = 12.sp, maxFontSize = 48.sp, stepSize = 1.6.sp)
        )
        /*AutoResizedText(
            containerModifier = Modifier
                .fillMaxSize(),
            text = stringResource(textRes),
            color = LocalPalette.current.textFamily.body,
            style = LocalTypography.current.secondary.regular,
            autoResizeStyle = AutoResizedStyleType.SQUEEZE,
            textCase = TextCase.Uppercase,
            textAlign = TextAlign.Center
        )*/
    }
}

@Composable
private fun OverviewPage(
    evidenceRecord: EvidencePopupRecord
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
                text = stringResource(evidenceRecord.cost.toStringResource()),
                fontSize = 30.sp,
                color = LocalPalette.current.textFamily.body,
                style = LocalTypography.current.primary.regular
            )
            
            val details = AnnotatedString.Companion.fromHtml(
                replaceHTMLFontColor(
                    stringResource(evidenceRecord.tiers[0].description.toStringResource()),
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
            animatedGifRes = evidenceRecord.tiers[0].animation.toDrawableResource()
        )

    }
}

@Composable
private fun EquipmentPage() {
    Column {

    }
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