package com.github.leuludyha.domain.model.library

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
) : ImageAnalysis.Analyzer {

    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            //EAN13 is the format ISBN barcodes are encoded in, no need to look for the others.
            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                .build()

            val scanner = BarcodeScanning.getClient(options)

            scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    onISBNBarcodeDetected(barcodes.filter { barcode ->
                        barcode.valueType == Barcode.TYPE_ISBN
                    })
                }
                .addOnFailureListener { exception ->
                    Log.d(
                        "BarcodeError",
                        "com.github.leuludyha.domain.model.library.BarcodeAnalyser: Something went wrong $exception"
                    )
                }.addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    companion object {
        /*
            Checks that the ISBN 13 code is correct assuming EAN13 encoding (works for books published after 2005. Earlier works
            might have an ISBN 10 code if they haven't been adapted, not verified here).
            Verified as explained here: https://en.wikipedia.org/wiki/ISBN
         */
        fun checkISBNCode(barcode: String): Boolean {
            if (barcode.length != 13) {
                Log.i(
                    "BarcodeError",
                    "com.github.domain.model.BarcodeAnalyser: The barcode doesn't have the proper length."
                )
                return false
            }

            var res = 0
            barcode.forEachIndexed { index, c ->
                run {
                    try {
                        res += Integer.parseInt(c.toString()) * (if (index % 2 == 0) 1 else 3)
                    } catch (e: Exception) {
                        if (e is java.lang.NumberFormatException) {
                            Log.i(
                                "BarcodeError",
                                "com.github.domain.model.BarcodeAnalyser: The barcode is not a number."
                            )
                            return false
                        }
                        throw e
                    }
                }
            }

            return (res % 10) == 0
        }
    }
}