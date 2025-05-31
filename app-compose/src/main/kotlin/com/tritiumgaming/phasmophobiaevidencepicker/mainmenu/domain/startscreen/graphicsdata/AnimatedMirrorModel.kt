package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.startscreen.graphicsdata

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.startscreen.AAnimatedModel

/**
 * FrostscreenData class
 *
 * @author TritiumGamingStudios
 */
class AnimatedMirrorModel(
    screenWidth: Int, screenHeight: Int
) : AAnimatedModel(
    screenWidth, screenHeight
) {

    override val filter: PorterDuffColorFilter
        get() = PorterDuffColorFilter(
            Color.argb(currentAlpha, 255, 255, 255),
            PorterDuff.Mode.MULTIPLY
        )

    val scaledWidth: Double
        get() = drawScale * drawWidth

    val scaledHeight: Double
        get() = drawScale * drawHeight

    init {
        tickMax = 500
        fadeTick = .7

        drawScale = 1.0

        setWidth()
        setHeight()

        setX()
        setY()

        tickMax = ((Math.random() * (tickMax - (tickMax * .5))) + (tickMax * .5)).toInt()
    }

    override fun setWidth() {
        drawWidth = screenWidth.toDouble()
    }

    override fun setHeight() {
        drawHeight = screenHeight.toDouble()
    }

    fun setX() {
        this.drawX = 0.0
        if (scaledWidth > screenWidth) {
            this.drawX -= (scaledHeight / 2.0)
        }
    }

    fun setY() {
        this.drawY = 0.0
        if (scaledHeight > screenHeight) {
            this.drawY -= (scaledHeight / 2.0)
        }
    }

    override fun setRect() {
        rect[drawX.toInt(), drawY.toInt(), (drawX + scaledWidth).toInt()] = (drawY + scaledHeight).toInt()
    }

    override fun doTick() {
        setRect()
        if (currentTick >= 0) {
            currentTick += tickIncrementDirection
        } else {
            isAlive = false
        }
        if (currentTick >= tickMax) {
            tickIncrementDirection *= -1
        }
        setAlpha()
    }

}
