package com.example.atry

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.atry.databinding.FragmentCameraPictureBinding
import com.example.atry.product.ProductViewModel
import kotlinx.android.synthetic.main.fragment_camera_picture.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias BarcodeListener = (barcode: String) -> Unit
typealias TextListener = (text: String) -> Unit

class CameraPicture : Fragment() {
    private lateinit var viewModel: ProductViewModel

    private var preview: Preview? = null
    private var barcodeImageCapture: ImageCapture? = null
    private var textImageCapture: ImageCapture? = null
    private var barcodeAnalyzer: ImageAnalysis? = null
    private var textAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var barcode: String? = null
    private var textRecognized: String? = null
    private lateinit var cameraExecutor: ExecutorService

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentCameraPictureBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera_picture, container, false)
        viewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkCameraPermissions()
    }

    private fun checkCameraPermissions() {
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions(
                CameraPicture.REQUIRED_PERMISSIONS,
                CameraPicture.REQUEST_CODE_PERMISSIONS
            )
        }
        cameraExecutor = Executors.newSingleThreadExecutor()
    }


private fun startCamera() {
    var notInformed: Boolean = true
    val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
    scan_button.visibility = View.GONE

    cameraProviderFuture.addListener(Runnable {
        // Used to bind the lifecycle of cameras to the lifecycleOwner
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
                it.setAnalyzer(cameraExecutor, AnalyzerBarcode { barcodeNumber ->

                    scan_button.visibility = View.VISIBLE
                    scan_button.setOnClickListener {
                        analyze(cameraProvider, cameraSelector, textImageCapture, textAnalyzer, preview)
                    }
                    if (barcodeNumber.length == 13 && notInformed) {
                        val toast = Toast.makeText(activity,
                            "Código de barras: ${barcodeNumber}. Enfoque la tabla de información nutricional.",
                            Toast.LENGTH_SHORT)
                        toast.setGravity(Gravity.TOP, 0, 0)
                        toast.show()
                        notInformed = false
                        barcode = barcodeNumber
                    }
                })
            }

        textAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, AnalyzerText { text ->
                    if (text.length > 10) {
                        cameraExecutor.shutdown()
                        textRecognized = text
                        val action = CameraPictureDirections.actionCameraPictureToProduct(barcode!!)
                        this.findNavController().navigate(action)
                    }
                })
            }

        analyze (cameraProvider, cameraSelector, barcodeImageCapture, barcodeAnalyzer, preview)

    }, ContextCompat.getMainExecutor(requireContext()))
}

private fun allPermissionsGranted() = CameraPicture.REQUIRED_PERMISSIONS.all {
    ContextCompat.checkSelfPermission(
        requireContext(), it) == PackageManager.PERMISSION_GRANTED
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
}
