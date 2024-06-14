package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex;

import android.text.Html;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.ColorUtils;

public abstract class CodexFragment extends InvestigationFragment {

    public void stylizeLogo(@NonNull AppCompatTextView label_ghostOS) {
        int color1 = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex3_gh0stTextNormal);
        int color2 = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex4_gh0stTextAlt);
        String color1Hex = String.format("#%06X", (0xFFFFFF & color1));
        String color2Hex = String.format("#%06X", (0xFFFFFF & color2));

        label_ghostOS.setText(Html.fromHtml(getString(R.string.codex_label_gh_ost)
                .replaceAll("#99AEB3", color1Hex)
                .replaceAll("#FFB43D", color2Hex)));
    }

}
