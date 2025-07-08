package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                    .fillMaxWidth(),
                contentColor = LocalPalette.current.surface.onColor,
                containerColor = LocalPalette.current.surface.onColor,
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
                        .sizeIn(maxWidth = 48.dp, maxHeight = 48.dp)
                        .padding(8.dp),
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
                            .sizeIn(maxWidth = 48.dp, maxHeight = 48.dp)
                            .padding(8.dp),
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

            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Spacer(Modifier.height(12.dp))
                            Text("Drawer Title", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
                            HorizontalDivider()

                            Text("Section 1", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .height(48.dp)
                                    .wrapContentWidth()
                                    .padding(4.dp),
                                label = { Text("Settings") },
                                selected = false,
                                icon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_nav_tasks),
                                        contentDescription = null) },
                                badge = { Text("20") }, // Placeholder
                                onClick = { /* Handle click */ }
                            )
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .height(48.dp)
                                    .wrapContentWidth()
                                    .padding(4.dp),
                                label = { Text("Settings") },
                                selected = false,
                                icon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_nav_tasks),
                                        contentDescription = null) },
                                badge = { Text("20") }, // Placeholder
                                onClick = { /* Handle click */ }
                            )

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .height(48.dp)
                                    .wrapContentWidth()
                                    .padding(4.dp),
                                label = { Text("Settings") },
                                selected = false,
                                icon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_nav_tasks),
                                        contentDescription = null) },
                                badge = { Text("20") }, // Placeholder
                                onClick = { /* Handle click */ }
                            )
                            NavigationDrawerItem(
                                modifier = Modifier
                                    .height(48.dp)
                                    .wrapContentWidth()
                                    .padding(4.dp),
                                label = { Text("Settings") },
                                selected = false,
                                icon = {
                                    Icon(
                                        painter = painterResource(R.drawable.icon_nav_tasks),
                                        contentDescription = null) },
                                badge = { Text("20") }, // Placeholder
                                onClick = { /* Handle click */ }
                            )

                            Spacer(Modifier.height(12.dp))
                        }
                    }
                },
                drawerState = rememberDrawerState,
            ) {

                content()

            }
        }
    }

}

private enum class Destination(
    val route: String,
    @DrawableRes val icon: Int,
    val navOptions: NavOptions? = null
) {
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
    INVESTIGATION(
        NavRoute.SCREEN_INVESTIGATION.route,
        R.drawable.icon_nav_evidence,
        NavOptions.Builder()
            .setPopUpTo(NavRoute.SCREEN_INVESTIGATION.route, inclusive = true)
            .setLaunchSingleTop(true)
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