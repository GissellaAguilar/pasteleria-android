package com.example.paseleriamilsabores.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.paseleriamilsabores.R

@Composable
fun CarouselSection() {
    val images = listOf(
        R.drawable.torta_chocolate,
        R.drawable.torta_vainilla_circular,
        R.drawable.torta_fruta
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(images) { image ->
            Card(
                modifier = Modifier
                    .width(280.dp)
                    .height(160.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.elevatedCardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
