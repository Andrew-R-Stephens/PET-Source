package com.tritiumgaming.core.ui.mappers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import com.tritiumgaming.core.ui.icon.LogoPatreonIcon
import com.tritiumgaming.core.ui.icon.LogoPhasmophobiaIcon
import com.tritiumgaming.core.ui.icon.NewsIcon
import com.tritiumgaming.core.ui.icon.NotifyIcon
import com.tritiumgaming.core.ui.icon.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.PersonIcon
import com.tritiumgaming.core.ui.icon.ReviewIcon
import com.tritiumgaming.core.ui.icon.StoreIcon
import com.tritiumgaming.core.ui.icon.TranslateIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.shared.core.ui.mappers.IconResources.IconResource

@Composable
@JvmName("IconFromComposeColor")
fun IconResource.ToComposable(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults()
) {

    when (this) {
        IconResource.ACTION_PAN -> ActionPanIcon(modifier, colors)
        IconResource.ACTION_ZOOM -> ActionZoomIcon(modifier, colors)
        IconResource.ARROW_60_LEFT -> Arrow60LeftIcon(modifier, colors)
        IconResource.ARROW_60_RIGHT -> Arrow60RightIcon(modifier, colors)
        IconResource.BUTTON_SCRATCHED -> ButtonScratchedIcon(modifier, colors)
        IconResource.DISCORD -> DiscordIcon(modifier, colors)
        IconResource.GEAR -> GearIcon(modifier, colors)
        IconResource.GLOBE -> GlobeIcon(modifier, colors)
        IconResource.ICON_LOGO_APP -> LogoAppIcon(modifier, colors)
        IconResource.ICON_LOGO_PHASMOPHOBIA -> LogoPhasmophobiaIcon(modifier, colors)
        IconResource.INFO -> InfoIcon(modifier, colors)
        IconResource.HAMBURGER_MENU -> HamburgerMenuIcon(modifier, colors)
        IconResource.NEWS -> NewsIcon(modifier, colors)
        IconResource.NOTIFY -> NotifyIcon(modifier, colors)
        IconResource.OPEN_IN_NEW -> OpenInNewIcon(modifier, colors)
        IconResource.PERSON -> PersonIcon(modifier, colors)
        IconResource.REVIEW -> ReviewIcon(modifier, colors)
        IconResource.STORE -> StoreIcon(modifier, colors)
        IconResource.TRANSLATE -> TranslateIcon(modifier, colors)
        IconResource.PATREON -> LogoPatreonIcon(modifier, colors)
    }
}
