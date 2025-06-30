package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.ump.ConsentInformation
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.RootNavigation
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.AppUpdateManagerService
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.ConsentManagementService
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.activities.impl.FirebaseAnalyticsService
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.GlobalPreferencesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.appsettings.ThemeConfigurationControl
import java.util.concurrent.atomic.AtomicBoolean

class PETActivity : AppCompatActivity(),
    AppUpdateManagerService, FirebaseAnalyticsService,
    ConsentManagementService {

    private val globalPreferencesViewModel: GlobalPreferencesViewModel
        by viewModels { GlobalPreferencesViewModel.Factory }

    /* Firebase Analytics */
    private lateinit var auth: FirebaseAuth
    override var firebaseAnalytics: FirebaseAnalytics? = null

    /* Consent */
    override var consentInformation: ConsentInformation? = null
    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    override val isMobileAdsInitializeCalled = AtomicBoolean(false)

    /* Update */
    override var appUpdateManager: AppUpdateManager? = null
    override var updateType: Int = IMMEDIATE
    override var activityUpdateResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartIntentSenderForResult()) {
                result: ActivityResult ->
            // handle callback
            if (result.resultCode != RESULT_OK) {
                print("Update flow failed! Result code: " + result.resultCode);
            }
        }

    /* Navigation Components */
    /*private var drawerLayout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var navigationBarView: NavigationBarView? = null*/

    override fun onCreate(savedInstanceState: Bundle?) {

        /*window.decorView*/
        enableEdgeToEdge()

        auth = Firebase.auth

        //signOut(activity = this@PETActivity)
        /*signIn(
            activity = this,
            option = SignInCredentialManager.SignInOptions.SILENT,
            onSuccess = {
                Log.d("Firebase",
                    "Signed in as: ${Firebase.auth.currentUser?.displayName}")
            },
            onFailure = {
                Log.d("Firebase", "Failed to sign in.")
            }
        )*/

        super.onCreate(savedInstanceState)

        setContent {

            ThemeConfigurationControl(
                globalPreferencesViewModel = globalPreferencesViewModel
            ) {

                Scaffold {

                    Box(
                        modifier = Modifier
                            .background(LocalPalette.current.surface.color)
                            .padding(it)
                    ) {
                        RootNavigation()
                    }

                }

            }

        }

        initializeMobileAdsSdk(this)
        createConsentInformation(this)
        initFirebaseAnalytics(this)

        checkForAppUpdate(this@PETActivity)

    }

    @Deprecated("Deprecated with Jetpack Compose")
    //TODO "Replace with Jetpack Compose components"
    fun initNavigationComponents() {
        /*val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment?

        Log.d("NavHostFragment", if (navHostFragment == null) "null" else "not null")

        navHostFragment?.navController?.let { navController ->
            val navBarView = findViewById<NavigationBarView>(R.id.item_navigation_bar)
            Log.d("Bar", if (navBarView == null) "null" else "not null")
            setNavigationBarBehavior(navBarView, navController)

            val navDrawerView = findViewById<NavigationView>(R.id.layout_navigation_drawer_view)
            Log.d("Drawer", if (navDrawerView == null) "null" else "not null")
            buildNavigationDrawer(navDrawerView, navController)
        }*/

    }

    @Deprecated("Deprecated with Jetpack Compose")
    //TODO "Replace with Jetpack Compose components"
    private fun buildNavigationDrawer(navView: NavigationView?, navController: NavController) {
        /*drawerLayout = findViewById(R.id.layout_navigation_drawer)

        drawerLayout?.let { drawerLayout ->
            actionBarDrawerToggle = ActionBarDrawerToggle(
                this, drawerLayout,
                R.string.navigation_open_state,
                R.string.navigation_closed_state
            )

            // pass the Open and Close toggle for the drawer layout listener
            // to toggle the button
            actionBarDrawerToggle?.let { actionBarDrawerToggle ->
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                drawerLayout.addDrawerListener(actionBarDrawerToggle)
                actionBarDrawerToggle.syncState()
            }

            // to make the Navigation drawer icon always appear on the action bar
            //getActivity().getSupportFragmentManager().setDisplayHomeAsUpEnabled(true);
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        setNavigationDrawerBehavior(navView, navController)*/
    }

    @Deprecated("Deprecated with Jetpack Compose")
    //TODO "Replace with Jetpack Compose components"
    private fun setNavigationDrawerBehavior(
        navView: NavigationView?, navController: NavController
    ) {
        /*navView?.let {
            setupWithNavController(navView, navController)
            navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item: MenuItem ->
                drawerLayout?.let { drawerLayout ->
                    if (onNavDestinationSelected(item, navController)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                        return@OnNavigationItemSelectedListener true
                    }
                    if (item.itemId == (R.id.action_home)) {
                        finish()
                    }
                }
                true
            })
        }*/
    }

    @Deprecated("Deprecated with Jetpack Compose")
    //TODO "Replace with Jetpack Compose components"
    private fun setNavigationBarBehavior(navView: NavigationBarView?, navController: NavController) {
        /*navView?.let {
            this.navigationBarView = navView

            setupWithNavController(navView, navController)

            navView.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener { item: MenuItem ->
                if (onNavDestinationSelected(item, navController)) {
                    if (drawerLayout != null) {
                        drawerLayout!!.closeDrawer(GravityCompat.START)
                    }

                    return@OnItemSelectedListener true
                }
                if (item.itemId == (R.id.open_menu)) {
                    drawerLayout?.let { drawerLayout ->
                        when (drawerLayout.isOpen) {
                            true -> drawerLayout.closeDrawer(GravityCompat.START)
                            false -> drawerLayout.openDrawer(GravityCompat.START)
                        }
                    }
                }
                true
            })

            navView.itemIconTintList = null
        }*/
    }

    @Deprecated("Deprecated with Jetpack Compose")
    //TODO "Replace with Jetpack Compose components"
    fun closeNavigationDrawer(): Boolean {
        /*drawerLayout?.let { drawerLayout ->
            if (drawerLayout.isOpen) {
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
        }
        return false*/
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        recreate()
    }

    override fun onResume() {
        super.onResume()

        completePendingAppUpdate()
    }

}
