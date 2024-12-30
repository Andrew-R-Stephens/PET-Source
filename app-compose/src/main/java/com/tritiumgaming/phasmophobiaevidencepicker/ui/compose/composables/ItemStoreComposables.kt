package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.ItemStoreType.Companion.Equipment
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables.ItemStoreType.Companion.Possession

@Composable
@Preview
fun TestGroups() {
    Column() {
        TestEquipmentGroup()
        TestPossessionGroup()
    }
}

@Composable
fun TestEquipmentGroup() {
    EquipmentGroup()
}

@Composable
fun TestPossessionGroup() {
    PossessionGroup()
}

@Composable
fun EquipmentGroup(
    icons: ArrayList<Int> = arrayListOf(
        R.drawable.icon_shop_crucifix_1,
        R.drawable.icon_shop_crucifix_2,
        R.drawable.icon_shop_crucifix_3
    )
) {
    Row(
        horizontalArrangement =
            if(icons.size > 1) Arrangement.SpaceBetween
            else Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var selectedIndex by remember{ mutableIntStateOf(-1) }

        icons.forEachIndexed { index : Int, icon: Int ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .sizeIn(48.dp, 48.dp, 96.dp, 96.dp)
                    .weight(1f)
            ) {
                EquipmentButton(
                    tierLevel = TierLevel(index + 1),
                    image = icon,
                    selected = (selectedIndex == index),
                    onClick = { selectedIndex = if(selectedIndex != index) { index } else { -1 }
                    }
                )
            }

            if(index < icons.size - 1)
                Spacer(modifier = Modifier.width(8.dp))

        }
    }
}

@Composable
fun PossessionGroup(
    icons: ArrayList<Int> = arrayListOf(
        R.drawable.icon_cursedpossessions_monkeyspawhand,
        R.drawable.icon_cursedpossessions_monkeyspawwish
    )
) {
    Row(
        horizontalArrangement =
        if(icons.size > 1) Arrangement.SpaceBetween
        else Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        var selectedIndex by remember{ mutableIntStateOf(-1) }

        icons.forEachIndexed { index : Int, icon: Int ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .sizeIn(48.dp, 48.dp, 96.dp, 96.dp)
                    .weight(1f)
            ) {
                PossessionButton(
                    image = icon,
                    selected = (selectedIndex == index),
                    onClick = { selectedIndex = if(selectedIndex != index) { index } else { -1 } }
                )
            }

            if(index < icons.size - 1)
                Spacer(modifier = Modifier.width(8.dp))

        }
    }

}

fun createGroup(
    view: ComposeView?,
    icons: ArrayList<Int> = arrayListOf(0, 0, 0),
    itemType: ItemStoreType = Equipment
) {
    view?.setContent {
        when(itemType){
            Equipment -> EquipmentGroup(icons)
            Possession -> PossessionGroup(icons)
        }

    }
}

class ItemStoreType {

    companion object {
        val Equipment = ItemStoreType()
        val Possession = ItemStoreType()

        fun values(): List<ItemStoreType> = listOf(Equipment, Possession)
    }

}

fun setEquipmentSimple(
    composeView: ComposeView?,
    image: Int = 0,
    tierLevel: Int = TierLevel.One.value
) {
    composeView?.setContent {
        EquipmentSimple(
            image = image,
            tierLevel = TierLevel(tierLevel)
        )
    }
}