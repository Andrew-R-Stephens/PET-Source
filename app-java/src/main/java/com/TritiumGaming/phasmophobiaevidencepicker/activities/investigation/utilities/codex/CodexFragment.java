package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.utilities.codex.views.CodexGridCard;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;

public class CodexFragment extends InvestigationFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities_codex, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        GridLayout gridView = view.findViewById(R.id.grid_codex);

        CodexGridCard gotoCodex = gridView.findViewById(R.id.grid_codexmenu_option1);
        gotoCodex.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_equipmentStoreFragment));

        CodexGridCard gotoCursedPossessions = gridView.findViewById(R.id.grid_codexmenu_option2);
        gotoCursedPossessions.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_cursedPossessionsFragment));

        stylizeLogo(view.findViewById(R.id.label_codex_ghostos));

    }

    public void stylizeLogo(@NonNull AppCompatTextView label_ghostOS) {
        int color1 = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex3_gh0stTextNormal);
        int color2 = ColorUtils.getColorFromAttribute(requireContext(), R.attr.codex4_gh0stTextAlt);
        String color1Hex = String.format("#%06X", (0xFFFFFF & color1));
        String color2Hex = String.format("#%06X", (0xFFFFFF & color2));

        label_ghostOS.setText(Html.fromHtml(getString(R.string.codex_label_gh_ost)
                .replaceAll("#99AEB3", color1Hex)
                .replaceAll("#FFB43D", color2Hex)));
    }

    @Override
    public void softReset() {

    }

    @Override
    protected void saveStates() {

    }

}
