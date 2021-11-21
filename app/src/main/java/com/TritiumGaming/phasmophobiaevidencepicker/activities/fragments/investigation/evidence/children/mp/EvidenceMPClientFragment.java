package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.evidence.children.mp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MPClientViewModel;

/**
 * EvidenceClientFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceMPClientFragment extends Fragment {

    private MPClientViewModel MPClientViewModel;

    public EvidenceMPClientFragment() {
        super(R.layout.fragment_evidence_mult_client);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        if (MPClientViewModel == null)
            MPClientViewModel =
                    new ViewModelProvider(requireActivity()).get(MPClientViewModel.class);

        return inflater.inflate(
                R.layout.fragment_evidence_mult_client, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
