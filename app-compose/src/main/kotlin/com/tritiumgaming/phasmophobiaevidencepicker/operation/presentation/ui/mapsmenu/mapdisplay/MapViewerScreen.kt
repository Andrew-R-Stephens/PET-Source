package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay

import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.complex.model.ComplexWorldPoint
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.MapsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.model.InteractiveViewController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.util.graphics.geometry.Point2D


@Composable
@Preview
private fun MapViewerScreenPreview() {
    SelectiveTheme {
        MapViewerScreen(
            mapsViewModel = viewModel ( factory = MapsViewModel.Factory ),
            mapId = "0"
        )
    }
}

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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        MapCanvas(
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
    Log.d("MapCanvas", "floorIndex: $selectedFloorIndex")

    val selectedRoom = mapDisplayUiState.value.roomId?.let { roomId ->
        mapsViewModel.getRoomById(roomId)
    }
    Log.d("MapCanvas", "selectedRoom: $selectedRoom")

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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray)
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

        Button(
            onClick = {
                mapsViewModel.incrementFloorIndex()
            }
        ) {
            Text(text = "$selectedFloorIndex")
        }
    }

}

fun Modifier.mapControlInput(
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
                        println("Number of active pointers: $pointerCount")
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
