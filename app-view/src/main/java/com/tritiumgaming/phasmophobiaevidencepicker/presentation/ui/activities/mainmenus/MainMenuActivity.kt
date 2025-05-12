package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.impl.AppUpdateManagerService
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETActivity

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
class MainMenuActivity : PETActivity(), AppUpdateManagerService {

    /*private val mainMenuViewModel: MainMenuViewModel by viewModels { MainMenuViewModel.Factory }
    private val newsLetterViewModel: NewsletterViewModel
        by viewModels { NewsletterViewModel.Factory }*/

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

        createConsentInformation(this)
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdate()
    }

}