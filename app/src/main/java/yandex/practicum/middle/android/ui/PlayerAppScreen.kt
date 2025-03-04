package yandex.practicum.middle.android.ui

import androidx.annotation.OptIn
import androidx.compose.runtime.Composable
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.session.MediaBrowser
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@OptIn(UnstableApi::class)
@Composable
fun PlayerAppScreen(browser: MediaBrowser) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.Root.name) {
        composable(route = Destinations.Item.name) {
            ItemScreen(browser, navController, MediaItem.fromBundle(it.arguments!!))
        }
        composable(route = Destinations.Root.name) {
            LibraryScreen(browser, navController)
        }
    }
}

@Serializable
enum class Destinations { Root, Item }