package com.example.atry

import android.annotation.SuppressLint
import android.media.Image
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer(private val listener: BarcodeListener) : ImageAnalysis.Analyzer {

    val options = BarcodeScannerOptions.Builder()
        .setBarcodeFormats(Barcode.FORMAT_EAN_13)
        .build()

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(takenImage: ImageProxy) {
        val mediaImage: Image? = takenImage.image
        var rawValue: String = "0"

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, takenImage.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient(options)

            val result = scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        rawValue = barcode.rawValue.toString() // Valor del código de barras
                        listener(rawValue)
                    }
                    takenImage.close()
                }
                .addOnFailureListener {
                    listener(rawValue)
                    takenImage.close()
                }
        }
    }
}