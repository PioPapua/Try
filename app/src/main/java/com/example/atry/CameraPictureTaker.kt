package com.example.atry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import kotlinx.android.synthetic.main.activity_camera_picture_taker.*
import java.util.concurrent.ExecutorService

typealias BarcodeListener = (barcode: String) -> Unit
typealias TextListener = (text: String) -> Unit

class CameraPictureTaker : AppCompatActivity() {
    private var preview: Preview? = null
    private var barcodeImageCapture: ImageCapture? = null
    private var textImageCapture: ImageCapture? = null
    private var barcodeAnalyzer: ImageAnalysis? = null
    private var textAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private lateinit var cameraExecutor: ExecutorService
    var barcode: String? = null
    var textRecognized: String? = null

    companion object {
        private const val TAG = "CameraXBasic"
        private const val REQUEST_CODE_PERMISSIONS = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_picture_taker)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        scan_button.visibility = View.GONE

        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Select back camera
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            preview = Preview.Builder()
                .build()

            barcodeImageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            textImageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY)
                .build()

            barcodeAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { barcodeNumber ->
                        var toast = Toast.makeText(this,
                            "Código de barras: ${barcodeNumber}. Enfoque la tabla de información nutricional.",
                            Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.TOP, 0, 0)
                            toast.show()
                            scan_button.visibility = View.VISIBLE
                            scan_button.setOnClickListener {
                                analyze(cameraProvider, cameraSelector, textImageCapture, textAnalyzer, preview)
                            }
                            barcode = barcodeNumber
                    })
                }

            textAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, TextAnalyzer { text ->
                        Log.d("Texto capturado en UI: ", text)
                        if (text.length > 10) {
                            cameraExecutor.shutdown()
                        }
                        textRecognized = text
                        onImageProcessFinished()
                    })
                }

            analyze (cameraProvider, cameraSelector, barcodeImageCapture, barcodeAnalyzer, preview)

        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun analyze (cameraProvider: ProcessCameraProvider,
                         cameraSelector: CameraSelector,
                         imageCapture: ImageCapture?,
                         imageAnalyzer: ImageAnalysis?,
                         preview: Preview?) {
        try {
            // Unbind use cases before rebinding
            cameraProvider.unbindAll()

            // Bind use cases to camera
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, imageCapture, imageAnalyzer, preview)
            preview?.setSurfaceProvider(viewFinder.createSurfaceProvider(camera?.cameraInfo))
        } catch(exc: Exception) {
            Log.e("TAG", "Use case binding failed", exc)
        }
    }

    private fun onImageProcessFinished() {
        //TODO Call next fragment to process text and display form.
    }
}