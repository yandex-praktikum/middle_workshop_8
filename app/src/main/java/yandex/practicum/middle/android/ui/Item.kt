package yandex.practicum.middle.android.ui

import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavHostController

@OptIn(UnstableApi::class)
@Composable
fun Item(item: MediaItem, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation()
    ) {
        Text(
            modifier = Modifier
                .clickable {
                    val destination = navController.graph.findNode(Destinations.Item.name)!!
                    navController.navigate(destination.id, item.toBundleIncludeLocalConfiguration())
                }
                .fillMaxWidth()
                .padding(16.dp),
            text = item.mediaId,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}