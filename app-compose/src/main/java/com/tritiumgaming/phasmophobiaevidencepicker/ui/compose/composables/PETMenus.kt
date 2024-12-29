package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.composables

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.NavigationRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkBuilder
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.navigation.NavRoutes
import com.tritiumgaming.phasmophobiaevidencepicker.theme.colorSchemes.LocalPalette
import org.jetbrains.annotations.TestOnly

@TestOnly
@Preview
@Composable
fun TestMenuIcons() {

    IconDropdownMenu(
        R.drawable.ic_menu,
        R.navigation.titlescreen_navgraph,
        arrayOf(
            DropdownNavigationPair(R.drawable.ic_gear, R.id.appSettingsFragment),
            DropdownNavigationPair(R.drawable.ic_person, R.id.appLanguageFragment),
            DropdownClickPair(R.drawable.ic_discord) { }
        ),
    ) { false }
}


@Composable
fun IconDropdownMenu(
    primaryContent: Any = Any(),
    navController: NavController,
    secondaryContentArray: Array<DropdownPair> = arrayOf(),
    modifier: Modifier = Modifier
        .size(48.dp),
    showExpandIcon: () -> Boolean = { true },
) {
    val localContext: Context = LocalContext.current

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
            .background(Color.Transparent)
    ) {

        Box {
            PrimarySelector(
                { SelectorContent(primaryContent) },
                { expanded = true },
                modifier = Modifier.size(48.dp)
            )

            if(showExpandIcon()) {
                val arrowImage: Int =
                    if (expanded) R.drawable.ic_expand_circle_up
                    else R.drawable.ic_expand_circle_down

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(LocalPalette.current.surface.onColor)
                        .fillMaxSize(fraction = .4f)
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(id = arrowImage),
                        contentDescription = "Arrow"
                    )
                }
            }
        }

        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                surface = LocalPalette.current.surface.onColor,
                background = Color.Transparent,
                onBackground = Color.Transparent,
                surfaceContainer = Color.Transparent,
                onPrimary = Color.Transparent,
                surfaceContainerLowest = Color.Transparent
            ),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(48.dp)
                    .padding(4.dp)
                    .align(Alignment.Center),
                scrollState = rememberScrollState()
            ) {
                SecondarySelectors(
                    navController = navController,
                    contentArray = secondaryContentArray
                )
            }
        }
    }
}


@Composable
fun IconDropdownMenu(
    primaryContent: Any = Any(),
    @NavigationRes navGraphId: Int = R.navigation.titlescreen_navgraph,
    secondaryContentArray: Array<DropdownPair> = arrayOf(),
    modifier: Modifier = Modifier
        .size(48.dp),
    showExpandIcon: () -> Boolean = { true },
) {
    val localContext: Context = LocalContext.current

    val navDeepLinkBuilder = NavDeepLinkBuilder(localContext).setGraph(navGraphId)

    var expanded by remember { mutableStateOf(false) }

    val typedValue = TypedValue()
    localContext.theme.resolveAttribute(
        R.attr.backgroundColorOnBackground,
        typedValue,
        true)
    val backgroundColorOnBackground = Color(typedValue.data)

    Box(
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
            .background(Color.Transparent)
    ) {

        Box {
            PrimarySelector(
                { SelectorContent(primaryContent) },
                { expanded = true },
                modifier = Modifier.size(48.dp)
            )

            if(showExpandIcon()) {
                val arrowImage: Int =
                    if (expanded) R.drawable.ic_expand_circle_up
                    else R.drawable.ic_expand_circle_down

                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(backgroundColorOnBackground)
                        .fillMaxSize(fraction = .4f)
                        .align(Alignment.BottomEnd)
                ) {
                    Image(
                        painter = painterResource(id = arrowImage),
                        contentDescription = "Arrow"
                    )
                }
            }
        }

        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme.copy(
                surface = backgroundColorOnBackground,
                background = Color.Transparent,
                onBackground = Color.Transparent,
                surfaceContainer = Color.Transparent,
                onPrimary = Color.Transparent,
                surfaceContainerLowest = Color.Transparent
            ),
            shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(20))
        ) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .width(48.dp)
                    .padding(4.dp)
                    .align(Alignment.Center),
                scrollState = rememberScrollState()
            ) {
                SecondarySelectors(
                    navDeepLinkBuilder = navDeepLinkBuilder,
                    contentArray = secondaryContentArray
                )
            }
        }
    }
}

@Composable
fun SecondarySelectors(
    modifier: Modifier = Modifier,
    navController: NavController,
    contentArray: Array<DropdownPair>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (index in contentArray.indices) {
            when(val content = contentArray[index]) {
                is DropdownClickPair -> {
                    SecondarySelector(
                        content.onClick,
                        { SelectorContent(content.content) },
                        modifier = Modifier
                    )
                }
                is DropdownNavPair -> {
                    SecondarySelector(
                        navController = navController,
                        navigationRoute = content.navigationRoute,
                        { SelectorContent(content.content) },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun SecondarySelectors(
    modifier: Modifier = Modifier,
    navDeepLinkBuilder: NavDeepLinkBuilder,
    contentArray: Array<DropdownPair>
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (index in contentArray.indices) {
            when(val content = contentArray[index]) {
                is DropdownClickPair -> {
                    SecondarySelector(
                        content.onClick,
                        { SelectorContent(content.content) },
                        modifier = Modifier
                    )
                }
                is DropdownNavigationPair -> {
                    SecondarySelector(
                        navDeepLinkBuilder = navDeepLinkBuilder,
                        navigationRoute = content.navigationRoute,
                        { SelectorContent(content.content) },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@Composable
fun PrimarySelector(
    contentView: @Composable () -> Unit,
    setExpanded: () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = { setExpanded() }
    ) { contentView() }
}

@Composable
fun SecondarySelector(
    navController: NavController? = null,
    navigationRoute: NavRoutes? = null,
    contentView: @Composable () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = {
            navigationRoute?.route?.let { navController?.navigate(it) }
        }
    ) { contentView() }
}

@Composable
fun SecondarySelector(
    navDeepLinkBuilder: NavDeepLinkBuilder,
    navigationRoute: Int = R.id.appSettingsFragment,
    contentView: @Composable () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = {
            navDeepLinkBuilder
                .setDestination(navigationRoute)
                .createPendingIntent().send()
        }
    ) { contentView() }
}

@Composable
fun SecondarySelector(
    onClick: () -> Unit = {},
    contentView: @Composable () -> Unit,
    modifier: Modifier
) {
    IconButton(
        onClick = { onClick() }
    ) { contentView() }
}

@Composable
fun SelectorContent(
    content: Any? = null
) {
    content?.let {
        when(it) {
            is Int -> SelectorContent(image = it)
            is View -> SelectorContent(contentView = it)
            else -> {
                (it as? @Composable () -> Unit)?.let { composable ->
                    SelectorContent(
                        composable = composable
                    )
                }

            }
        }
    }

}

@Composable
fun SelectorContent(
    @DrawableRes image: Int
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "Image",
        modifier = Modifier
            .aspectRatio(1f)
    )
}

@Composable
fun SelectorContent(
    contentView: View
) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f),
        factory = {
            contentView
        },
        update = { view ->
            view.invalidate()
        }
    )
}

@Composable
fun SelectorContent(
    composable: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
    ) {
        composable()
    }
}

abstract class DropdownPair
data class DropdownClickPair(val content: Any, val onClick: () -> Unit = {}):
    DropdownPair()
data class DropdownNavigationPair(val content: Any, val navigationRoute: Int):
    DropdownPair()
data class DropdownNavPair(val content: Any, val navigationRoute: NavRoutes):
    DropdownPair()
