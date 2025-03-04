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
fun LibraryScreen(browser: MediaBrowser, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    var items by remember { mutableStateOf<List<MediaItem>>(listOf()) }

    LaunchedEffect(browser) {
        coroutineScope.launch {
            val rootResult = browser.getLibraryRoot(null).await()
            items = listOf(rootResult.value!!)
        }
    }

    ItemsList(items, navController)
}
