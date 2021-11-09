package com.TritiumGaming.phasmophobiaevidencepicker.fragments.investigation.evidence.children.mp;

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
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MPHostViewModel;

/**
 * EvidenceClientFragment class
 *
 * @author TritiumGamingStudios
 */
public class EvidenceMPHostFragment extends Fragment {

    private MPHostViewModel MPHostViewModel;

    public EvidenceMPHostFragment(){
        super(R.layout.fragment_evidence_mult_host);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(MPHostViewModel == null)
            MPHostViewModel = new ViewModelProvider(requireActivity()).get(MPHostViewModel.class);

        return inflater.inflate(R.layout.fragment_evidence_mult_host, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
