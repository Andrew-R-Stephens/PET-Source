package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AppUpdateManagerService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.LocalPalettesMap
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.ClassicTypography
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.types.LocalTypographiesMap
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.MainMenuViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.NewsletterViewModel

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
class MainMenuActivity : PETActivity(), AppUpdateManagerService {

    private val mainMenuViewModel: MainMenuViewModel by viewModels { MainMenuViewModel.Factory }
    private val newsLetterViewModel: NewsletterViewModel
        by viewModels { NewsletterViewModel.Factory }

    override var appUpdateManager: AppUpdateManager? = null
    override var updateType: Int = IMMEDIATE
    override var activityUpdateResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) {
                    result: androidx.activity.result.ActivityResult ->
                // handle callback
                if (result.resultCode != RESULT_OK) {
                    print("Update flow failed! Result code: " + result.resultCode);
                }
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_mainmenu)

        createConsentInformation()
    }

    public override fun loadPreferences() {
        super.loadPreferences()

        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            globalPreferencesViewModel.incrementAppTimesOpened()

            //set language
            if (setLanguage(globalPreferencesViewModel.currentLanguageCode.value)) {
                recreate()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdate()
    }

}