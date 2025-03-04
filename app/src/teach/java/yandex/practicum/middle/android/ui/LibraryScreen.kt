package yandex.practicum.middle.android.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.concurrent.futures.await
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaBrowser
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun ItemScreen(browser: MediaBrowser, navController: NavHostController, item: MediaItem) {
    PlayerScreen(browser, item)
}

@Composable
fun LibraryScreen(browser: MediaBrowser, navController: NavHostController) {
    val items by remember {
        mutableStateOf(
            listOf(
                MediaItem.Builder().setMediaId("BigBuckBunny.mp4")
                    .setUri("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                    .build()
            )
        )
    }

    ItemsList(items, navController)
}
