package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
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
        AppCompatImageView prev = view.findViewById(R.id.button_prev);
        View goto_spiritboxtool = view.findViewById(R.id.utility_spiritbox);

        prev.setOnClickListener(v ->
                Navigation.findNavController(v).popBackStack());
        goto_spiritboxtool.setOnClickListener(v ->
                Navigation.findNavController(v).
                        navigate(R.id.action_utilitiesFragment_to_toolSpiritBoxFragment));
    }
}
