package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.applanguages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuActivity
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.applanguages.views.LanguagesAdapterView
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.NavHeaderLayout
import java.util.Arrays

class AppLanguageFragment : MainMenuFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.init()
        return inflater.inflate(R.layout.fragment_languages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // INITIALIZE VIEWS
        val navHeaderLayout = view.findViewById<NavHeaderLayout>(R.id.navHeaderLayout)
        val confirmButton = navHeaderLayout.findViewById<View>(R.id.button_left)
        val cancelButton = navHeaderLayout.findViewById<View>(R.id.button_right)

        val recyclerViewLanguages = view.findViewById<RecyclerView>(R.id.recyclerview_languageslist)

        // LISTENERS
        confirmButton.setOnClickListener { v: View? ->
            mainMenuViewModel?.languageSelectedOriginal = -1
            try { v?.let { findNavController(v).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        cancelButton.setOnClickListener { handleDiscardChanges() }

        // DATA
        var selected = 0
        var languageNames: ArrayList<String> = ArrayList()
        try {
            languageNames = ArrayList(
                listOf(*requireContext().resources.getStringArray(R.array.languages_name)))

            globalPreferencesViewModel?.let { globalPreferencesViewModel ->
                selected = globalPreferencesViewModel.getLanguageIndex(ArrayList(
                    listOf(*requireContext().resources.getStringArray(R.array.languages_abbreviation))
                ))
            }
            mainMenuViewModel?.let { mainMenuViewModel ->
                if (mainMenuViewModel.languageSelectedOriginal == -1) {
                    mainMenuViewModel.languageSelectedOriginal = selected
                }
            }
        } catch (e: IllegalStateException) { e.printStackTrace() }

        for (i in languageNames.indices) {
            val adapter = LanguagesAdapterView(languageNames, selected,
                object: LanguagesAdapterView.OnLanguageListener {
                    override fun onNoteClick(position: Int) {
                        globalPreferencesViewModel?.setLanguage(position,
                            this@AppLanguageFragment.resources
                                .getStringArray(R.array.languages_abbreviation)
                        )
                        mainMenuViewModel?.canRefreshFragment = true

                        this@AppLanguageFragment.configureLanguage()
                        this@AppLanguageFragment.refreshFragment()
                    }
            })

            recyclerViewLanguages.adapter = adapter
            try { recyclerViewLanguages.layoutManager = LinearLayoutManager(requireContext()) }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        /*
        for (i in languageNames.indices) {
            val adapter = LanguagesAdapterView(languageNames, selected) { position: Int ->
                globalPreferencesViewModel?.setLanguage(position,
                    this@AppLanguageFragment.resources.getStringArray(R.array.languages_abbreviation)
                )
                mainMenuViewModel?.canRefreshFragment = true

                this@AppLanguageFragment.configureLanguage()
                this@AppLanguageFragment.refreshFragment()
            }

            recyclerViewLanguages.adapter = adapter
            try { recyclerViewLanguages.layoutManager = LinearLayoutManager(requireContext()) }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }*/
    }

    private fun handleDiscardChanges() {
        mainMenuViewModel?.let { mainMenuViewModel ->
            globalPreferencesViewModel?.setLanguage(
                mainMenuViewModel.languageSelectedOriginal,
                resources.getStringArray(R.array.languages_abbreviation))
            Log.d("Languages",
                "Set language = ${mainMenuViewModel.languageSelectedOriginal}")
        }

        configureLanguage()

        try { findNavController(requireView()).popBackStack() }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun configureLanguage() {
        globalPreferencesViewModel?.let{ globalPreferencesViewModel ->
            try { (requireActivity() as MainMenuActivity).setLanguage(
                    globalPreferencesViewModel.languageName)
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun initViewModels() {
        super.initViewModels()
        initMainMenuViewModel()
    }

    override fun backPressedHandler() {
        handleDiscardChanges()

        val message = getString(R.string.toast_discardchanges)
        try { Toast.makeText(requireActivity(), message, com.google.android.material.R.integer.material_motion_duration_short_2).show() }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    public override fun refreshFragment() {
        mainMenuViewModel?.let { mainMenuViewModel ->
            if (mainMenuViewModel.canRefreshFragment) {
                super.refreshFragment()
                mainMenuViewModel.canRefreshFragment = false
            }
        }
    }

    public override fun saveStates() {
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            try { globalPreferencesViewModel.saveToFile(requireContext())
            } catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun onPause() {
        // SAVE PERSISTENT DATA
        saveStates()
        super.onPause()
    }
}
