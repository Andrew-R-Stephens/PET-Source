package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.applanguages

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.MainMenuFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.applanguages.views.LanguagesAdapterView
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.views.global.NavHeaderLayout

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
        val cancelButton = navHeaderLayout.findViewById<View>(R.id.button_left)
        val confirmButton = navHeaderLayout.findViewById<View>(R.id.button_right)

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
        globalPreferencesViewModel?.let { globalPreferencesViewModel ->
            selected = globalPreferencesViewModel.getCurrentLanguageIndex()
        }
        mainMenuViewModel?.let { mainMenuViewModel ->
            if (mainMenuViewModel.languageSelectedOriginal == -1) {
                mainMenuViewModel.languageSelectedOriginal = selected
            }
        }
        globalPreferencesViewModel?.languageList?.let { languageList ->
            for (i in languageList.indices) {
                val adapter = LanguagesAdapterView(languageList, selected,
                    object: LanguagesAdapterView.OnLanguageListener {
                        override fun onNoteClick(position: Int) {
                            globalPreferencesViewModel?.setCurrentLanguage(position)
                            mainMenuViewModel?.canRefreshFragment = true

                            this@AppLanguageFragment.configureLanguage()
                            this@AppLanguageFragment.refreshFragment()
                        }
                    })

                recyclerViewLanguages.adapter = adapter
                try { recyclerViewLanguages.layoutManager = LinearLayoutManager(requireContext()) }
                catch (e: IllegalStateException) { e.printStackTrace() }
            }
        }
    }

    private fun handleDiscardChanges() {
        mainMenuViewModel?.let { mainMenuViewModel ->
            globalPreferencesViewModel?.setCurrentLanguage(
                mainMenuViewModel.languageSelectedOriginal)
            Log.d("Languages",
                "Set language = ${mainMenuViewModel.languageSelectedOriginal}")
        }

        configureLanguage()

        try { findNavController(requireView()).popBackStack() }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun configureLanguage() {
        globalPreferencesViewModel?.currentLanguageAbbr?.let{ currLangAbbr ->
            try {
                (requireActivity() as MainMenuActivity).setLanguage(currLangAbbr)
            }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }
    }

    override fun initViewModels() {
        super.initViewModels()
        initMainMenuViewModel()
    }

    override fun backPressedHandler() {
        handleDiscardChanges()

        val message = getString(R.string.toast_discardchanges)
        try { Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show() }
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
