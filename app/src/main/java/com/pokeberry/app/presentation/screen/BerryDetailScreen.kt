package com.pokeberry.app.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.pokeberry.app.presentation.viewmodel.BerryDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerryDetailScreen(
    berryId: Int,
    onBackClick: () -> Unit,
    viewModel: BerryDetailViewModel = viewModel()
) {

    val berry by viewModel.berry.collectAsState()

    val lastResponse by viewModel.lastResponse.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    var showDebugDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.fetchBerryDetail(berryId)
    }

    Scaffold(

        topBar = {

            CenterAlignedTopAppBar(

                title = {
                    Text("Berry Detail")
                },

                navigationIcon = {

                    IconButton(
                        onClick = onBackClick
                    ) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },

                actions = {

                    IconButton(

                        onClick = {
                            showDebugDialog = true
                        }

                    ) {

                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = "Debug"
                        )
                    }
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }

    ) { paddingValues ->

        if (isLoading) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),

                contentAlignment = Alignment.Center
            ) {

                CircularProgressIndicator()
            }

        } else {

            berry?.let { data ->

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),

                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    item {

                        Card(
                            modifier = Modifier.fillMaxWidth(),

                            shape = RoundedCornerShape(28.dp),

                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 8.dp
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(24.dp),

                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                val itemSpriteUrl by viewModel.itemSpriteUrl.collectAsState()

                                AsyncImage(
                                    model = itemSpriteUrl,
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = data.name.uppercase(),

                                    style = MaterialTheme.typography.headlineMedium,

                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = "Berry ID #${data.id}",

                                    style = MaterialTheme.typography.bodyLarge,

                                    color = MaterialTheme.colorScheme.primary
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                HorizontalDivider()

                                Spacer(modifier = Modifier.height(20.dp))

                                BerryInfoCard(
                                    icon = Icons.Default.Eco,
                                    title = "Growth Time",
                                    value = "${data.growthTime}"
                                )
                                BerryInfoCard(
                                    icon = Icons.Default.Spa,
                                    title = "Max Harvest",
                                    value = "${data.maxHarvest}"
                                )
                                BerryInfoCard(
                                    icon = Icons.Default.Eco,
                                    title = "Size",
                                    value = "${data.size}"
                                )

                                BerryInfoCard(
                                    icon = Icons.Default.Spa,
                                    title = "Smoothness",
                                    value = "${data.smoothness}"
                                )

                                BerryInfoCard(
                                    icon = Icons.Default.LocalFlorist,
                                    title = "Soil Dryness",
                                    value = "${data.soilDryness}"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDebugDialog) {

        AlertDialog(

            onDismissRequest = {
                showDebugDialog = false
            },

            confirmButton = {

                TextButton(

                    onClick = {
                        showDebugDialog = false
                    }

                ) {

                    Text("Close")
                }
            },

            title = {
                Text("Detail API Debug 📡")
            },

            text = {

                LazyColumn {

                    item {

                        Text(
                            text =
                                """
REQUEST:
GET https://pokeapi.co/api/v2/berry/$berryId


RESPONSE:
$lastResponse
                            """.trimIndent(),

                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun BerryInfoCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    value: String
) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),

        shape = RoundedCornerShape(18.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer
                    ),

                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Column {

                Text(
                    text = title,

                    style = MaterialTheme.typography.bodyMedium,

                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = value,

                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}