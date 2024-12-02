package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.applanguages

import android.os.Bundle
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
            mainMenuViewModel.languageSelectedOriginal = -1
            try { v?.let { findNavController(v).popBackStack() } }
            catch (e: IllegalStateException) { e.printStackTrace() }
        }

        cancelButton.setOnClickListener { handleDiscardChanges() }

        // DATA
        val selected = globalPreferencesViewModel.getCurrentLanguageIndex()
        if (mainMenuViewModel.languageSelectedOriginal == -1) {
            mainMenuViewModel.languageSelectedOriginal = selected
        }
        globalPreferencesViewModel.languageList.let { languageList ->
            for (i in languageList.indices) {
                val adapter = LanguagesAdapterView(languageList, selected,
                    object: LanguagesAdapterView.OnLanguageListener {
                        override fun onNoteClick(position: Int) {
                            globalPreferencesViewModel.setCurrentLanguageCodeByIndex(position)
                            mainMenuViewModel.canRefreshFragment = true

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
        globalPreferencesViewModel.setCurrentLanguageCodeByIndex(
            mainMenuViewModel.languageSelectedOriginal)

        configureLanguage(
            globalPreferencesViewModel
                .languageList[mainMenuViewModel.languageSelectedOriginal].abbreviation
        )

        try { findNavController(requireView()).popBackStack() }
        catch (e: IllegalStateException) { e.printStackTrace() }

        val message = getString(R.string.toast_discardchanges)
        try { Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show() }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    private fun configureLanguage(
        langCode: String = globalPreferencesViewModel.currentLanguageCode.value
    ) {
        try { (requireActivity() as MainMenuActivity).configureLanguage(langCode) }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    override fun backPressedHandler() {
        handleDiscardChanges()
    }

    public override fun refreshFragment() {
        if (mainMenuViewModel.canRefreshFragment) {
            super.refreshFragment()
            mainMenuViewModel.canRefreshFragment = false
        }
    }

}
