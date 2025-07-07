package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen

@Composable
@Preview
private fun InvestigationScreenPreview() {
    OperationScreen {}
}

@Composable
fun InvestigationScreen(
    navController: NavHostController = rememberNavController(),
    content: @Composable () -> Unit
) {

    OperationScreen(
        navController = navController,
        content = { content() }
    )

}
