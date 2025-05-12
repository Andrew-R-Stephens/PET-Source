package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.mainmenus.appsettings

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.SwitchCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import com.tritiumgaming.phasmophobiaevidencepicker.R

class SettingsToggleItemView : ConstraintLayout {
    private var switchId = 0

    var isChecked: Boolean
        get() {
            val toggleSwitch = findViewById<SwitchCompat>(this.switchId)
            return toggleSwitch.isChecked
        }
        set(isChecked) {
            val toggleSwitch = findViewById<SwitchCompat>(this.switchId)
            toggleSwitch.isChecked = isChecked
        }

    constructor(context: Context) : super(context) {
        init(getContext(), null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(getContext(), attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
        init(getContext(), attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes) {
        init(getContext(), attrs)
    }

    fun init(context: Context, attrs: AttributeSet?) {
        inflate(context, R.layout.item_settings_toggle, this)

        if (attrs != null) {
            context.withStyledAttributes(attrs, R.styleable.SettingsToggleItemView) {

                val description =
                    getString(R.styleable.SettingsToggleItemView_settings_toggle_description)
                setDescription(description)

            }
        }

        initSwitchID(generateViewId())
    }

    private fun initSwitchID(id: Int) {
        this.switchId = id

        findViewById<View>(R.id.switch_toggle).id = switchId
    }

    private fun setDescription(text: String?) {
        val label = findViewById<AppCompatTextView>(R.id.switch_description)
        label?.text = text
    }

    fun setSwitchClickListener(listener: OnClickListener?) {
        val toggleSwitch = findViewById<SwitchCompat>(this.switchId)
        toggleSwitch?.setOnClickListener(listener)
    }

}
