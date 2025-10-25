package com.tritiumgaming.feature.operation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.shared.core.navigation.NavRoute

@Composable
fun OperationNavigationDrawer(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = DrawerState(DrawerValue.Closed),
    content: @Composable () -> Unit,
) {

    ModalNavigationDrawer(
        modifier = modifier,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = LocalPalette.current.surface.onColor,
                drawerTonalElevation = 16.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(12.dp))

                    NavigationDrawerItem(
                        modifier = Modifier
                            .height(48.dp)
                            .wrapContentWidth()
                            .padding(8.dp),
                        label = {
                            Text(
                                text = stringResource(R.string.general_navigation_home),
                                style = LocalTypography.current.quaternary.bold.copy(
                                    fontSize = 18.sp,
                                    color = LocalPalette.current.textFamily.body
                                )
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.ic_nav_home),
                                contentDescription = null,
                                tint = LocalPalette.current.textFamily.body
                            )
                        },
                        onClick = {
                            navController.navigate(NavRoute.SCREEN_HOME.route)
                        }
                    )

                    Text(
                        text = stringResource(R.string.general_navigation_primary),
                        modifier = Modifier.padding(16.dp),
                        style = LocalTypography.current.primary.bold.copy (
                            fontSize = 24.sp,
                        ),
                        color = LocalPalette.current.textFamily.primary
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .height(48.dp)
                            .wrapContentWidth()
                            .padding(8.dp),
                        label = {
                            Text(
                                text = stringResource(R.string.investigation_section_title_evidence),
                                style = LocalTypography.current.quaternary.bold.copy(
                                    fontSize = 18.sp,
                                    color = LocalPalette.current.textFamily.body
                                )
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.icon_nav_evidence),
                                contentDescription = null,
                                tint = LocalPalette.current.textFamily.body
                            )
                        },
                        onClick = {
                            navController.navigate(NavRoute.SCREEN_INVESTIGATION.route)
                        }
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .height(48.dp)
                            .wrapContentWidth()
                            .padding(8.dp),
                        label = {
                            Text(
                                text = stringResource(R.string.general_tasks_button),
                                style = LocalTypography.current.quaternary.bold.copy(
                                    fontSize = 18.sp,
                                    color = LocalPalette.current.textFamily.body
                                )
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.icon_nav_tasks),
                                contentDescription = null,
                                tint = LocalPalette.current.textFamily.body
                            )
                        },
                        onClick = {
                            navController.navigate(NavRoute.SCREEN_MISSIONS.route)
                        }
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .height(48.dp)
                            .wrapContentWidth()
                            .padding(8.dp),
                        label = {
                            Text(
                                text = stringResource(R.string.general_maps_button),
                                style = LocalTypography.current.quaternary.bold.copy(
                                    fontSize = 18.sp,
                                    color = LocalPalette.current.textFamily.body
                                )
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.icon_nav_mapmenu2),
                                contentDescription = null,
                                tint = LocalPalette.current.textFamily.body
                            )
                        },
                        onClick = {
                            navController.navigate(NavRoute.SCREEN_MAPS_MENU.route)
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = stringResource(R.string.general_navigation_tools),
                        modifier = Modifier.padding(16.dp),
                        style = LocalTypography.current.primary.bold.copy (
                            fontSize = 24.sp,
                        ),
                        color = LocalPalette.current.textFamily.primary
                    )

                    NavigationDrawerItem(
                        modifier = Modifier
                            .height(48.dp)
                            .wrapContentWidth()
                            .padding(8.dp),
                        label = {
                            Text(
                                text = stringResource(R.string.general_codex_button),
                                style = LocalTypography.current.quaternary.bold.copy(
                                    fontSize = 18.sp,
                                    color = LocalPalette.current.textFamily.body
                                )
                            )
                        },
                        selected = false,
                        icon = {
                            Icon(
                                painter = painterResource(R.drawable.icon_nav_codex),
                                contentDescription = null,
                                tint = LocalPalette.current.textFamily.body
                            )
                        },
                        onClick = {
                            navController.navigate(NavRoute.SCREEN_CODEX_MENU.route)
                        }
                    )

                    Spacer(Modifier.height(12.dp))
                }
            }
        },
        drawerState = drawerState,
    ) {

        content()

    }
}