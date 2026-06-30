package com.pokeberry.app.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pokeberry.app.presentation.component.BerryItem
import com.pokeberry.app.presentation.viewmodel.BerryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerryScreen(
    onBerryClick: (Int) -> Unit,
    viewModel: BerryViewModel = viewModel()
) {

    val berries by viewModel.berries.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val lastRequestUrl by
    viewModel.lastRequestUrl.collectAsState()

    val lastResponse by
    viewModel.lastResponse.collectAsState()

    val listState = rememberLazyListState()

    var showDebugDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(listState) {

        snapshotFlow {

            listState.layoutInfo
                .visibleItemsInfo
                .lastOrNull()
                ?.index

        }.collect { lastVisibleIndex ->

            if (lastVisibleIndex != null) {
                if (
                    lastVisibleIndex >= berries.lastIndex - 3
                    && !isLoading
                ) {

                    viewModel.fetchBerryList()
                }
            }
        }
    }

    Scaffold(

        topBar = {

            TopAppBar(

                title = {
                    Text("Berries 🍓")
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
                }
            )
        }

    ) { paddingValues ->

        PullToRefreshBox(

            isRefreshing = isLoading,

            onRefresh = {
                viewModel.refreshBerryList()
            },

            content = {

                LazyColumn(
                    state = listState,

                    modifier = Modifier.padding(paddingValues),

                    verticalArrangement = Arrangement.spacedBy(4.dp),

                    contentPadding = PaddingValues(
                        bottom = 16.dp
                    )
                ) {

                    items(berries) { berry ->

                        BerryItem(
                            berry = berry,

                            onClick = {
                                onBerryClick(berry.id)
                            }
                        )
                    }

                    item {

                        if (isLoading) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),

                                contentAlignment = Alignment.Center
                            ) {

                                CircularProgressIndicator(
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }
                }
            }
        )
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
                Text("API Debug 📡")
            },

            text = {

                LazyColumn {

                    item {

                        Text(
                            text =
                                """
REQUEST:
GET $lastRequestUrl


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