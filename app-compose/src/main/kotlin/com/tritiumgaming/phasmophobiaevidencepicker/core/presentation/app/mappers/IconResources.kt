package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.ActionPan
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.ActionZoom
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Arrow60Left
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Arrow60Right
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.ButtonScratched
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Discord
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Gear
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Globe
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.IconLogoApp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.IconLogoPhasmophobia
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Info
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Menu
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.News
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Notify
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.OpenInNew
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Person
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Review
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Store
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.vectors.Translate

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
