package com.pokeberry.app.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CatchingPokemon
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Yard
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pokeberry.app.domain.model.Berry

@Composable
fun BerryItem(
    berry: Berry,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val icons = remember {
        listOf(
            Icons.Default.CatchingPokemon,
            Icons.Default.Eco,
            Icons.Default.LocalFlorist,
            Icons.Default.Spa,
            Icons.Default.Yard
        )
    }

    val colors = remember {
        listOf(
            Color(0xFFE57373), // Red
            Color(0xFF81C784), // Green
            Color(0xFF64B5F6), // Blue
            Color(0xFFFFB74D), // Orange
            Color(0xFFBA68C8)  // Purple
        )
    }

    val randomIcon = remember(berry.id) { icons.random() }
    val randomColor = remember(berry.id) { colors.random() }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                imageVector = randomIcon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = randomColor
            )

            Text(
                text = berry.name.replaceFirstChar {
                    it.uppercase()
                },
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )

            Text(
                text = "ID: ${berry.id}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}