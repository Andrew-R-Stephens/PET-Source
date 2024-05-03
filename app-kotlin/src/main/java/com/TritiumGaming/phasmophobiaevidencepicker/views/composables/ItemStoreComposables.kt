package com.TritiumGaming.phasmophobiaevidencepicker.views.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreType.Companion.Equipment
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.ItemStoreType.Companion.Possession

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

@Composable
@Preview
fun PaginationIcon(
    image: Int = R.drawable.icon_sh_emf,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    var isSelected by remember {
        mutableStateOf(selected)
    }

    Image(
        painter = painterResource(id = image),
        colorFilter = ColorFilter.tint(
            if(isSelected) Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.codex_4))
            else Color(ColorUtils.getColorFromAttribute(LocalContext.current, R.attr.codex_2))),
        contentDescription = "Pagination Image",
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(4.dp)
            .clickable {
                isSelected = true
                onClick()
            }
    )
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

fun setPossessionSimple(
    composeView: ComposeView?,
    image: Int = 0
) {
    composeView?.setContent {
        PossessionSimple(
            image = image
        )
    }
}

fun setItemButtonGroup(
    composeView: ComposeView?
) {
    composeView?.setContent {
        (
            EquipmentButton()
        )
    }
}

fun setPaginationIcon(
    composeView: ComposeView?,
    image: Int = R.drawable.icon_sh_emf,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    composeView?.setContent {
        (
            PaginationIcon(
                image = image,
                selected = selected,
                onClick = onClick
            )
        )
    }
}
