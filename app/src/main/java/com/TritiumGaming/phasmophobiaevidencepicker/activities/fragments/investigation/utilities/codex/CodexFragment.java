package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;

public class CodexFragment  extends Fragment {

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

        ConstraintLayout lstnr_difficulties = view.findViewById(R.id.constraintLayout_aboutdifficulties);
        ConstraintLayout lstnr_evidence = view.findViewById(R.id.constraintLayout_evidence);
        ConstraintLayout lstnr_ghosts = view.findViewById(R.id.constraintLayout_ghosts);
        ConstraintLayout lstnr_ghostevents = view.findViewById(R.id.constraintLayout_ghostevents);
        ConstraintLayout lstnr_mapinfo = view.findViewById(R.id.constraintLayout_mapsinfo);
        ConstraintLayout lstnr_cursedPosessions = view.findViewById(R.id.constraintLayout_cursedPosessions);
        ConstraintLayout lstnr_sanityInfo = view.findViewById(R.id.constraintLayout_aboutsanity);
        ConstraintLayout lstnr_toolsPrimary = view.findViewById(R.id.constraintLayout_toolsprimary);
        ConstraintLayout lstnr_toolsAlternatives = view.findViewById(R.id.constraintLayout_toolsalternative);

        lstnr_difficulties.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexDifficultiesFragment));

        lstnr_evidence.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexEvidenceFragment));

        lstnr_ghosts.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexGhostsFragment));

        lstnr_ghostevents.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexGhostEventsFragment));

        lstnr_mapinfo.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexMapInfoFragment));

        lstnr_cursedPosessions.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexPosessionsFragment));

        lstnr_sanityInfo.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexSanityInfo));

        lstnr_toolsPrimary.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexToolsPrimary));

        lstnr_toolsAlternatives.setOnClickListener(v -> Navigation.findNavController(v)
                .navigate(R.id.action_codexFragment_to_codexToolsAlternatives));

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(this,
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

    }

}
