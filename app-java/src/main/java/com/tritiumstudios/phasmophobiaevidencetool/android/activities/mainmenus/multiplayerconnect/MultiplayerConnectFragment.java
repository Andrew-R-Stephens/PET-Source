package com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.multiplayerconnect;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.tritiumstudios.phasmophobiaevidencetool.R;

/**
 * LobbyConnectFragment class
 *
 * @author TritiumGamingStudios
 */
public class MultiplayerConnectFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lobbyconnect, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(
            @NonNull View view,
            @Nullable Bundle savedInstanceState) {

        AppCompatTextView createLobby = view.findViewById(R.id.lobbyconnect_createlobby);
        AppCompatTextView joinLobby = view.findViewById(R.id.lobbyconnect_joinlobby);

        createLobby.setOnClickListener(v -> Navigation.findNavController(v).
                navigate(R.id.action_lobbyConnectFragment_to_evidenceFragment_multHost));

        joinLobby.setOnClickListener(v -> Navigation.findNavController(v).
                navigate(R.id.action_lobbyConnectFragment_to_evidenceFragment_multClient));

    }


}
