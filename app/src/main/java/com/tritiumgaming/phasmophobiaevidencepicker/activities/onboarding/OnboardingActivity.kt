package com.tritiumgaming.phasmophobiaevidencepicker.ui.onboarding

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.tritiumgaming.phasmophobiaevidencepicker.R

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Onboarding", "Creating Activity content view")
        setContentView(R.layout.activity_onboarding)
    }

    override fun finish() {
        Log.d("Onboarding", "Finishing Activity!")
        super.finish()
    }
}
