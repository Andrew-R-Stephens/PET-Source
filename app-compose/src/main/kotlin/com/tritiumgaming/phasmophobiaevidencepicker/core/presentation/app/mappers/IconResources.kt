package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.ActionPan
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.ActionZoom
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Arrow60Left
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Arrow60Right
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.ButtonScratched
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Discord
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Gear
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Globe
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.IconLogoApp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.IconLogoPhasmophobia
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Info
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Menu
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.News
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Notify
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.OpenInNew
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Person
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Review
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Store
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.Translate

@Composable
fun IconResource.ToComposable(
    modifier: Modifier = Modifier,
    colors: List<Color> = emptyList()
) =
    when (this) {
        IconResource.ACTION_PAN -> ActionPan(modifier)
        IconResource.ACTION_ZOOM -> ActionZoom(modifier)
        IconResource.ARROW_60_LEFT -> Arrow60Left(modifier)
        IconResource.ARROW_60_RIGHT -> Arrow60Right(modifier)
        IconResource.BUTTON_SCRATCHED -> ButtonScratched(modifier)
        IconResource.DISCORD -> Discord(modifier, colors)
        IconResource.GEAR -> Gear(modifier)
        IconResource.GLOBE -> Globe(modifier)
        IconResource.ICON_LOGO_APP -> IconLogoApp(modifier)
        IconResource.ICON_LOGO_PHASMOPHOBIA -> IconLogoPhasmophobia(modifier)
        IconResource.INFO -> Info(modifier)
        IconResource.MENU -> Menu(modifier)
        IconResource.NEWS -> News(modifier)
        IconResource.NOTIFY -> Notify(modifier)
        IconResource.OPEN_IN_NEW -> OpenInNew(modifier)
        IconResource.PERSON -> Person(modifier)
        IconResource.REVIEW -> Review(modifier)
        IconResource.STORE -> Store(modifier)
        IconResource.TRANSLATE -> Translate(modifier)
    }
