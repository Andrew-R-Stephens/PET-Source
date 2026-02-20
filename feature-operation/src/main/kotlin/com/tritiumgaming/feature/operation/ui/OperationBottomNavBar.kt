package com.tritiumgaming.feature.operation.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.HamburgerMenuIcon
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.screens.AppScreen
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.getHamburgerMenuVector
import com.tritiumgaming.shared.core.navigation.NavRoute
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun OperationNavigationBar(
    navController: NavHostController = rememberNavController(),
    windowInsets: WindowInsets = WindowInsets(),
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val rememberDrawerState = rememberDrawerState(DrawerValue.Closed)

    var selectedDestination by rememberSaveable {
        mutableStateOf(navController.currentDestination?.route)
    }

    val destinations = listOf(
        Destination.INVESTIGATION,
        Destination.MISSIONS,
        Destination.MAPS
    )

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    Log.d("Device Config", deviceConfiguration.name)
    val bottomNavigationBarEnabled =
        when(deviceConfiguration) {
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> false
            else -> true
        }

    Scaffold(
        contentWindowInsets = windowInsets,
        bottomBar = {
            if (bottomNavigationBarEnabled) {
                OperationNavigationBottomBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    navController = navController,
                    scope = scope,
                    rememberDrawerState = rememberDrawerState,
                    onChangeDestination = {
                        selectedDestination = it
                    },
                    destinations = destinations,
                    windowInsets = windowInsets
                )
            }
        }
    ) { contentPadding ->

        if (bottomNavigationBarEnabled) {

            AppScreen(
                modifier = Modifier
            ) {

                OperationNavigationDrawer(
                    navController = navController,
                    drawerState = rememberDrawerState,
                    modifier = Modifier.padding(contentPadding)
                ) {
                    content()
                }
            }

        } else {

            OperationNavigationRail(
                navController = navController,
                scope = scope,
                rememberDrawerState = rememberDrawerState,
                onChangeDestination = {
                    selectedDestination = it
                },
                destinations = destinations,
                windowInsets = windowInsets
            ) {
                AppScreen {

                    OperationNavigationDrawer(
                        navController = navController,
                        drawerState = rememberDrawerState,
                        modifier = Modifier
                    ) {
                        content()
                    }
                }

            }
        }

    }
}

@Composable
private fun OperationNavigationBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope,
    rememberDrawerState: DrawerState,
    onChangeDestination: (route: String) -> Unit = {},
    destinations: List<Destination>,
    windowInsets: WindowInsets
) {
    NavigationBar(
        modifier = modifier,
        contentColor = LocalPalette.current.surfaceContainer,
        containerColor = LocalPalette.current.surfaceContainer,
        windowInsets = windowInsets.exclude(
            insets = WindowInsets(
                bottom = windowInsets.asPaddingValues().calculateBottomPadding()
            )
        )
    ) {

        NavigationBarItem(
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent,
                selectedIconColor = Color.Transparent,
                unselectedIconColor = Color.Transparent
            ),
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
                HamburgerMenuIcon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    colors = if (rememberDrawerState.isOpen) {
                            IconVectorColors.defaults(
                                fillColor = LocalPalette.current.surfaceContainer,
                                strokeColor = LocalPalette.current.primary
                            )
                        } else {
                            IconVectorColors.defaults(
                                fillColor = LocalPalette.current.surfaceContainer,
                                strokeColor = LocalPalette.current.onSurface
                            )
                        },
                )
            },
            label = {

            }
        )

        destinations.forEach { destination ->

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = LocalPalette.current.primary,
                    unselectedIconColor = LocalPalette.current.onSurface
                ),
                selected = navController.currentDestination?.route == destination.route,
                enabled = navController.currentDestination?.route != destination.route,
                onClick = {
                    navController.navigate(
                        route = destination.route,
                        navOptions = destination.navOptions,
                    )
                    onChangeDestination(destination.route)
                },
                icon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.CenterVertically),
                        painter = painterResource(destination.icon),
                        contentDescription = destination.name,
                        tint =
                            if (navController.currentDestination?.route == destination.route)
                                LocalPalette.current.primary
                            else
                                LocalPalette.current.onSurface
                    )
                }
            )

        }

    }
}

@Composable
private fun OperationNavigationRail(
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope,
    rememberDrawerState: DrawerState,
    onChangeDestination: (route: String) -> Unit = {},
    destinations: List<Destination>,
    windowInsets: WindowInsets,
    content: @Composable () -> Unit
) {

    Row {

        NavigationRail(
            modifier = Modifier,
            contentColor = LocalPalette.current.surfaceContainer,
            containerColor = LocalPalette.current.surfaceContainer,
            windowInsets = windowInsets
        ) {

            NavigationRailItem(
                colors = NavigationRailItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedIconColor = Color.Transparent,
                    unselectedIconColor = Color.Transparent
                ),
                modifier = Modifier,
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

                    Image(
                        modifier = Modifier
                            .size(24.dp),
                        imageVector =
                            getHamburgerMenuVector(
                                colors =
                                    if (rememberDrawerState.isOpen) {
                                        IconVectorColors.defaults(
                                            fillColor =LocalPalette.current.surfaceContainer,
                                            strokeColor = LocalPalette.current.primary
                                        )
                                    } else {
                                        IconVectorColors.defaults(
                                            fillColor =LocalPalette.current.surfaceContainer,
                                            strokeColor = LocalPalette.current.onSurface
                                        )
                                    }
                            ),
                        contentDescription = "Menu Drawer"
                    )
                }
            )

            destinations.forEachIndexed { index, destination ->

                NavigationRailItem(
                    colors = NavigationRailItemDefaults.colors(
                        indicatorColor = Color.Transparent,
                        selectedIconColor = LocalPalette.current.primary,
                        unselectedIconColor = LocalPalette.current.onSurface
                    ),
                    modifier = Modifier,
                    selected = navController.currentDestination?.route == destination.route,
                    enabled = navController.currentDestination?.route != destination.route,
                    onClick = {
                        navController.navigate(
                            route = destination.route,
                            navOptions = destination.navOptions,
                        )
                        onChangeDestination(destination.route)
                    },
                    icon = {
                        Icon(
                            modifier = Modifier
                                .size(24.dp),
                            painter = painterResource(destination.icon),
                            contentDescription = destination.name,
                            tint =
                                if (navController.currentDestination?.route == destination.route)
                                    LocalPalette.current.primary
                                else
                                    LocalPalette.current.onSurface
                        )
                    }
                )
            }

        }

        content()

    }

}

private enum class Destination(
    val route: String,
    @field:DrawableRes val icon: Int,
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