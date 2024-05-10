package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data

import android.content.res.Resources
import android.content.res.TypedArray
import androidx.annotation.StringRes

open class InvestigationPopupData {

    protected fun intArrayFromTypedArray(
        resources: Resources,
        typedArray: TypedArray,
        key: Int = -1,
        defaultValue: Int = 0
    ): IntArray {

        val childArray =
            if(key == -1) typedArray
            else resources.obtainTypedArray(typedArray.getResourceId(key, defaultValue))

        @StringRes val outArray = IntArray(childArray.length())
        for (j in 0 until childArray.length()) {
            outArray[j] = childArray.getResourceId(j, defaultValue)
        }
        childArray.recycle() //cleanup

        return outArray
    }

}
