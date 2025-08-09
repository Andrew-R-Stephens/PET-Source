package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.icons.IconResources.IconResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.ActionPanIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.ActionZoomIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.Arrow60LeftIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.Arrow60RightIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.ButtonScratchedIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.DiscordIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.GearIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.GlobeIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.HamburgerMenuIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.InfoIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.LogoAppIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.LogoPhasmophobiaIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.NewsIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.NotifyIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.OpenInNewIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.PersonIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.ReviewIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.StoreIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.TranslateIcon

@Composable
fun IconResource.ToComposable(
    modifier: Modifier = Modifier,
    colors: List<Color> = emptyList()
) =
    when (this) {
        IconResource.ACTION_PAN -> ActionPanIcon(modifier)
        IconResource.ACTION_ZOOM -> ActionZoomIcon(modifier)
        IconResource.ARROW_60_LEFT -> Arrow60LeftIcon(modifier, colors)
        IconResource.ARROW_60_RIGHT -> Arrow60RightIcon(modifier, colors)
        IconResource.BUTTON_SCRATCHED -> ButtonScratchedIcon(modifier)
        IconResource.DISCORD -> DiscordIcon(modifier, colors)
        IconResource.GEAR -> GearIcon(modifier)
        IconResource.GLOBE -> GlobeIcon(modifier)
        IconResource.ICON_LOGO_APP -> LogoAppIcon(modifier)
        IconResource.ICON_LOGO_PHASMOPHOBIA -> LogoPhasmophobiaIcon(modifier)
        IconResource.INFO -> InfoIcon(modifier)
        IconResource.HAMBURGER_MENU -> HamburgerMenuIcon(modifier)
        IconResource.NEWS -> NewsIcon(modifier)
        IconResource.NOTIFY -> NotifyIcon(modifier)
        IconResource.OPEN_IN_NEW -> OpenInNewIcon(modifier)
        IconResource.PERSON -> PersonIcon(modifier)
        IconResource.REVIEW -> ReviewIcon(modifier)
        IconResource.STORE -> StoreIcon(modifier)
        IconResource.TRANSLATE -> TranslateIcon(modifier)
    }
