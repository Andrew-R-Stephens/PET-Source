package com.tritiumstudios.phasmophobiaevidencetool.activities.mainmenus.appsettings;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.tritiumstudios.phasmophobiaevidencetool.R;

public class SettingsToggleItem extends ConstraintLayout {

    private int switchId;

    public SettingsToggleItem(@NonNull Context context) {
        super(context);

        init(getContext(), null);
    }

    public SettingsToggleItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);

        init(getContext(), attrs);
    }

    public SettingsToggleItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(getContext(), attrs);
    }

    public SettingsToggleItem(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(getContext(), attrs);
    }

    public void init(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        inflate(context, R.layout.item_settings_toggle, this);

        if (attrs != null) {
            TypedArray typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.SettingsToggleItem);

            String description =
                    typedArray.getString(R.styleable.SettingsToggleItem_settings_toggle_description);
            setDescription(description);

            typedArray.recycle();
        }

        initSwitchID(View.generateViewId());
    }

    private void initSwitchID(int id) {
        this.switchId = id;

        findViewById(R.id.switch_toggle).setId(this.switchId);
    }

    public void setDescription(String text) {
        AppCompatTextView label = findViewById(R.id.switch_description);
        if(label != null) {
            label.setText(text);
        }
    }

    public void setSwitchClickListener(OnClickListener listener) {
        SwitchCompat toggleSwitch = findViewById(this.switchId);

        toggleSwitch.setOnClickListener(listener);
    }

    public void setChecked(boolean isChecked) {
        SwitchCompat toggleSwitch = findViewById(this.switchId);
        Log.d("GPVM", "Changing state of: " + toggleSwitch.getId());

        toggleSwitch.setChecked(isChecked);
    }

    public boolean isChecked() {
        SwitchCompat toggleSwitch = findViewById(this.switchId);
        Log.d("GPVM", "Checking state of: " + toggleSwitch.getId());

        return toggleSwitch.isChecked();
    }

}
