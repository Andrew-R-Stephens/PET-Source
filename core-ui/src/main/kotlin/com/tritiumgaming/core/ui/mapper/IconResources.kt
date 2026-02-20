package com.tritiumgaming.core.ui.mapper

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tritiumgaming.core.ui.icon.impl.base.ActionPanIcon
import com.tritiumgaming.core.ui.icon.impl.base.ActionZoomIcon
import com.tritiumgaming.core.ui.icon.impl.base.Arrow60LeftIcon
import com.tritiumgaming.core.ui.icon.impl.base.Arrow60RightIcon
import com.tritiumgaming.core.ui.icon.impl.base.ButtonScratchedIcon
import com.tritiumgaming.core.ui.icon.impl.base.DiscordIcon
import com.tritiumgaming.core.ui.icon.impl.base.GearIcon
import com.tritiumgaming.core.ui.icon.impl.base.GlobeIcon
import com.tritiumgaming.core.ui.icon.impl.base.HamburgerMenuIcon
import com.tritiumgaming.core.ui.icon.impl.base.InfoIcon
import com.tritiumgaming.core.ui.icon.impl.base.LogoAppIcon
import com.tritiumgaming.core.ui.icon.impl.base.LogoPatreonIcon
import com.tritiumgaming.core.ui.icon.impl.base.LogoPhasmophobiaIcon
import com.tritiumgaming.core.ui.icon.impl.base.NewsIcon
import com.tritiumgaming.core.ui.icon.impl.base.NotifyIcon
import com.tritiumgaming.core.ui.icon.impl.base.OpenInNewIcon
import com.tritiumgaming.core.ui.icon.impl.base.PersonIcon
import com.tritiumgaming.core.ui.icon.impl.base.ReviewIcon
import com.tritiumgaming.core.ui.icon.impl.base.StoreIcon
import com.tritiumgaming.core.ui.icon.impl.base.TranslateIcon
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
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
