package yandex.practicum.middle.android.ui

import androidx.compose.runtime.Composable
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaBrowser
import androidx.navigation.NavHostController

@Composable
fun ItemScreen(browser: MediaBrowser, navController: NavHostController, item: MediaItem) {
    PlayerScreen(browser, item)
}