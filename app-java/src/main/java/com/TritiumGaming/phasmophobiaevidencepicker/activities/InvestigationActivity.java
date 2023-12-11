package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationActivity extends PETActivity {

    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    protected EvidenceViewModel evidenceViewModel;
    protected ObjectivesViewModel objectivesViewModel;
    protected MapMenuViewModel mapMenuViewModel;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_investigation_solo);

    }

    public void initComponents() {

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        Log.d("NavHostFragment", navHostFragment == null ? "null" : "not null");
        if(navHostFragment == null) { return; }

        NavController navController = navHostFragment.getNavController();

        NavigationBarView navBarView = findViewById(R.id.item_navigation_bar);
        Log.d("Bar", navBarView == null ? "null" : "not null");
        setNavigationBarBehavior(navBarView, navController);

        NavigationView navDrawerView = findViewById(R.id.layout_navigation_drawer_view);
        Log.d("Drawer", navDrawerView == null ? "null" : "not null");
        setNavigationDrawer(navDrawerView, navController);

    }

    protected ViewModelProvider.AndroidViewModelFactory initViewModels() {

        ViewModelProvider.AndroidViewModelFactory factory = super.initViewModels();

        evidenceViewModel = factory.create(
                EvidenceViewModel.class);

        objectivesViewModel = factory.create(
                ObjectivesViewModel.class);

        mapMenuViewModel = factory.create(
                MapMenuViewModel.class);

        return factory;
    }

    protected void initPrefs() {
        super.initPrefs();

        setLanguage(getAppLanguage());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        recreate();
    }

    public void clearViewModels() {
        if(objectivesViewModel != null) {
            objectivesViewModel.reset();
        }
        if(evidenceViewModel != null) {
            evidenceViewModel.reset();
        }
    }


    private void setNavigationDrawer(NavigationView navView, NavController navController) {
        drawerLayout = findViewById(R.id.layout_navigation_drawer);

        if(drawerLayout == null) { return; }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_open_state, R.string.navigation_closed_state);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        //getActivity().getSupportFragmentManager().setDisplayHomeAsUpEnabled(true);
        drawerLayout.closeDrawer(GravityCompat.START);

        setNavigationDrawerBehavior(navView, navController);
    }

    private void setNavigationDrawerBehavior(NavigationView navView, NavController navController) {

        if(drawerLayout != null) {

            if(navView != null) {
                NavigationUI.setupWithNavController(navView, navController);

                navView.setNavigationItemSelectedListener(item -> {

                    boolean success = NavigationUI.onNavDestinationSelected(item, navController);
                    if(success) {
                        drawerLayout.closeDrawer(GravityCompat.START);

                        return true;
                    }

                    if(drawerLayout != null) {
                        if (item.getItemId() == (R.id.action_home)) {
                            finish();
                        }
                    }

                    return true;
                });
            }
        }
    }

    public void setNavigationBarBehavior(NavigationBarView navView, NavController navController) {

        if(navView != null) {
            NavigationUI.setupWithNavController(navView, navController);
            navView.setOnItemSelectedListener(item -> {

                Log.d("Bar", "item: " + item.getTitle());

                if(NavigationUI.onNavDestinationSelected(item, navController)) {
                    if(drawerLayout != null) {

                        drawerLayout.closeDrawer(GravityCompat.START);
                    }

                    return true;
                }

                if (item.getItemId() == (R.id.open_menu)) {
                    if(drawerLayout != null) {
                        if (drawerLayout.isOpen()) {
                            drawerLayout.closeDrawer(GravityCompat.START);
                        } else {
                            drawerLayout.openDrawer(GravityCompat.START);
                        }
                    }
                }
                return true;

            });
        }
    }


    public boolean closeDrawer() {
        if(drawerLayout != null) {
            if (drawerLayout.isOpen()) {
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    /**
     * Resets ObjectiveViewModel and EvidenceViewModel data upon Activity exit
     */
    @Override
    public void onBackPressed() {

        clearViewModels();

        super.onBackPressed();
    }

    @Override
    public void finish() {

        clearViewModels();

        super.finish();
    }

}
