package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation.utilities;

import androidx.fragment.app.Fragment;

/**
 * EvidenceFragment class
 *
 * TODO
 *
 * @author TritiumGamingStudios
 */
public class UtilitiesFragment extends Fragment {
/*

    protected EvidenceViewModel evidenceViewModel;

    protected Typeface font_normal;
    protected int[] fontSize;

    */

    /**
     * EvidenceFragment parameterized constructor
     * @param layout
     */

    /*

    public UtilitiesFragment(int layout){
        super(layout);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_evidence_solo, container, false);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        if(evidenceViewModel == null)
            evidenceViewModel = new ViewModelProvider(requireActivity()).get(EvidenceViewModel.class);

        if(!evidenceViewModel.hasInvestigationData() && getContext() != null)
            evidenceViewModel.setInvestigationData(new InvestigationData(getContext()));

        if(getActivity() != null) {
            evidenceViewModel.setHuntWarningAudioAllowed(((InvestigationActivity) getActivity()).getHuntWarningAllowed());
            evidenceViewModel.setHuntWarningFlashTimeout(((InvestigationActivity) getActivity()).getHuntWarningFlashTimeout());
        }

        // FONT FAMILY
        font_normal = Typeface.MONOSPACE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            font_normal = getResources().getFont(R.font.norse_regular);
        else
        if(getContext() != null)
            font_normal = ResourcesCompat.getFont(getContext(), R.font.norse_regular);

        // NAVIGATION VIEWS
        AppCompatTextView label_goto_left = view.findViewById(R.id.label_goto_left);
        AppCompatTextView label_goto_right = view.findViewById(R.id.label_goto_right);
        View listener_goto_left = view.findViewById(R.id.listener_goto_left);
        View listener_goto_right = view.findViewById(R.id.listener_goto_right);
        // RESET VIEWS
        AppCompatTextView label_resetAll = view.findViewById(R.id.label_resetAll);
        //View navigation_fraglistener_spiritbox = view.findViewById(R.id.goto_spiritboxtool);

        // TEXT SIZES
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            label_resetAll.setAutoSizeTextTypeUniformWithConfiguration(5, 25, 1, TypedValue.COMPLEX_UNIT_SP);
            label_goto_left.setAutoSizeTextTypeUniformWithConfiguration(10, 50, 1, TypedValue.COMPLEX_UNIT_SP);
            label_goto_right.setAutoSizeTextTypeUniformWithConfiguration(10, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        }

        // LISTENERS
        listener_goto_left.setOnClickListener(v -> {
                    if (evidenceViewModel != null && evidenceViewModel.hasSanityData())
                        evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                    Navigation.findNavController(v).navigate(R.id.action_evidence_to_objectives);
                }
        );
        listener_goto_right.setOnClickListener(v -> {
                    if(evidenceViewModel != null && evidenceViewModel.hasSanityData())
                        evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                    Navigation.findNavController(v).navigate(R.id.action_evidence_to_mapmenu);
                }
        );
        */
/*
        navigation_fraglistener_spiritbox.setOnClickListener(v -> {
                    if(evidenceViewModel != null && evidenceViewModel.hasSanityData())
                        evidenceViewModel.getSanityData().setFlashTimeoutStart(-1);
                    Navigation.findNavController(v).navigate(R.id.action_evidenceFragment_to_toolSpiritBoxFragment);
                }
        );
        *//*


        // FONT SIZES
        fontSize = new int[]{12,40}; //min - max

        // COLORS
        @ColorInt int color_strikethrough = Color.WHITE, color_circle = Color.WHITE;
        TypedValue typedValue = new TypedValue();
        if(getContext() != null && getContext().getTheme() != null) {
            Resources.Theme theme = getContext().getTheme();
            theme.resolveAttribute(R.attr.strikethroughColor, typedValue, true);
            color_strikethrough = typedValue.data;
            theme.resolveAttribute(R.attr.circleColor, typedValue, true);
            color_circle = typedValue.data;
            theme.resolveAttribute(R.attr.negativeSelColor, typedValue, true);
        }
    }

    */
/**
     * onPause method
     *//*

    @Override
    public void onPause() {
        super.onPause();
    }
*/

}
