package com.github.leuludyha.ibdb.presentation.screen

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.github.leuludyha.domain.model.BarcodeAnalyser
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/*
    This class allows to scan a barcode.
    It has been built by following this tutorial: https://developer.android.com/codelabs/camerax-getting-started
    In order to check how to get the resulting barcode from this activity and how to call it, check this page:
    https://stackoverflow.com/questions/14785806/android-how-to-make-an-activity-return-results-to-the-activity-which-calls-it
 */
class BarcodeScanningActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.height(10.dp))

                    val cameraPermissionState =
                        rememberPermissionState(permission = android.Manifest.permission.CAMERA)

                    Button(
                        onClick = {
                            cameraPermissionState.launchPermissionRequest()
                        }
                    ) {
                        Text(text = "Camera Permission")
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    val barCodeVal = remember { mutableStateOf("") }
                    CameraPreview(barCodeVal) {
                        //TODO: Uncomment these lines once this function is called from another activity, it is commented for
                        // now for testing purposes, so that the activity may be launched independently
//                        val data = Intent()
//                        data.putExtra("ISBN_code", barCodeVal.value)
//                        setResult(RESULT_OK, data)
//                        finish()
                        Log.i("BarcodeTest", "The callback has been called to finish the activity.")
                    }
                }
            }
        }
    }
}

@Composable
fun CameraPreview(barCodeVal: MutableState<String>, barcodeFoundCallback: () -> Unit) {
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
                val cameraSelector : CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                // Preview
                preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                //This is our created class that will only look for ISBN barcodes in the image
                //(and execute the callback just defined for all the found barcodes
                val barcodeAnalyser = BarcodeAnalyser { barcodes ->
                    barcodes.forEach { barcode ->
                        barcode.rawValue?.let { barcodeValue ->
                            if(BarcodeAnalyser.checkISBNCode(barcodeValue)) {
                                barCodeVal.value = barcodeValue
                                Toast.makeText(context, barcodeValue, Toast.LENGTH_LONG).show()
                                barcodeFoundCallback()
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
