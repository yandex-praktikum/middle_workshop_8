package yandex.practicum.middle.android.ui


import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.concurrent.futures.await
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var mediaController = mutableStateOf<MediaController?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = "asset:/audio.mp3"
        setContent {
            MaterialTheme {
                val player = mediaController.value
                if (player != null)
                    Player(player, url)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        lifecycleScope.launch {
            mediaController.value = MediaController.Builder(this@MainActivity, sessionToken).buildAsync().await()
        }
    }

    override fun onStop() {
        super.onStop()

        mediaController.value?.release()
        mediaController.value = null
    }
}

@Composable
fun Player(player: Player, url: String) {
    val coroutineScope = rememberCoroutineScope()
    val mediaItem = remember(url) {
        MediaItem.Builder().setUri(url).build()
    }

    LaunchedEffect(mediaItem) {
        coroutineScope.launch {
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            factory = { ctx ->
                PlayerView(ctx).apply { this.player = player }
            }
        )
    }
}
