package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
open class InvestigationActivity : PETActivity() {
    protected var investigationViewModel: InvestigationViewModel? = null
    protected var objectivesViewModel: ObjectivesViewModel? = null
    protected var mapMenuViewModel: MapMenuViewModel? = null

    private var drawerLayout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    private var navigationBarView: NavigationBarView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_investigation)
    }

    override fun initViewModels(): AndroidViewModelFactory? {
        super.initViewModels()

        val factory: AndroidViewModelFactory? = super.initViewModels()
        factory?.let {
            initInvestigationViewModel(factory)
            initObjectivesViewModel(factory)
            initMapMenuViewModel(factory)
        }

        return factory
    }

    private fun initMapMenuViewModel(factory: AndroidViewModelFactory) {
        mapMenuViewModel = factory.create(MapMenuViewModel::class.java)
    }

    private fun initObjectivesViewModel(factory: AndroidViewModelFactory) {
        objectivesViewModel = factory.create(ObjectivesViewModel::class.java)
    }

    private fun initInvestigationViewModel(factory: AndroidViewModelFactory) {
        investigationViewModel = factory.create(InvestigationViewModel::class.java)
    }

    override fun loadPreferences() {
        super.loadPreferences()
        globalPreferencesViewModel?.currentLanguageAbbr?.let { globalPreferencesViewModel ->
            setLanguage(globalPreferencesViewModel) }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        recreate()
    }

    private fun resetViewModels() {
        objectivesViewModel?.reset()
        investigationViewModel?.reset()
    }

    fun initNavigationComponents() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment?

        Log.d("NavHostFragment", if (navHostFragment == null) "null" else "not null")

        navHostFragment?.navController?.let { navController ->
            val navBarView = findViewById<NavigationBarView>(R.id.item_navigation_bar)
            Log.d("Bar", if (navBarView == null) "null" else "not null")
            setNavigationBarBehavior(navBarView, navController)

            val navDrawerView = findViewById<NavigationView>(R.id.layout_navigation_drawer_view)
            Log.d("Drawer", if (navDrawerView == null) "null" else "not null")
            buildNavigationDrawer(navDrawerView, navController)
        }

    }

    private fun buildNavigationDrawer(navView: NavigationView?, navController: NavController) {
        drawerLayout = findViewById(R.id.layout_navigation_drawer)

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

        setNavigationDrawerBehavior(navView, navController)
    }

    private fun setNavigationDrawerBehavior(
        navView: NavigationView?, navController: NavController) {
        navView?.let {
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
        }
    }

    fun setNavigationBarBehavior(navView: NavigationBarView?, navController: NavController) {
        navView?.let {
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
        }
    }


    fun closeNavigationDrawer(): Boolean {
        drawerLayout?.let { drawerLayout ->
            if (drawerLayout.isOpen) {
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
        }
        return false
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onBackPressed() {
        resetViewModels()

        super.onBackPressed()
    }

    override fun finish() {
        resetViewModels()

        super.finish()
    }
}
