package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.SalonAssetProvider
import com.example.ui.components.KimeraWordmark
import com.example.ui.theme.BorderHairline
import com.example.ui.theme.Charcoal
import com.example.ui.theme.DeepCoral
import com.example.ui.theme.KimeraCoral
import com.example.ui.theme.PeachTint
import com.example.ui.theme.RenderWhite
import com.example.ui.theme.WarmCream
import com.example.ui.theme.WarmGrey

@Composable
fun ReferenceCaptureScreen(
    clientName: String,
    initialReferenceStyle: String,
    initialFaceShape: String,
    initialHairTexture: String,
    initialDesiredFade: String,
    onGenerate: (
        refName: String,
        faceShape: String,
        hairTexture: String,
        desiredFade: String,
        clientPhotoUri: String?,
        refPhotoUri: String?
    ) -> Unit,
    onBack: () -> Unit
) {
    var customClientPhotoUri by remember { mutableStateOf<Uri?>(null) }
    var selectedStyleName by remember { mutableStateOf("Textured French Crop") }

    val clientPhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            customClientPhotoUri = uri
        }
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Charcoal
                        )
                    }
                    KimeraWordmark(fontSize = 24)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Capture",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Charcoal
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(PeachTint)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Step 2 of 4",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = DeepCoral
                        )
                    }
                }
            }
        },
        containerColor = WarmCream
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 6.dp)
        ) {
            // Step Progress Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(DeepCoral)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(KimeraCoral)
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFFE2D6CF))
                        )
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp))
                                .background(Color(0xFFE2D6CF))
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "✔ 1 Consent", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = DeepCoral)
                        Text(text = "● 2 Capture", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DeepCoral)
                        Text(text = "● 3 Previews", fontSize = 10.sp, color = WarmGrey)
                        Text(text = "● 4 Cut", fontSize = 10.sp, color = WarmGrey)
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Serif Title
            Text(
                text = "Take Photo & Pick Style",
                fontSize = 30.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Charcoal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Snap a quick chair photo and choose your target look.",
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = WarmGrey
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Card 1: Client Photo
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("client_photo_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(DeepCoral)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Client Photo",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Charcoal
                            )
                        }

                        Text(
                            text = clientName,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Charcoal
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Client Photo Image container
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.2f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFEFE8E2))
                    ) {
                        if (customClientPhotoUri != null) {
                            AsyncImage(
                                model = customClientPhotoUri,
                                contentDescription = "Client Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Image(
                                painter = painterResource(id = SalonAssetProvider.rohanClientPhotoResId),
                                contentDescription = "Rohan V Client Photo",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // Retake pill button on bottom right of photo
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(9999.dp))
                                .background(RenderWhite)
                                .clickable {
                                    clientPhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                }
                                .padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = DeepCoral,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Retake",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Charcoal
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Card 2: Target Style
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("target_style_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F4EE)),
                border = BorderStroke(1.dp, BorderHairline)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(DeepCoral)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Target Style",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Charcoal
                            )
                        }

                        Text(
                            text = "Change",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = DeepCoral,
                            modifier = Modifier.clickable {
                                selectedStyleName = if (selectedStyleName == "Textured French Crop") "Modern Drop Taper" else "Textured French Crop"
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = RenderWhite)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            ) {
                                Image(
                                    painter = painterResource(id = SalonAssetProvider.defaultReferenceDrawableId),
                                    contentDescription = "Textured French Crop",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = selectedStyleName,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Charcoal
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = "Modern taper fade · Low maintenance",
                                    fontSize = 11.sp,
                                    color = WarmGrey
                                )
                            }

                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = DeepCoral,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Primary Dark Terracotta Pill Button: GENERATE 3 PREVIEWS
            Button(
                onClick = {
                    onGenerate(
                        selectedStyleName,
                        initialFaceShape,
                        initialHairTexture,
                        initialDesiredFade,
                        customClientPhotoUri?.toString(),
                        null
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("generate_previews_button"),
                shape = RoundedCornerShape(9999.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DeepCoral,
                    contentColor = RenderWhite
                )
            ) {
                Text(
                    text = "GENERATE 3 PREVIEWS",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = WarmGrey,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Takes under 60 seconds",
                    fontSize = 12.sp,
                    color = WarmGrey
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
