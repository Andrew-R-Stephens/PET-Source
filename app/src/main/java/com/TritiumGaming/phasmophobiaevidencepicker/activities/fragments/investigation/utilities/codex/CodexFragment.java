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
import androidx.appcompat.widget.AppCompatTextView;
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

        AppCompatImageView prev = view.findViewById(R.id.button_prev);
        AppCompatTextView title = view.findViewById(R.id.textView_fragtitle);


        AppCompatTextView lstnr_cursedPosessions = view.findViewById(R.id.item1);
        AppCompatTextView lstnr_evidence = view.findViewById(R.id.item2);
        AppCompatTextView lstnr_ghosts = view.findViewById(R.id.item3);
        AppCompatTextView lstnr_toolsPrimary = view.findViewById(R.id.item4);
        AppCompatTextView lstnr_toolsAlternatives = view.findViewById(R.id.item5);
        AppCompatTextView lstnr_mapinfo = view.findViewById(R.id.item6);
        AppCompatTextView lstnr_sanityInfo = view.findViewById(R.id.item7);
        AppCompatTextView lstnr_ghostevents = view.findViewById(R.id.item8);
        AppCompatTextView lstnr_difficulties = view.findViewById(R.id.item9);


        title.setText("Codex");


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


        prev.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack());


        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

    }

}
