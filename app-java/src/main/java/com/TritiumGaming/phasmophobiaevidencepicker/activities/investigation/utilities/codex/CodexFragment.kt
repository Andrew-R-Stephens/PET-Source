package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex

import android.text.Html
import androidx.appcompat.widget.AppCompatTextView
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ColorUtils.getColorFromAttribute

abstract class CodexFragment : InvestigationFragment() {
    fun stylizeLogo(ghostOSLabel: AppCompatTextView?) {

        val color1 = getColorFromAttribute(requireContext(), R.attr.codex3_gh0stTextNormal)
        val color2 = getColorFromAttribute(requireContext(), R.attr.codex4_gh0stTextAlt)
        val color1Hex = String.format("#%06X", (0xFFFFFF and color1))
        val color2Hex = String.format("#%06X", (0xFFFFFF and color2))

        ghostOSLabel?.text = Html.fromHtml(
            getString(R.string.codex_label_gh_ost)
                .replace("#99AEB3".toRegex(), color1Hex)
                .replace("#FFB43D".toRegex(), color2Hex)
        )
    }
}
