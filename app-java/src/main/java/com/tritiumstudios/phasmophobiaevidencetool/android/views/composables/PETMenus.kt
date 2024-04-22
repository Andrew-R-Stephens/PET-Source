package com.tritiumstudios.phasmophobiaevidencetool.android.views.composables

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.NavigationRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavDeepLinkBuilder
import com.tritiumstudios.phasmophobiaevidencetool.R


@Composable
@Preview
fun IconDropdownMenu(
    primaryContent: Any = Any(),
    @NavigationRes navGraphId: Int = R.navigation.titlescreen_navgraph,
    secondaryContentArray: Array<Any> = arrayOf(),
    @IntegerRes navigationRoutes: Array<Int> = arrayOf()
) {
    val localContext: Context = LocalContext.current

    val navDeepLinkBuilder = NavDeepLinkBuilder(localContext).setGraph(navGraphId)

    var expanded by remember { mutableStateOf(false) }

    val typedValue = TypedValue()
    localContext.theme.resolveAttribute(
        R.attr.backgroundColorOnBackground,
        typedValue,
        true
    )
    val backgroundColorOnBackground = Color(typedValue.data)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
            .background(Color.Transparent)
    ) {
        Box {
            PrimarySelector(
                { SelectorContent(primaryContent) },
                { expanded = true },
                modifier = Modifier.size(48.dp)
            )

            val  arrowImage : Int =
                if(expanded) R.drawable.ic_expand_circle_up
                else R.drawable.ic_expand_circle_down

            Box(
                modifier = Modifier
                    .background(backgroundColorOnBackground)
                    .clip(CircleShape)
                    .fillMaxSize(fraction = .4f)
                    .align(Alignment.BottomEnd)
            ) {
                Image(
                    painter = painterResource(id = arrowImage),
                    contentDescription = "CircleDown"
                )
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
                onDismissRequest = { expanded = false }
            ) {
                SecondarySelectors(
                    navDeepLinkBuilder = navDeepLinkBuilder,
                    contentArray = secondaryContentArray,
                    routes = navigationRoutes
                )
            }
        }
    }
}

@Composable
fun SecondarySelectors(
    modifier: Modifier = Modifier,
    navDeepLinkBuilder: NavDeepLinkBuilder,
    contentArray: Array<Any>,
    @IntegerRes routes: Array<Int> = arrayOf(R.id.appSettingsFragment)
) {
    Column {
        val maxRange = Math.min(contentArray.size, routes.size)

        for (index in 0..<maxRange) {
            val content = contentArray[index]
            val route = routes[index]

            SecondarySelector(
                navDeepLinkBuilder = navDeepLinkBuilder,
                navigationRoute = route,
                { SelectorContent(content) },
                modifier = Modifier
            )
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
        onClick = {
            setExpanded()
        },
        modifier = modifier
    ) {
        contentView()
    }
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
        },
        modifier = modifier
    ) {
        contentView()
    }
}

@Composable
fun SelectorContent(
    content: Any
) {
    when(content) {
        is Int -> SelectorContent(content)
        is View -> SelectorContent(contentView = content)
        is Composable -> SelectorContent(
            composable = { content as Composable }
        )
    }
}

@Composable
fun SelectorContent(
    @DrawableRes image: Int
) {
    Image(
        painter = painterResource(id = image),
        contentDescription = "Image"
    )
}

@Composable
fun SelectorContent(
    contentView: View
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
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
    composable()
}

fun setIconDropdownMenu(
    recipientView: ComposeView?,
    primaryContent: Any,
    @NavigationRes navGraphId: Int,
    secondaryContentArray: Array<Any>,
    @IntegerRes navigationRoutes: Array<Int>
) {
    recipientView?.setContent {
        IconDropdownMenu(
            primaryContent = primaryContent,
            navGraphId = navGraphId,
            secondaryContentArray = secondaryContentArray,
            navigationRoutes = navigationRoutes)
    }
}