package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.labels.DynamicContentAlignmentPercentage
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.labels.DynamicContentRow
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButton
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.components.navigation.PETImageButtonType
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.Arrow60LeftIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.Arrow60RightIcon
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.vector.getArrow60LeftVector
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldPoint
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.mission.model.Mission
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.MapsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.model.InteractiveViewController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions.ObjectivesViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.component.GridPattern
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.util.graphics.geometry.Point2D
import kotlin.collections.forEach


@Composable
fun MapViewerScreen(
    navController: NavHostController = rememberNavController(),
    mapsViewModel: MapsViewModel,
    mapId: String
) {
    mapsViewModel.setCurrentMapId(mapId)

    OperationScreen(
        navController = navController,
    ) {
        MapViewerContent(
            mapsViewModel = mapsViewModel
        )
    }

}

@Composable
private fun MapViewerContent(
    mapsViewModel: MapsViewModel
) {

    val mapDisplayUiState = mapsViewModel.mapDisplayUiState.collectAsStateWithLifecycle()
    val selectedFloorIndex = mapDisplayUiState.value.floorIndex

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        BackgroundGrid(
            modifier = Modifier
                .fillMaxSize()
        )

        MapCanvas(
            mapsViewModel = mapsViewModel
        )

        UiController(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            mapsViewModel = mapsViewModel
        )
    }

}

@Composable
private fun MapCanvas(
    mapsViewModel: MapsViewModel
) {
    val context = LocalContext.current

    val mapDisplayUiState = mapsViewModel.mapDisplayUiState.collectAsStateWithLifecycle()
    val selectedFloorIndex = mapDisplayUiState.value.floorIndex

    val selectedRoom = mapDisplayUiState.value.roomId.let { roomId ->
        mapsViewModel.getRoomById(roomId)
    }

    val floorImageRef: SimpleMapResources.MapFloorImage = mapsViewModel.getFloorImage()
    @DrawableRes val floorImageRes: Int = floorImageRef.toDrawableResource()
    val floorImage: ImageBitmap =
        BitmapFactory.decodeResource(context.resources, floorImageRes).asImageBitmap()

    val inputController by remember{ mutableStateOf( InteractiveViewController()) }
    inputController.setImageSize(floorImage.width, floorImage.height)
    inputController.updateMatrix()

    var interactionSourceMillis by remember {
        mutableLongStateOf(System.currentTimeMillis())
    }
    var displayWidth by remember {
        mutableIntStateOf(0)
    }
    var displayHeight by remember {
        mutableIntStateOf(0)
    }

    val wallPath = Path()
    val wallPathColor = LocalPalette.current.mapRoomBorderColor
    val wallFillColor = LocalPalette.current.mapRoomFillColor


    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                displayWidth = it.width
                displayHeight = it.height
                inputController.setContainerSize(it.width, it.height)
            }
            .mapControlInput(
                interactiveViewController = inputController,
                onTap = { x, y ->

                    val matrix = inputController.matrixValues
                    val scaleX = matrix[Matrix.MSCALE_X]
                    val scaleY = matrix[Matrix.MSCALE_Y]
                    val translateX = matrix[Matrix.MTRANS_X]
                    val translateY = matrix[Matrix.MTRANS_Y]

                    mapsViewModel.setSelectedRoomAtPoint(
                        point = Point2D.Point2DFloat(x, y),
                        scaleX = scaleX,
                        scaleY = scaleY,
                        translateX = translateX,
                        translateY = translateY
                    )

                }
            ) {
                interactionSourceMillis = System.currentTimeMillis()
            }
    ) {
        Log.d("MapCanvas", "$interactionSourceMillis")

        inputController.postCenterTranslateMatrix(
            floorImage.width.toFloat(),
            floorImage.height.toFloat(),
            displayWidth.toFloat(),
            displayHeight.toFloat()
        )

        val matrix: FloatArray = inputController.matrixValues
        val scaleX = matrix[Matrix.MSCALE_X]
        val scaleY = matrix[Matrix.MSCALE_Y]
        val panX = matrix[Matrix.MTRANS_X]
        val panY = matrix[Matrix.MTRANS_Y]

        drawContext.canvas.save()
        drawContext.canvas.nativeCanvas.concat(inputController.matrix)
        drawImage(
            image = floorImage
        )
        drawContext.canvas.restore()

        wallPath.reset()

        selectedRoom?.let { room ->
            val points: List<ComplexWorldPoint> = room.roomArea.points
            val firstPoint = try {
                points[0]
            } catch (e: Exception) { // There are no points for this room
                e.printStackTrace()
                return@let
            }

            wallPath.moveTo(
                (panX) + (firstPoint.x * scaleX),
                (panY) + (firstPoint.y * scaleY)
            ) // used for first point
            for (i in 1 until points.size) {
                wallPath.lineTo(
                    (panX) + points[i].x * scaleX,
                    (panY) + points[i].y * scaleY
                )
            }
            wallPath.lineTo(
                (panX) + (firstPoint.x * scaleX),
                (panY) + (firstPoint.y * scaleY)
            )

            drawPath(
                wallPath,
                color = wallFillColor,
                style = Fill
            )

            drawPath(
                wallPath,
                color = wallPathColor,
                style = Stroke(
                    width = 3f
                )
            )
        }

        /*val fontSize = (scaleX / width) * 24
        paint.textSize = min(36.0, max(12.0, fontSize.toDouble())).toFloat()

        paint.isAntiAlias = true
        paint.color = poiColor
        paint.setColorFilter(poiColorFilter)

        mapMenuViewModel?.currentMapModel?.let { currentMapModel ->
            for (poi in currentMapModel.currentFloor.floorPOIs) {
                var x = panX
                var y = panY
                poi.point?.let { point ->
                    x = (panX) + point.x * scaleX
                    y = (panY) + point.y * scaleY
                }

                val b = poiImages[poi.type]
                if (bitmapExists(b)) {
                    b?.let {
                        interactivePoiModel.deepCopy(interactiveMapData)
                        interactivePoiModel.setPan(x, y)
                        interactivePoiModel.postTranslateOriginMatrix(
                            b.width.toFloat(), b.height.toFloat(),
                            width.toFloat(), height.toFloat()
                        )
                        canvas.drawBitmap(b, interactivePoiModel.matrix, paint)
                    }

                }
            }
        }

        paint.isAntiAlias = false

        if (frameRect == null) {
            frameRect = Rect(1, 1, width - 1, height - 1)
        } else {
            frameRect?.bottom = height - 1
        }

        paint.setColorFilter(null)
        paint.color = mapBorderColor
        paint.style = Paint.Style.STROKE
        frameRect?.let { frameRect ->
            canvas.drawRect(frameRect, paint)
        }*/

    }

}

private fun Modifier.mapControlInput(
    interactiveViewController: InteractiveViewController,
    onTap: (Float, Float) -> Unit,
    onInteraction: () -> Unit
): Modifier {

    var pointerCount = 0

    return this.then(
        Modifier
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()

                        pointerCount = event.changes.size
                    }
                }
            }
            .pointerInput(Unit) {
                detectTapGestures { tap ->
                    if (pointerCount == 1) {
                        onTap(tap.x, tap.y)
                        onInteraction()
                    }
                }
            }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    if (pointerCount == 1) {
                        interactiveViewController.doPan(pan.x, pan.y)
                        onInteraction()
                    }
                    if (pointerCount == 2) {
                        interactiveViewController.doZoom(zoom)
                        onInteraction()
                    }
                }
            }
    )
}

@Composable
private fun UiController(
    modifier: Modifier = Modifier,
    mapsViewModel: MapsViewModel,
) {

    val mapDisplayUiState = mapsViewModel.mapDisplayUiState.collectAsStateWithLifecycle()
    val floorIndex = mapDisplayUiState.value.floorIndex

    val currentMap = mapsViewModel.getSimpleMap()
    val floorCount: Int = currentMap.floorCount
    val mapTitle: SimpleMapResources.MapTitle = currentMap.mapName
    val floorTitle: SimpleMapResources.MapFloorTitle = currentMap.mapFloors[floorIndex].layerName

    Column(
        modifier = modifier
            .background(LocalPalette.current.backgroundColor_mapviewOverlay),
        verticalArrangement = Arrangement.spacedBy(2.dp),
    ) {

        val rememberLazyListState = rememberLazyListState()
        LaunchedEffect(floorIndex) {
            rememberLazyListState.animateScrollToItem(floorIndex)
        }

        DynamicContentRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 16.dp),
            maxPercentage = DynamicContentAlignmentPercentage(
                maxPercentage = .5f,
                alignment = Alignment.End
            ),
            startComponent = {
                Text(
                    modifier = Modifier
                        .height(24.dp)
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            initialDelayMillis = 1000,
                            repeatDelayMillis = 5000
                        )
                        .padding(end = 16.dp),
                    text = stringResource(mapTitle.toStringResource()),
                    style = LocalTypography.current.quaternary.bold,
                    fontSize = 24.sp,
                    color = LocalPalette.current.textFamily.body
                )
            },
            endComponent = {
                LazyRow(
                    state = rememberLazyListState
                ) {
                    items(floorCount) { index ->
                        val icon = if(floorIndex == index) R.drawable.ic_selector_sel
                        else R.drawable.ic_selector_unsel

                        Image(
                            modifier = Modifier
                                .size(24.dp)
                                .padding(5.dp, 2.dp, 5.dp, 2.dp),
                            painter = painterResource(icon),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(
                                LocalPalette.current.textFamily.body)
                        )
                    }

                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .size(36.dp),
                painter = painterResource(R.drawable.ic_stairs),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Arrow60LeftIcon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = {
                            mapsViewModel.decrementFloorIndex()
                        })
                        .padding(8.dp),
                    tint = listOf(LocalPalette.current.textFamily.body)
                )

                Text(
                    text = stringResource(floorTitle.toStringResource()),
                    style = LocalTypography.current.quaternary.bold,
                    textAlign = TextAlign.Center,
                    color = LocalPalette.current.textFamily.body
                )

                Arrow60RightIcon(
                    modifier = Modifier
                        .size(48.dp)
                        .clickable(onClick = {
                            mapsViewModel.incrementFloorIndex()
                        })
                        .padding(8.dp),
                    tint = listOf(LocalPalette.current.textFamily.body)
                )

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .size(36.dp),
                painter = painterResource(R.drawable.ic_room),
                contentScale = ContentScale.Fit,
                contentDescription = null,
                colorFilter = ColorFilter.tint(LocalPalette.current.textFamily.body)
            )

            RoomDropdownWrapper(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                mapsViewModel = mapsViewModel
            )

        }

    }

}

@Composable
private fun BackgroundGrid(
    modifier: Modifier = Modifier
) {

    Image(
        modifier = modifier,
        painter = painterResource(R.drawable.itemstore_grid),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        alpha = .40f
    )

}

@Preview
@Composable
private fun UiControllerPreview(

) {
    SelectiveTheme {

        UiController(
            modifier = Modifier
                .fillMaxSize(),
            mapsViewModel = viewModel(factory = MapsViewModel.Factory)
        )

    }
}

@Preview
@Composable
private fun BackgroundGridPreview(

) {
    SelectiveTheme {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalPalette.current.background.color)
        ) {

            BackgroundGrid(
                modifier = Modifier
                    .fillMaxSize()
            )

        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDropdownWrapper(
    modifier: Modifier = Modifier,
    mapsViewModel: MapsViewModel
) {

    val mapDisplayUiState = mapsViewModel.mapDisplayUiState.collectAsStateWithLifecycle()
    val roomName = mapsViewModel.getRoomNameById(mapDisplayUiState.value.roomId) ?: ""

    val roomList = mapsViewModel.getRooms().filterNot {
        room -> room.id == mapDisplayUiState.value.roomId }

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                    .padding(horizontal = 4.dp),
                value = roomName,
                textStyle = LocalTypography.current.quaternary.regular.copy(
                    color = LocalPalette.current.textFamily.body,
                    fontSize = 14.sp
                ),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                maxLines = 2,
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
                onValueChange = {},
                readOnly = true,
            )

        }

        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = LocalPalette.current.surface.onColor,
            shape = RoundedCornerShape(
                bottomStart = 8.dp,
                bottomEnd = 8.dp
            ),
            scrollState = rememberScrollState(),
            matchAnchorWidth = true,
        ) {

            roomList.forEach { it ->
                DropdownMenuItem(
                    text =  {
                        Text(
                            text = it.name,
                            style = LocalTypography.current.quaternary.regular,
                            color = LocalPalette.current.textFamily.body,
                            fontSize = 18.sp
                        )
                    },
                    colors = MenuDefaults.itemColors().copy(
                        textColor = LocalPalette.current.textFamily.body,
                    ),
                    onClick = {
                        expanded = false
                        mapsViewModel.setCurrentRoomId(it.id)
                    },
                )
            }

        }
    }

}
