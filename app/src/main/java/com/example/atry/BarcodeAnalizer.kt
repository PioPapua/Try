package com.example.atry

import android.media.Image
import android.util.Log
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

    override fun analyze(takenImage: ImageProxy) {
        val mediaImage: Image? = takenImage.image
        val rawValue: String = "0"

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, takenImage.imageInfo.rotationDegrees)
            val scanner = BarcodeScanning.getClient(options)

            val result = scanner.process(image)
                .addOnSuccessListener { barcodes ->
                    for (barcode in barcodes) {
                        val rawValue = barcode.rawValue // Valor del código de barras
                        Log.d("Codigo de barras: ", rawValue)
                    }
                    takenImage.close()
                }
                .addOnFailureListener {
                    takenImage.close()
                }
        } else {
            Log.d("mediaImage ", "es null")
        }
        listener (rawValue)
    }
}