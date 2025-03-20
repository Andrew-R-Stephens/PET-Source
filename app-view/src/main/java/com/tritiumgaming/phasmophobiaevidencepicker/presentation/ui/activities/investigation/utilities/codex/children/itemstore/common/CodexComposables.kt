package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.common.EquipmentTierItemStyle.Companion.Bordered
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.common.EquipmentTierItemStyle.Companion.Plain
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.common.TierLevel.Companion.One
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.common.TierLevel.Companion.Three
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.investigation.utilities.codex.children.itemstore.common.TierLevel.Companion.Two
import com.tritiumgaming.phasmophobiaevidencepicker.util.ColorUtils.getColorFromAttribute

@Composable
fun EquipmentSimple(
    image: Int = R.drawable.icon_shop_crucifix_2,
    tierLevel: TierLevel = One
) {
    EquipmentTierItem(
        image = image,
        style = Plain,
        tierLevel = tierLevel
    )
}

@Composable
@Preview
fun EquipmentButton(
    image: Int = R.drawable.icon_shop_crucifix_3,
    tierLevel: TierLevel = One,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    EquipmentTierItem(
        image = image,
        style = Bordered,
        tierLevel = tierLevel,
        selected = selected,
        onClick = {
            onClick()
        }
    )

}

@Composable
fun PossessionSimple(
    image: Int = R.drawable.icon_cursedpossessions_mirror
) {
    PossessionItem(
        image = image
    )
}

@Composable
@Preview
fun PossessionButton(
    image: Int = R.drawable.icon_cursedpossessions_mirror,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    PossessionItem(
        image = image,
        selected = selected,
        onClick = {
            onClick()
        }
    )
}

@Composable
@Preview
fun EquipmentTierItem(
    image: Int = R.drawable.icon_shop_crucifix_1,
    style: EquipmentTierItemStyle = Bordered,
    tierLevel: TierLevel = One,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .then(
                when (style) {
                    Plain -> Modifier
                    Bordered -> Modifier
                        .then(
                            when (selected) {
                                true -> Modifier
                                    .border(
                                        2.dp,
                                        Color(getColorFromAttribute(context, R.attr.codex4_border)))
                                false -> Modifier
                                    .border(
                                        2.dp,
                                        Color(getColorFromAttribute(context, R.attr.codex3_itemBorder)))
                            }
                        )

                    else -> { Modifier }
                }
            )
            .size(200.dp)
            .clickable { onClick() }
    ) {
        GridPattern()

        Box(
            modifier = Modifier
                .padding(2.dp)
        ) {
            EquipmentImage(
                image = image
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize(.25f)
                .align(Alignment.TopStart)
        ) {

            when(style) {
                Plain -> TierEmblemPlain(
                    tierLevel = tierLevel
                )
                Bordered -> TierEmblemBordered(
                    tierLevel = tierLevel,
                    selected = selected
                )
            }

        }

    }
}

@Composable
fun PossessionItem(
    image: Int = R.drawable.icon_cursedpossessions_mirror,
    selected: Boolean = true,
    onClick: () -> Unit = {}
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .then(
                when (selected) {
                    true -> Modifier
                        .border(
                            2.dp,
                            Color(getColorFromAttribute(context, R.attr.codex4_border)))
                    false -> Modifier
                        .border(
                            2.dp,
                            Color(getColorFromAttribute(context, R.attr.codex3_itemBorder)))
                }
            )
            .size(200.dp)
            .clickable { onClick() }
    ) {
        GridPattern()

        Box(
            modifier = Modifier
                .padding(4.dp)
                .aspectRatio(1f)
        ) {
            PossessionImage(
                image = image
            )
        }

    }
}

@Composable
fun GridPattern() {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
    ) {
        Image(
            painter = painterResource(id = R.drawable.itemstore_grid),
            contentDescription = "Tier Image",
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun EquipmentImage(
    image: Int = R.drawable.icon_shop_crucifix_1
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "Equipment Image",
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun PossessionImage(
    image: Int = R.drawable.icon_cursedpossessions_mirror
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "Possession Image",
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun TierEmblemPlain(
    tierLevel: TierLevel = Two
) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(8.dp)
    ) {
        TierEmblem(
            tierLevel = tierLevel,
            tintColor = Color(getColorFromAttribute(LocalContext.current, R.attr.codex4_tierAlt))
        )
    }
}

@Composable
fun TierEmblemBordered(
    tierLevel: TierLevel = One,
    selected: Boolean = false
) {

    Box(
        modifier = Modifier
            .then(
                Modifier
                    .background(
                        if (selected) {
                            Color(getColorFromAttribute(LocalContext.current, R.attr.codex4_background))
                        } else {
                            Color(getColorFromAttribute(LocalContext.current, R.attr.codex3_tierBackground))
                        }
                    )
                    .padding(8.dp)
            )
    ) {
        val tintColor =
            Color(getColorFromAttribute(LocalContext.current, R.attr.codex2_tierNormal))

        TierEmblem(
            tierLevel = tierLevel,
            tintColor = tintColor
        )
    }
}

@Composable
fun TierEmblem(
    tierLevel: TierLevel = One,
    tintColor: Color = Color.White,
    tintMode: BlendMode = BlendMode.SrcIn
) {
    val image = when(tierLevel) {
        One -> R.drawable.ic_tier_1
        Two -> R.drawable.ic_tier_2
        Three -> R.drawable.ic_tier_3
        else -> { 0 }
    }

    Image(
        painter = painterResource(id = image),
        contentDescription = "Tier Image",
        colorFilter = ColorFilter.tint(tintColor, tintMode)
    )
}

class EquipmentTierItemStyle {

    companion object {
        val Plain = EquipmentTierItemStyle()
        val Bordered = EquipmentTierItemStyle()

        fun values(): List<EquipmentTierItemStyle> = listOf(Plain, Bordered)
    }
}

@JvmInline
value class TierLevel(val value: Int) {

    companion object {
        val One = TierLevel(1)
        val Two = TierLevel(2)
        val Three = TierLevel(3)

        fun values(): List<TierLevel> = listOf(One, Two, Three)
    }
}
