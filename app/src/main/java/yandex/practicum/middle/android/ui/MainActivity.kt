package yandex.practicum.middle.android.ui


import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.analytics.PlaybackStats
import androidx.media3.exoplayer.analytics.PlaybackStatsListener
import androidx.media3.ui.PlayerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    val mediaPlayer by lazy { MediaPlayer.create(this, R.raw.audio) }

    val player by lazy { ExoPlayer.Builder(this).build() }
    val mediaPlayer = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
//    mediaPlayer.start()
//    mediaPlayer.start()
//    val player = ExoPlayer.Builder(this).build()
        val url =
            "https://promodj.com/download/7614020/THE%20MATRIX%20-%20Welcome%20To%20The%20Future%20%28Extended%20mix%29%20%28promodj.com%29.mp3"
//        mediaPlayer.setDataSource(url)
//        mediaPlayer.prepare()
//        mediaPlayer.start()
        val videoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val mediaItem =
//            MediaItem.fromUri(videoUrl)
//        val mediaItem = MediaItem.fromUri(Uri.parse("asset:/audio.mp3"))
//        player.setMediaItem(mediaItem)
//        player.prepare()
//        player.play()
//player.play()
//    mediaPlayer.setV
//        val focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).build()
//        audioManager.requestAudioFocus(focusRequest)
////        mediaPlayer.setVolume(1, 1)
//        val r = audioManager.getStreamMinVolume(AudioManager.STREAM_RING)
//        audioManager.setStreamVolume(
//            AudioManager.STREAM_RING,
//            audioManager.getStreamMaxVolume(AudioManager.STREAM_RING),
//            0
//        )
//        val v1 = audioManager.getStreamVolume(AudioManager.STREAM_RING)
//        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        if (notificationManager.isNotificationPolicyAccessGranted) {
//            audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
//            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
//            val v2 = audioManager.getStreamVolume(AudioManager.STREAM_RING)
//            v2.toString()
//        } else {
//            startActivity(
//                Intent(
//                    Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS
//                )
//            )
//        }
////        mediaPlayer.setDataSource(assets.openFd("audio.mp3").fileDescriptor)
//        mediaPlayer.setDataSource("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
//        mediaPlayer.prepare()
//        mediaPlayer.setOnPreparedListener { }
            setContent {
                MaterialTheme {
                    Player("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8")
                    //                        createSurfaceView(ctx) {
                    //                            mediaPlayer.setDisplay(it)
                    //                            mediaPlayer.start()
                    //                        }
                }
            }
    }

}

@Composable
@UnstableApi
fun Player(videoUrl: String) {
    val context = LocalContext.current
    val exoPlayer = remember { ExoPlayer.Builder(context).build() }
    val playbackStatsListener = PlaybackStatsListener(true) { eventTime: EventTime, playbackStats: PlaybackStats, ->
        val totalPlayTimeMs = playbackStats.totalPlayTimeMs
        playbackStats.totalPausedTimeMs
        ...
    }
    playbackStatsListener.
    exoPlayer.addAnalyticsListener(
        playbackStatsListener
    )

    exoPlayer.addAnalyticsListener(object : AnalyticsListener {
        override fun onPlaybackStateChanged(eventTime: AnalyticsListener.EventTime, @Player.State state: Int) {
            ...
        }

        override fun onDroppedVideoFrames(eventTime: AnalyticsListener.EventTime, droppedFrames: Int, elapsedMs: Long) {
            ...
        }
    })
    val coroutineScope = rememberCoroutineScope()
    val mediaItem = remember(videoUrl) {
        MediaItem.Builder().setUri(videoUrl).setMimeType(MimeTypes.APPLICATION_M3U8).build()
    }


    LaunchedEffect(mediaItem) {
        coroutineScope.launch {
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.playWhenReady = true
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
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
                PlayerView(ctx).apply { this.player = exoPlayer }
            }
        )
    }
}

val dashMediaItem = MediaItem.Builder()
    .setUri("https://example.com/video/stream")
    .setMimeType(MimeTypes.APPLICATION_MPD)
    .build()

val hlsMediaItem = MediaItem.Builder()
    .setUri("https://example.com/video/playlist")
    .setMimeType(MimeTypes.APPLICATION_M3U8)
    .build()

private fun createSurfaceView(ctx: Context, onSurfaceCreated: (SurfaceHolder) -> Unit): SurfaceView {
    val surfaceView = SurfaceView(ctx)
    surfaceView.holder.addCallback(object : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) = onSurfaceCreated(holder)

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        }

        override fun surfaceDestroyed(p0: SurfaceHolder) {
        }
    })
    return surfaceView
}
