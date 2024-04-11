package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import android.content.Context
import android.util.TypedValue
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.NavigationRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavDeepLinkBuilder
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.views.account.AccountIconView


@Composable
@Preview
fun StartScreenSettingsIconDropdown(
    @DrawableRes primaryButtonIcon: Int = R.drawable.icon_gear,
    @NavigationRes navGraphId: Int = R.navigation.titlescreen_navgraph,
    @IntegerRes navigationRoutes: Array<Int> = arrayOf(R.id.appSettingsFragment)
) {
    val localContext: Context = LocalContext.current

    val navDeepLinkBuilder = NavDeepLinkBuilder(localContext).setGraph(navGraphId)

    var expanded by remember { mutableStateOf(true) }

    val typedValue = TypedValue()
    localContext.theme.resolveAttribute(
        R.attr.backgroundColorOnBackground,
        typedValue,
        true
    )
    val backgroundColorOnBackground = Color(typedValue.data)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .background(Color.Transparent)
    ) {
        IconButton(
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                painter = painterResource(id = primaryButtonIcon),
                tint = Color.Unspecified,
                contentDescription = "Temp",
                modifier = Modifier
                    .size(48.dp)
            )
        }

        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                surface = backgroundColorOnBackground,
                background = Color.Transparent,
                onBackground = Color.Transparent,
                surfaceContainer = Color.Transparent,
                onPrimary = Color.Transparent,
                surfaceContainerLowest = Color.Transparent,

            ),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                IconButton(
                    onClick = {
                        navDeepLinkBuilder
                            .setDestination(navigationRoutes[0])
                            .createPendingIntent().send()
                    }) {
                    Icon(
                        painterResource(id = R.drawable.icon_gear),
                        tint = Color.Unspecified,
                        contentDescription = "Temp",
                        modifier = Modifier
                            .size(48.dp)
                    )
                }
                IconButton(
                    onClick = {
                        navDeepLinkBuilder
                            .setDestination(navigationRoutes[1])
                            .createPendingIntent().send()
                    }) {
                    Icon(
                        painterResource(id = R.drawable.icon_globe),
                        tint = Color.Unspecified,
                        contentDescription = "Temp",
                        modifier = Modifier
                            .size(48.dp)
                    )
                }
                IconButton(
                    onClick = {
                        navDeepLinkBuilder
                            .setDestination(navigationRoutes[2])
                            .createPendingIntent().send()
                    },
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp)) {
                    AndroidView(
                        modifier = Modifier.fillMaxSize(), // Occupy the max size in the Compose UI tree
                        factory = { context ->
                            // Creates view
                            AccountIconView(context)
                        },
                        update = { view ->
                            // View's been inflated or state read in this block has been updated
                            // Add logic here if necessary

                            // As selectedItem is read here, AndroidView will recompose
                            // whenever the state changes
                            // Example of Compose -> View communication
                            view.invalidate()
                        }
                    )
                }
            }
        }
    }
}

fun setStartScreenSettingsIconDropdown(
    recipientView: ComposeView?,
    @DrawableRes primaryButtonIcon: Int,
    @NavigationRes navGraphId: Int,
    @IntegerRes navigationRoutes: Array<Int>
) {
    recipientView?.setContent { StartScreenSettingsIconDropdown(
        primaryButtonIcon = primaryButtonIcon,
        navGraphId = navGraphId,
        navigationRoutes = navigationRoutes
    ) }
}