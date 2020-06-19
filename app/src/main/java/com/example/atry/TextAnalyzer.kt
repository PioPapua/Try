package com.example.atry

import android.annotation.SuppressLint
import android.media.Image
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition

class TextAnalyzer(private val listener: TextListener) : ImageAnalysis.Analyzer {

    @SuppressLint("UnsafeExperimentalUsageError")
    override fun analyze(takenImage: ImageProxy) {
        val mediaImage: Image? = takenImage.image
        var capturedText: String = "0"

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, takenImage.imageInfo.rotationDegrees)
            val recognizer = TextRecognition.getClient()

            val result = recognizer.process(image)
                .addOnSuccessListener { visionText ->
                    val resultText = visionText.text
                    capturedText = resultText // Valor del texto capturado

                    for (block in visionText.textBlocks) {
                        val blockText = block.text
                        Log.d("BlockText capturado: ", blockText)
                        for (line in block.lines) {
                            val lineText = line.text
                            Log.d("LineText capturada: ", lineText)
                            for (element in line.elements) {
                                val elementText = element.text
                                Log.d("ElementText capturado: ", elementText)
                            }
                        }
                    }
                    listener(capturedText)
                    takenImage.close()
                }
                .addOnFailureListener {
                    listener(capturedText)
                    takenImage.close()
                }
        }
    }
}