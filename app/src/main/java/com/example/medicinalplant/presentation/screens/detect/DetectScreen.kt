package com.example.medicinalplant.presentation.screens.detect

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.medicinalplant.BuildConfig
import com.example.medicinalplant.util.createImageFile
import com.example.medicinalplant.R
import com.example.medicinalplant.util.predict
import java.util.Objects

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetectScreen(
    selectedLanguage: Int,
    onApplyClick:(Int) -> Unit,
) {
    var padding by remember { mutableStateOf(PaddingValues()) }
    var predictedindex by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )
    var capturedImageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val cameraLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.TakePicture()) {
            capturedImageUri = uri
        }
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { imageUri ->
        if (imageUri != null) {
            capturedImageUri = imageUri
        }
    }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) {

        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    Scaffold(
        topBar = {
            DetectTopBar(
                selectedLanguage = selectedLanguage,
                onApplyClick = onApplyClick,
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val permissionCheckResult = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA)
                if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uri)
                } else {
                    // Request a Permission
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
                showResult = false
            }) {
                Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Camera Icon")
            }
        },
        content = { values ->
            padding = values
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.welcome)
                )
                Column(
                    modifier = Modifier
                        .height(500.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (capturedImageUri.path?.isNotEmpty() == true) {
                            Image(
                            modifier = Modifier
                                .height(400.dp)
                                .fillMaxWidth(),
                            painter = rememberAsyncImagePainter(capturedImageUri),
                            contentDescription = stringResource(id = R.string.plant_image)
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Button(
                            onClick = {
                                val ind = predict(context = context, uri =  capturedImageUri)
//                                predictedIndex(selectedLanguage, ind)
                                showResult = true
                                Log.d("UPLOAD_SCREEN", ind.toString())
                                predictedindex = ind
                            }) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Icon(
                                    imageVector = Icons.Default.ImageSearch,
                                    contentDescription = "Image Search Icon"
                                )
                                Spacer(modifier = Modifier.padding(4.dp))
                                Text(text = stringResource(id = R.string.detect))
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        if (showResult) {
                            when(predictedindex) {
                                0 -> ShowResult(text = stringResource(id = R.string.acacia_gumefora))
                                1 -> ShowResult(text = stringResource(id = R.string.agave_sisal))
                                2 -> ShowResult(text = stringResource(id = R.string.ajuga))
                                3 -> ShowResult(text =stringResource(id = R.string.allophylus) )
                                4 -> ShowResult(text = stringResource(id = R.string.aloe_ankoberenisis))
                                5 -> ShowResult(text = stringResource(id = R.string.aloe_debrana))
                                6 -> ShowResult(text = stringResource(id = R.string.archirantus))
                                7 -> ShowResult(text = stringResource(id = R.string.bederjan))
                                8 -> ShowResult(text = stringResource(id = R.string.beresemma_abysinica))
                                9 -> ShowResult(text = stringResource(id = R.string.bide))
                                10 -> ShowResult(text = stringResource(id = R.string.calpurnia))
                                11 -> ShowResult(text = stringResource(id = R.string.carissa_spinanrum))
                                12 -> ShowResult(text = stringResource(id = R.string.chaenopodium))
                                13 -> ShowResult(text = stringResource(id = R.string.chlorodundurum))
                                14 -> ShowResult(text = stringResource(id = R.string.climatis))
                                15 -> ShowResult(text = stringResource(id = R.string.clutea))
                                16 -> ShowResult(text = stringResource(id = R.string.cordia))
                                17 -> ShowResult(text = stringResource(id = R.string.crotun))
                                18 -> ShowResult(text = stringResource(id = R.string.dovianus))
                                19 -> ShowResult(text = stringResource(id = R.string.eberighia))
                                20 -> ShowResult(text = stringResource(id = R.string.echinoopes_kebericho))
                                21 -> ShowResult(text = stringResource(id = R.string.ficus_sur))
                                22 -> ShowResult(text = stringResource(id = R.string.hagnia_abbysinica))
                                23 -> ShowResult(text = stringResource(id = R.string.jesminium))
                                24 -> ShowResult(text = stringResource(id = R.string.laggerai))
                                25 -> ShowResult(text = stringResource(id = R.string.leontes))
                                26 -> ShowResult(text = stringResource(id = R.string.leucas))
                                27 -> ShowResult(text = stringResource(id = R.string.linipia_adonesisi))
                                28 -> ShowResult(text = stringResource(id = R.string.lobelia_rehinopetanum))
                                29 -> ShowResult(text = stringResource(id = R.string.melitia))
                                30 -> ShowResult(text = stringResource(id = R.string.messa_lanceolata))
                                31 -> ShowResult(text = stringResource(id = R.string.osirus))
                                32 -> ShowResult(text = stringResource(id = R.string.phutolleca))
                                33 -> ShowResult(text = stringResource(id = R.string.plantago))
                                34 -> ShowResult(text = stringResource(id = R.string.rumes_abbysinica))
                                35 -> ShowResult(text = stringResource(id = R.string.rumix_nervo))
                                36 -> ShowResult(text = stringResource(id = R.string.senecio))
                                37 -> ShowResult(text = stringResource(id = R.string.stephania_abbysinica))
                                38 -> ShowResult(text = stringResource(id = R.string.thymus_schimperia))
                                39 -> ShowResult(text = stringResource(id = R.string.uritica))
                                40 -> ShowResult(text = stringResource(id = R.string.verbasucum))
                                41 -> ShowResult(text = stringResource(id = R.string.vernonia_amag))
                                42 -> ShowResult(text = stringResource(id = R.string.vernonia_leop))
                                else -> ShowResult(text = stringResource(id = R.string.zeneria_scabra))
                            }
                            
                        }
                    }
                }
                Row (
                    modifier = Modifier.fillMaxWidth()
                ){
                    Button(
                        shape = Shapes().small,
                        onClick = {
                            photoPicker.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                            showResult = false
                        }
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Icon(
                                imageVector = Icons.Default.Image,
                                contentDescription = "Gallery Icon"
                            )
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text(text = stringResource(id = R.string.gallery))
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ShowResult(text: String) {
    Text(text = text)
}