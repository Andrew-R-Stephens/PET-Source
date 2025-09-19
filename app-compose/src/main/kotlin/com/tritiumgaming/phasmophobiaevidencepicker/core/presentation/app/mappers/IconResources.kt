package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tritiumgaming.core.ui.icon.ActionPanIcon
import com.tritiumgaming.core.ui.icon.ActionZoomIcon
import com.tritiumgaming.core.ui.icon.Arrow60LeftIcon
import com.tritiumgaming.core.ui.icon.Arrow60RightIcon
import com.tritiumgaming.core.ui.icon.ButtonScratchedIcon
import com.tritiumgaming.core.ui.icon.DiscordIcon
import com.tritiumgaming.core.ui.icon.GearIcon
import com.tritiumgaming.core.ui.icon.GlobeIcon
import com.tritiumgaming.core.ui.icon.HamburgerMenuIcon
import com.tritiumgaming.core.ui.icon.InfoIcon
import com.tritiumgaming.core.ui.icon.LogoAppIcon
import com.tritiumgaming.core.ui.icon.LogoPhasmophobiaIcon
import com.tritiumgaming.core.ui.icon.NewsIcon
import com.tritiumgaming.core.ui.icon.NotifyIcon
import com.tritiumgaming.core.ui.icon.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.PersonIcon
import com.tritiumgaming.core.ui.icon.ReviewIcon
import com.tritiumgaming.core.ui.icon.StoreIcon
import com.tritiumgaming.core.ui.icon.TranslateIcon
import com.tritiumgaming.shared.core.domain.icons.IconResources.IconResource

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
