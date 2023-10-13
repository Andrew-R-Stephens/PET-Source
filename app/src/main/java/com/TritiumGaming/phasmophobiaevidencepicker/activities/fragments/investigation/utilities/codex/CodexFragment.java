package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.InvestigationFragment;
import com.google.android.material.card.MaterialCardView;

public class CodexFragment extends InvestigationFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities_codex2, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        GridLayout gridView = view.findViewById(R.id.grid_codex);
        AppCompatImageView prev = view.findViewById(R.id.button_prev);
        AppCompatTextView title = view.findViewById(R.id.codex_title);


        MaterialCardView gotoCodex = gridView.findViewById(R.id.grid_codexmenu_option1);
        gotoCodex.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_itemStoreFragment));

    }

    @Override
    public void softReset() {

    }

}
