@file:OptIn(ExperimentalPermissionsApi::class)

package com.valify.registrationsdk.presentation.ui.captureImage

import android.graphics.Bitmap
import android.graphics.Color
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.valify.registrationsdk.R
import com.valify.registrationsdk.domain.utils.AppError
import com.valify.registrationsdk.presentation.theme.dimens
import com.valify.registrationsdk.presentation.ui.captureImage.viewmodel.CaptureIntent
import com.valify.registrationsdk.presentation.ui.captureImage.viewmodel.CaptureViewModel
import com.valify.registrationsdk.utils.FaceDetectSmile
import java.util.concurrent.Executor


@Composable
internal fun CaptureImageScreen(
    viewModel: CaptureViewModel = hiltViewModel(),
    onCaptureComplete: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(state.appFailure) {
        snackbarHostState.currentSnackbarData?.dismiss()
        state.appFailure?.let {
            val message = when (it) {
                is AppError.DatabaseError -> context.getString(R.string.database_error_msg)
                else -> context.getString(R.string.something_went_wrong_msg)
            }
            snackbarHostState.showSnackbar(message)
        }
    }

    if (state.success) {
        onCaptureComplete()
    }

    Scaffold(snackbarHost = {
        SnackbarHost(snackbarHostState)
    }) {
        if (cameraPermission.status.isGranted) {
            CameraContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) { image ->
                viewModel.sendIntent(CaptureIntent.SaveCaptureImageIntent(image))
            }
        } else {
            NoPermissionContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                cameraPermission.launchPermissionRequest()
            }
        }
    }
}

@Composable
private fun CameraContent(modifier: Modifier = Modifier, onImageCaptured: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val previewView = remember {
        PreviewView(context).apply {
            setBackgroundColor(Color.BLACK)
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_START
        }
    }
    val executor = remember { ContextCompat.getMainExecutor(context) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val imageCapture = remember { ImageCapture.Builder().build() }

    DisposableEffect(lifecycleOwner) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build().also {
                it.surfaceProvider = previewView.surfaceProvider
            }
            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_FRONT_CAMERA, preview, imageCapture, smileDetection(executor) {
                    captureImage(executor, imageCapture) {
                        onImageCaptured(it)
                    }
                })
            } catch (error: Exception) {
                error.printStackTrace()
            }
        }, executor)

        onDispose {
            try {
                cameraProviderFuture.get().unbindAll()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    Box(
        modifier = modifier
    ) {
        AndroidView(modifier = Modifier.fillMaxSize(), factory = {
            previewView
        })
    }

}

@Composable
private fun NoPermissionContent(modifier: Modifier = Modifier, onRequestPermission: () -> Unit) {
    Column(
        modifier = modifier.padding(MaterialTheme.dimens.paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(R.string.camera_permission_msg), style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.paddingMedium))
        Button(onClick = onRequestPermission) {
            Icon(imageVector = Icons.Default.Camera, contentDescription = null)
            Text(text = stringResource(R.string.grant_permission_label))
        }
    }
}

private fun captureImage(
    executor: Executor,
    imageCapture: ImageCapture,
    onImageCaptured: (Bitmap) -> Unit,
) {
    imageCapture.takePicture(executor, object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            onImageCaptured(image.toBitmap())
            image.close()
        }
    })
}


private fun smileDetection(
    executor: Executor,
    onSmileDetect: () -> Unit,
): ImageAnalysis {
    return ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build().also {
        it.setAnalyzer(executor, FaceDetectSmile(onSmileDetect))
    }
}

@Preview(device = "id:small_phone", showBackground = true, backgroundColor = 0xFFFFFFFF)
@PreviewScreenSizes
@Composable
private fun PreviewNoPermissionContent() {
    NoPermissionContent(modifier = Modifier.fillMaxSize(), onRequestPermission = {})
}
