package com.github.leuludyha.domain.model

import android.annotation.SuppressLint
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

/*
    This code Analyzer allows to search for (exclusively) ISBN barcodes on an image.
    It has been built following this tutorial: https://developers.google.com/ml-kit/vision/barcode-scanning/android
 */
@SuppressLint("UnsafeOptInUsageError")
class BarcodeAnalyser(
    private val onISBNBarcodeDetected: (barcodes: List<Barcode>) -> Unit,
): ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        Log.println(Log.INFO, "test", "I am analyzing an image!")
        val mediaImage = imageProxy.image
        if(mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            //EAN13 is the format ISBN barcodes are encoded in, no need to look for the others.
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                .build()

            val scanner = BarcodeScanning.getClient(options)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    Log.println(Log.INFO, "test", "I found some barcodes!!")
                    onISBNBarcodeDetected(barcodes.filter {
                        barcode -> barcode.valueType == Barcode.TYPE_ISBN
                    })
                }
                .addOnFailureListener {
                    exception -> Log.d("TAG", "com.github.leuludyha.domain.model.BarcodeAnalyser: Something went wrong $exception")
                }.addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}