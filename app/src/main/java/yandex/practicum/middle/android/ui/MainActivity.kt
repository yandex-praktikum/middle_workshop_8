package yandex.practicum.middle.android.ui


import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.concurrent.futures.await
import androidx.lifecycle.lifecycleScope
import androidx.media3.session.MediaBrowser
import androidx.media3.session.SessionToken
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var client = mutableStateOf<MediaBrowser?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val player = client.value
                if (player != null)
                    PlayerAppScreen(player)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        lifecycleScope.launch {
            client.value = MediaBrowser.Builder(this@MainActivity, sessionToken).buildAsync().await()
        }
    }

    override fun onStop() {
        super.onStop()

        client.value?.release()
        client.value = null
    }
}

