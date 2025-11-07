package com.tritiumgaming.feature.operation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette


@Composable
@Preview
private fun OperationScreenPreview() {
    OperationScreen {}
}

@Composable
fun OperationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    content: @Composable (modifier: Modifier) -> Unit
) {

    OperationNavigationBar(
        navController = navController
    ) {
        content(modifier)
    }

}
