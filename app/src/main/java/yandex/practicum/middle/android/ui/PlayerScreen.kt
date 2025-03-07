package yandex.practicum.middle.android.ui

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat.getSystemService
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.ui.PlayerView
import kotlinx.coroutines.launch

@Composable
fun PlayerScreen(player: Player, url: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val mediaItem = remember(url) {
        MediaItem.Builder().setUri(url).build()
    }

    LaunchedEffect(mediaItem) {
        coroutineScope.launch {
            setupAudioFocus(context, player)
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
                PlayerView(ctx).apply {
                    this.player = player
                }
            }
        )
    }
}

private fun setupAudioFocus(context: Context, player: Player) {
    val audioManager = getSystemService(context, AudioManager::class.java)!!

    val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
        .setOnAudioFocusChangeListener { focusChange ->
            when (focusChange) {
                AudioManager.AUDIOFOCUS_LOSS -> player.stop()
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> player.pause()
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> player.volume = 0.2f
                AudioManager.AUDIOFOCUS_GAIN -> {
                    player.play()
                    player.volume = 1f
                }
            }
        }
        .build()

    player.addListener(object : Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            if (playbackState == Player.STATE_ENDED)
                audioManager.abandonAudioFocusRequest(focusRequest)
        }
    })
    audioManager.requestAudioFocus(focusRequest)
}