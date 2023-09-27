package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.InvestigationFragment;


public class UtilitiesFragment extends InvestigationFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // NAVIGATION VIEWS
        AppCompatTextView title = view.findViewById(R.id.textView_fragtitle);
        AppCompatImageView prev = view.findViewById(R.id.button_prev);
        AppCompatTextView goto_codex = view.findViewById(R.id.codex);
        AppCompatTextView goto_petHelp = view.findViewById(R.id.pethelp);

        title.setText("Utilities");

        prev.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack());
/*

        goto_codex.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_utilitiesFragment_to_codexFragment));
*/

        /*
        goto_petHelp.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_utilitiesFragment_to_codexFragment));
        */

    }

    @Override
    public void softReset() {

    }
}
