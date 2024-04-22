package com.tritiumstudios.phasmophobiaevidencetool.android.activities.onboarding;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tritiumstudios.phasmophobiaevidencetool.R;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("Onboarding", "Creating Activity content view");
        setContentView(R.layout.activity_onboarding);
    }

    @Override
    public void finish() {
        Log.d("Onboarding", "Finishing Activity!");
        super.finish();
    }
}
