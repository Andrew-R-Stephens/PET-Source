package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.util.BitmapUtils.Companion.bitmapExists
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.model.SimpleWorldMapFloor
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.MapsViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.model.InteractiveViewController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay.model.InteractiveWorldMap
import kotlin.math.min


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

    OperationScreen(
        navController = navController,
    ) {
        MapViewerContent(
            mapsViewModel = mapsViewModel,
            mapId = mapId
        )
    }

}

@Composable
private fun MapViewerContent(
    mapsViewModel: MapsViewModel,
    mapId: String
) {

    val mapUiState = mapsViewModel.mapDisplayUiState.collectAsStateWithLifecycle()
    mapsViewModel.setCurrentMapId(mapId)

    Log.d("MapViewerScreen", "mapId: $mapId")

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        mapsViewModel.interactiveWorldMap?.let { map ->
            MapCanvas(
                map = map
            )
        }

        Text(
            text = "${mapUiState.value.currentId} " +
                    "${mapUiState.value.currentFloor} ",
            style = LocalTypography.current.primary.regular,
            color = LocalPalette.current.textFamily.primary
        )
    }

}

@Composable
private fun MapCanvas(
    map: InteractiveWorldMap
) {
    val context = LocalContext.current

    val floorImageRef: SimpleMapResources.MapFloorImage = map.currentFloorImage
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

    val scaleX = inputController.matrixValues[Matrix.MSCALE_X]
    val scaleY = inputController.matrixValues[Matrix.MSCALE_Y]
    val transX = inputController.matrixValues[Matrix.MTRANS_X]
    val transY = inputController.matrixValues[Matrix.MTRANS_Y]

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
                    interactiveViewController = inputController
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
            var touchX = 0f
            var touchY = 0f

            drawContext.canvas.save()
            drawContext.canvas.nativeCanvas.concat(inputController.matrix)
            drawImage(
                image = floorImage
            )
            drawContext.canvas.restore()

            /*paint.strokeWidth = 3f
            wallpath.reset()

            selectedRoomModel?.let { selectedRoomModel ->
                val points: List<PointF> = selectedRoomModel.roomArea.points
                val firstPoint = points[0]
                wallpath.moveTo(
                    (panX) + (firstPoint.x * scaleX),
                    (panY) + (firstPoint.y * scaleY)
                ) // used for first point
                for (i in 1 until points.size) {
                    wallpath.lineTo(
                        (panX) + points[i].x * scaleX,
                        (panY) + points[i].y * scaleY
                    )
                }
                wallpath.lineTo(
                    (panX) + (firstPoint.x * scaleX),
                    (panY) + (firstPoint.y * scaleY)
                )

                paint.color = selectedFillColor
                paint.style = Paint.Style.FILL
                canvas.drawPath(wallpath, paint)

                paint.color = selectedBorderColor
                paint.style = Paint.Style.STROKE
                canvas.drawPath(wallpath, paint)
            }

            val fontSize = (scaleX / width) * 24
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


            /*Log.d("MapCanvas", "$interactionSourceMillis")
            var scaleX: Float
            var scaleY: Float
            var panX: Float
            var panY: Float
            var touchX = 0f
            var touchY = 0f

            interactiveViewController.postCenterTranslateMatrix(
                100f,
                100f,
                displayWidth.toFloat(),
                displayHeight.toFloat()
            )

            val matrix: FloatArray = interactiveViewController.matrixValues
            scaleX = matrix[Matrix.MSCALE_X]
            scaleY = matrix[Matrix.MSCALE_Y]
            panX = matrix[Matrix.MTRANS_X]
            panY = matrix[Matrix.MTRANS_Y]

            drawRect(
                color = Red,
                topLeft = Offset(panX, panY),
                size = Size(scaleX, scaleY)
            )

            Log.d("MapViewerScreen", "\tView Bounds: $displayWidth, $displayHeight" +
                    "\n\tMatrix Scale: $scaleX $scaleY" +
                    "\n\tMatrix Trans: $transX $transY")*/

        }
    }

    Column {

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "View Bounds: $displayWidth, $displayHeight" +
                    "\nMatrix Scale: $scaleX $scaleY" +
                    "\nMatrix Trans: $transX $transY",
            style = LocalTypography.current.quaternary.regular,
            color = LocalPalette.current.textFamily.body,
            fontSize = 24.sp
        )
    }
}

fun Modifier.mapControlInput(
    interactiveViewController: InteractiveViewController,
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
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    if (pointerCount == 1) {
                        interactiveViewController.incrementPan(pan.x, pan.y)
                        onInteraction()
                    }
                    if (pointerCount == 2) {
                        interactiveViewController.addZoom(zoom)
                        onInteraction()
                    }
                }
            }
    )
}
