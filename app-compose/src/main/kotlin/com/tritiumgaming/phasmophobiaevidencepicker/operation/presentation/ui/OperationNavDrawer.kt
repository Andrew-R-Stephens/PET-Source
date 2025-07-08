package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R

@Composable
fun OperationNavDrawer(
    content: @Composable () -> Unit,
    drawerState: DrawerState = DrawerState(DrawerValue.Closed)
) {

    ModalNavigationDrawer(
        gesturesEnabled = false,
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
        drawerState = drawerState,
    ) {

        content()

    }
}