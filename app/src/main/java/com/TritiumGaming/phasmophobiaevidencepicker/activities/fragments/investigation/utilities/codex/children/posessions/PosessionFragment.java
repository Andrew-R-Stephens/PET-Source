package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities.codex.children.posessions;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;

public class PosessionFragment extends Fragment {

    protected String title;

    public PosessionFragment(@LayoutRes int layout) {
        super(layout);
    }

    public String getTitle() {
        return title;
    }

}
