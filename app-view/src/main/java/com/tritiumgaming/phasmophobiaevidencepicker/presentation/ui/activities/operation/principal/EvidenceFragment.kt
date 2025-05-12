package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.compose.ui.platform.ComposeView
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.sanity.sanity.SanityRunnable
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.InvestigationFragment
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.operation.principal.journal.InvestigationContent
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.SelectiveTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

open class EvidenceFragment : InvestigationFragment(R.layout.fragment_evidence_v2) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val journalComposable = view.findViewById<ComposeView>(R.id.layout_composable)
        journalComposable?.setContent {
            SelectiveTheme(
                palette = globalPreferencesViewModel.colorTheme.style,
                typography = globalPreferencesViewModel.fontTheme.style
            ) {
                InvestigationContent()
            }
        }

        //journalComposable.invalidate()

    }


    private var sanityScope: CoroutineScope? = CoroutineScope(Dispatchers.Default)
    private var sanityJob: Job? = null

    private fun createSanityRunnable() {
        investigationViewModel.sanityRunnable =
            object : SanityRunnable(requireContext(), 10f, 10f) {

                override fun runCondition(): Boolean {
                    return sanityJob?.isActive == true
                }

                override fun onTick() {

                    /** TODO : recreate the following action flow

                    if (huntAudio is both allowed) AND (huntAudio is not "triggered") AND (the phase is "action") {
                    play the huntWarning audio
                    set huntAudio as "triggered"
                    }
                     */

                    investigationViewModel.apply {
                        if (!timerPaused.value) {

                            tickSanityHandler()

                            updatePhaseTimeElapsed()

                            if (globalPreferencesViewModel.huntWarningAudioPreference.value) {
                                huntWarningAudioListener?.play()
                            }
                        }
                    }
                }
            }

        val huntWarningListener: SanityRunnable.HuntWarningAudioListener? = null
        globalPreferencesViewModel.currentLanguageCode.value.let { language ->
            object : SanityRunnable.HuntWarningAudioListener() {
                private fun createMediaPlayer(language: String): MediaPlayer? {
                    var p: MediaPlayer? = null
                    try {
                        p = when (language) {
                            "es" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_es)
                            "fr" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_fr)
                            "de" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_de)
                            "en" -> MediaPlayer.create(requireContext(), R.raw.huntwarning_en)
                            else -> MediaPlayer.create(requireContext(), R.raw.huntwarning_en)
                        }
                    } catch (e: IllegalStateException) { e.printStackTrace() }
                    return p
                }

                override fun init() {
                    lang = language
                    mediaPlayer = createMediaPlayer(language)
                }

                override fun play() {
                    if (globalPreferencesViewModel.huntWarningAudioPreference.value) {
                        mediaPlayer?.start()
                        investigationViewModel.setAudioWarnTriggered(true)
                    }
                }

                override fun stop() { mediaPlayer?.release() }
            }
        }

        huntWarningListener?.let { listener ->
            investigationViewModel.sanityRunnable?.huntWarningAudioListener = listener }

    }

    private fun startSanityJob() {
        sanityJob = sanityScope?.launch {
            investigationViewModel.sanityRunnable ?: createSanityRunnable()
            investigationViewModel.sanityRunnable?.run()
        }
        sanityJob?.start()
    }

    private fun stopSanityJob() {
        sanityJob?.cancel("Cancelling Sanity Job")
        investigationViewModel.sanityRunnable?.huntWarningAudioListener?.stop()
    }

    override fun onPause() {
        stopSanityJob()
        super.onPause()
    }

    override fun onResume() {
        startSanityJob()
        super.onResume()
    }

    /*sanityToolsLayout = view.findViewById(R.id.layout_sanity_tool)
        investigationViewModel.let { investigationViewModel ->
            sanityToolsLayout?.init(investigationViewModel)
        }

        // GHOST / EVIDENCE CONTAINERS
        investigationSectionWrapper = view.findViewById(R.id.layout_evidence_tool_wrapper)
        val columnLeft = investigationSectionWrapper?.findViewById<FrameLayout>(R.id.column_left)
        val columnRight = investigationSectionWrapper?.findViewById<FrameLayout>(R.id.column_right)
        columnRight?.findViewById<View>(R.id.scrollview)?.verticalScrollbarPosition =
            View.SCROLLBAR_POSITION_RIGHT

        globalPreferencesViewModel.let { globalPreferencesViewModel ->
            if (!globalPreferencesViewModel.rTLPreference.value) {
                ghostSection = columnLeft?.getChildAt(0) as InvestigationSection
                evidenceSection = columnRight?.getChildAt(0) as InvestigationSection
            } else {
                evidenceSection = columnLeft?.getChildAt(0) as InvestigationSection
                ghostSection = columnRight?.getChildAt(0) as InvestigationSection
            }
        }

        ghostSection?.setLabel(getString(R.string.investigation_section_title_ghosts))
        evidenceSection?.setLabel(getString(R.string.investigation_section_title_evidence))

        ghostList = GhostListView(requireContext())
        evidenceList = EvidenceListView(requireContext())

        val setupLists = CoroutineScope(Dispatchers.Main).launch {
            globalPreferencesViewModel.let { globalPreferencesViewModel ->
                investigationViewModel.let { investigationViewModel ->
                    evidenceList?.init(
                        globalPreferencesViewModel,
                        investigationViewModel,
                        popupWindow,
                        evidenceSection?.findViewById(R.id.progressbar),
                        adRequest,
                        ghostList
                    )
                    ghostList?.init(
                        globalPreferencesViewModel,
                        investigationViewModel,
                        popupWindow,
                        ghostSection?.findViewById(R.id.progressbar),
                        adRequest
                    )
                }
            }
        }
        setupLists.start()

        val ghostScrollview = ghostSection?.findViewById<ScrollView>(R.id.scrollview)
        val evidenceScrollview = evidenceSection?.findViewById<ScrollView>(R.id.scrollview)

        ghostScrollview?.addView(ghostList)
        evidenceScrollview?.addView(evidenceList)

        sanityToolsLayout?.visibility =
            if(investigationViewModel.isInvestigationToolsDrawerCollapsed.value)
                GONE else VISIBLE

        val sanityToolbarComposable: ComposeView? = view.findViewById(R.id.sanityToolbarComposable)
        sanityToolbarComposable?.setContent {
            val collapseButton: @Composable () -> Unit = {
                CollapseButton {
                    investigationViewModel.isInvestigationToolsDrawerCollapsed.value.let {
                            when (it) {
                                true -> {
                                    sanityToolsLayout?.animate()
                                        ?.setListener(object : AnimatorListenerAdapter() {
                                            override fun onAnimationStart(animation: Animator) {
                                                super.onAnimationStart(animation)
                                                sanityToolsLayout?.visibility = GONE
                                            }

                                            override fun onAnimationEnd(animation: Animator) {
                                                super.onAnimationEnd(animation)
                                                sanityToolsLayout?.visibility = VISIBLE
                                                investigationViewModel.setInvestigationToolsDrawerState(false)
                                            }
                                        })?.start()
                                }
                                false -> {
                                    sanityToolsLayout?.animate()
                                        ?.setListener(object : AnimatorListenerAdapter() {
                                            override fun onAnimationStart(animation: Animator) {
                                                super.onAnimationStart(animation)
                                                sanityToolsLayout?.visibility = VISIBLE
                                            }

                                            override fun onAnimationEnd(animation: Animator) {
                                                super.onAnimationStart(animation)
                                                sanityToolsLayout?.visibility = GONE
                                                investigationViewModel.setInvestigationToolsDrawerState(true)
                                            }
                                        })?.start()
                                }
                            }

                        }
                }
            }

            val resetButton: @Composable () -> Unit = {
                ResetButton(
                    onClick = { reset() }
                )
            }

            val toolsList: Array<ToolBarItemPair> = arrayOf(
                ToolBarItemPair(collapseButton),
                ToolBarItemPair(resetButton),
                ToolBarItemPair(View(LocalContext.current))
            )
            InvestigationToolbar(
                toolsList
            )
        }

        popupWindow = PopupWindow(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )

        ghostList?.createPopupWindow(popupWindow)
        evidenceList?.createPopupWindow(popupWindow)

        Thread { ghostList?.createViews() }.start()
        Thread { evidenceList?.createViews() }.start()
        
        lifecycleScope.launch {
            investigationViewModel.ghostOrder.collect {
                Log.d("GhostListView", "Order Updated")
                ghostList?.attemptInvalidate()
            }
        }

    }

    private fun initResetButton(buttonReset: ComposeView?) {
        buttonReset?.setContent {
            ResetButton(
                onClick = { reset() }
            )
        }
    }

    private fun initCollapsible(toggleCollapseButton: ComposeView?) {
        investigationViewModel.investigationModel.let { investigationModel ->
            toggleCollapseButton?.setContent {
                CollapseButton(
                    onClick = { investigationModel.toggleInvestigationToolsDrawerState() }
                )
            }
        }

        lifecycleScope.launch {
            investigationViewModel.investigationModel.isInvestigationToolsDrawerCollapsed
                .collectLatest { state ->
                sanityToolsLayout?.visibility = when (state) {
                    true -> View.GONE
                    false -> View.VISIBLE
                }
            }
        }

        // SANITY COLLAPSIBLE
        toggleCollapseButton?.setOnClickListener {
            when (investigationViewModel.investigationModel.isInvestigationToolsDrawerCollapsed.value == true) {
                true ->
                    sanityToolsLayout?.animate()
                        ?.setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator) {
                                super.onAnimationStart(animation)
                                sanityToolsLayout?.visibility = View.GONE
                            }
                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationEnd(animation)
                                sanityToolsLayout?.visibility = View.VISIBLE
                                investigationViewModel.investigationModel.setInvestigationToolsDrawerState(false)
                            }
                        })?.start()
                false -> {
                    sanityToolsLayout?.animate()
                        ?.setListener(object : AnimatorListenerAdapter() {
                            override fun onAnimationStart(animation: Animator) {
                                super.onAnimationStart(animation)
                                sanityToolsLayout?.visibility = View.VISIBLE
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                super.onAnimationStart(animation)
                                sanityToolsLayout?.visibility = View.GONE
                                investigationViewModel.investigationModel.setInvestigationToolsDrawerState(true)
                            }
                        })?.start()
                }
            }
        }
    }*/

    override fun reset() {
        /*stopSanityJob()
        startSanityJob()
        investigationViewModel.reset()
        objectivesViewModel.reset()
        investigationViewModel.reorderGhostScoreModel()
        ghostList?.reset()*/
    }

    override fun onDestroyView() {
        popupWindow?.dismiss()
        popupWindow = null

        super.onDestroyView()
    }

}
