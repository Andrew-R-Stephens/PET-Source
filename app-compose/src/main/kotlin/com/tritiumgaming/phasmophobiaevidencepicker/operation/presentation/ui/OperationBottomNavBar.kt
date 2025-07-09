package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.screens.PETScreen
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.HamburgerMenuIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.getHamburgerMenuVector
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import kotlinx.coroutines.launch

@Composable
fun OperationBottomNavBar(
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val rememberDrawerState = rememberDrawerState(DrawerValue.Closed)

    var selectedDestination by rememberSaveable {
        mutableStateOf(navController.currentDestination?.route) }

    var destinations = listOf<Destination>(
        Destination.MISSIONS,
        Destination.INVESTIGATION,
        Destination.MAPS
    )

    //println("Selected at top: $selectedDestination")

    Scaffold(
        bottomBar = {

            NavigationBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp, max = 80.dp)
                    .wrapContentHeight(),
                contentColor = LocalPalette.current.surface.color,
                containerColor = LocalPalette.current.surface.color,
                windowInsets = NavigationBarDefaults.windowInsets,
            ) {

                NavigationBarItem(
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = Color.Transparent,
                        unselectedIconColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .weight(1f, true)
                        .sizeIn(maxWidth = 24.dp, maxHeight = 24.dp),
                    selected = rememberDrawerState.isOpen,
                    onClick = {
                        scope.launch {
                            if (rememberDrawerState.isClosed) {
                                rememberDrawerState.open()
                            } else {
                                rememberDrawerState.close()
                            }
                        }
                    },
                    icon = {
                        HamburgerMenuIcon()
                        Image(
                            modifier = Modifier,
                            imageVector =
                                getHamburgerMenuVector(
                                    groupColors =
                                        if (rememberDrawerState.isOpen) {
                                            listOf(
                                                LocalPalette.current.surface.color,
                                                LocalPalette.current.textFamily.primary
                                            )
                                        } else {
                                            listOf(
                                                LocalPalette.current.surface.color,
                                                LocalPalette.current.textFamily.body
                                            )
                                        }
                            ),
                            contentDescription = "Menu Drawer"
                        )
                    }
                )

                destinations.forEachIndexed { index, destination ->

                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = LocalPalette.current.textFamily.primary,
                            unselectedIconColor = LocalPalette.current.textFamily.body
                        ),
                        modifier = Modifier
                            .weight(1f, true)
                            .sizeIn(maxWidth = 28.dp, maxHeight = 28.dp),
                        selected = navController.currentDestination?.route == destination.route,
                        enabled = navController.currentDestination?.route != destination.route,
                        onClick = {
                            navController.navigate(
                                route = destination.route,
                                navOptions = destination.navOptions,
                            )
                            selectedDestination = destination.route
                        },
                        icon = {
                            Icon(
                                modifier = Modifier,
                                painter = painterResource(destination.icon),
                                contentDescription = destination.name,
                                tint =
                                    if (navController.currentDestination?.route == destination.route)
                                        LocalPalette.current.textFamily.primary
                                    else
                                        LocalPalette.current.textFamily.body
                            )
                        }
                    )

                }

            }
        }
    ) { contentPadding ->

        PETScreen(
            modifier = Modifier.padding(bottom = contentPadding.calculateBottomPadding()),
        ) {

            OperationNavDrawer(
                navController = navController,
                content = content,
                drawerState = rememberDrawerState
            )

        }
    }

}

private enum class Destination(
    val route: String,
    @DrawableRes val icon: Int,
    val navOptions: NavOptions? = null
) {
    INVESTIGATION(
        NavRoute.SCREEN_INVESTIGATION.route,
        R.drawable.icon_nav_evidence,
        NavOptions.Builder()
            .setPopUpTo(NavRoute.SCREEN_INVESTIGATION.route, inclusive = true)
            .setLaunchSingleTop(true)
            .build()
    ),
    MISSIONS(
        NavRoute.SCREEN_MISSIONS.route,
        R.drawable.icon_nav_tasks,
        NavOptions.Builder()
            .setPopUpTo(NavRoute.SCREEN_INVESTIGATION.route, inclusive = false)
            .setLaunchSingleTop(true)
            .setEnterAnim(0)
            .setExitAnim(0)
            .setPopEnterAnim(0)
            .setPopExitAnim(0)
            .build()
    ),
    MAPS(
        NavRoute.SCREEN_MAPS_MENU.route,
        R.drawable.icon_nav_mapmenu2,
        NavOptions.Builder()
            .setPopUpTo(NavRoute.SCREEN_INVESTIGATION.route, inclusive = false)
            .setLaunchSingleTop(true)
            .setEnterAnim(0)
            .setExitAnim(0)
            .setPopEnterAnim(0)
            .setPopExitAnim(0)
            .build()
    ),
}