package com.github.leuludyha.ibdb.presentation.screen.barcode

import android.Manifest
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.github.leuludyha.domain.model.BarcodeAnalyser
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO REMOVE
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun BarcodeScreen(
    navController: NavHostController,
    outerPadding: PaddingValues,
    viewModel: BarcodeScreenViewModel = hiltViewModel(),
) {
    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colorScheme.primary

    SideEffect {
        systemUiController.setStatusBarColor(color = systemBarColor)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        val cameraPermissionState =
            rememberPermissionState(permission = Manifest.permission.CAMERA)

        Button(
            onClick = {
                cameraPermissionState.launchPermissionRequest()
            }
        ) {
            Text(text = "Camera Permission")
        }

        Spacer(modifier = Modifier.height(10.dp))

        CameraPreview { }
    }
}

@Composable
fun CameraPreview(barcodeFoundCallback: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }

    AndroidView(
        factory = { AndroidViewContext ->
            PreviewView(AndroidViewContext).apply {
                this.scaleType = PreviewView.ScaleType.FILL_CENTER
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        update = { previewView ->

            val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()
            val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
                ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                // Used to bind the lifecycle of cameras to the lifecycle owner
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                //Select back camera as default
                val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                // Preview
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                //This is our created class that will only look for ISBN barcodes in the image
                //(and execute the callback just defined for all the found barcodes
                val barcodeAnalyser = BarcodeAnalyser { barcodes ->
                    barcodes.forEach { barcode ->
                        barcode.rawValue?.let { barcodeValue ->
                            if (BarcodeAnalyser.checkISBNCode(barcodeValue)) {
                                Toast.makeText(context, barcodeValue, Toast.LENGTH_LONG).show()
                                barcodeFoundCallback(barcodeValue)
                            }
                        }
                    }
                }
                //This ImageAnalysis object takes care of feeding input images from the camera to
                //our created barcodeAnalyser.
                val imageAnalysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor, barcodeAnalyser)
                    }

                try {
                    //Here we are binding all elements just created to the lifetime of the cameraProvider.
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.d("BarcodeError", "CameraPreview: ${e.localizedMessage}")
                }
            }, ContextCompat.getMainExecutor(context))
        }
    )
}