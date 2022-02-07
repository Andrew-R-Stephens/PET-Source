package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities;

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

/**
 * EvidenceFragment class
 * <p>
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class UtilitiesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // NAVIGATION VIEWS
        AppCompatTextView title = view.findViewById(R.id.textView_fragtitle);
        AppCompatImageView prev = view.findViewById(R.id.button_prev);
        AppCompatTextView goto_spiritBox = view.findViewById(R.id.ghostbox);
        AppCompatTextView goto_codex = view.findViewById(R.id.codex);
        AppCompatTextView goto_petHelp = view.findViewById(R.id.pethelp);
        AppCompatTextView goto_highscores = view.findViewById(R.id.highscores);

        title.setText("Utilities");

        prev.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack());

        goto_spiritBox.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_utilitiesFragment_to_ghostBoxFragment));
        goto_codex.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_utilitiesFragment_to_codexFragment));
        goto_petHelp.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_utilitiesFragment_to_petHelpFragment));
        goto_highscores.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_utilitiesFragment_to_highscoresFragment));

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
