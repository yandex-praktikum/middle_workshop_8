package yandex.practicum.middle.android.ui

import android.content.Intent
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.LibraryResult
import androidx.media3.session.MediaLibraryService
import androidx.media3.session.MediaSession
import com.google.common.collect.ImmutableList
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import dagger.hilt.android.AndroidEntryPoint
import yandex.practicum.middle.android.data.MediaRepository
import javax.inject.Inject

@AndroidEntryPoint
class PlaybackService : MediaLibraryService() {
    @Inject
    lateinit var repository: MediaRepository

    private var session: MediaLibrarySession? = null

    override fun onCreate() {
        super.onCreate()

        val player = ExoPlayer.Builder(this).build()

        session = MediaLibrarySession.Builder(this, player, object : MediaLibrarySession.Callback {
            override fun onAddMediaItems(
                mediaSession: MediaSession,
                controller: MediaSession.ControllerInfo,
                mediaItems: MutableList<MediaItem>
            ): ListenableFuture<MutableList<MediaItem>> {
                val itemsWithUri = mediaItems.map { mediaItem ->
                    mediaItem.buildUpon().setUri(repository.items.find { it.name == mediaItem.mediaId }?.url).build()
                }
                return Futures.immediateFuture(itemsWithUri.toMutableList())
            }

            override fun onGetLibraryRoot(
                session: MediaLibrarySession,
                browser: MediaSession.ControllerInfo,
                params: LibraryParams?
            ): ListenableFuture<LibraryResult<MediaItem>> {
                val metadata = MediaMetadata.Builder()
                    .setIsBrowsable(true)
                    .setIsPlayable(false)
                    .build()

                val item = MediaItem.Builder()
                    .setMediaId("Видео: ${repository.items.size}")
                    .setMediaMetadata(metadata)
                    .build()
                return Futures.immediateFuture(LibraryResult.ofItem(item, params))
            }

            override fun onGetChildren(
                session: MediaLibrarySession,
                browser: MediaSession.ControllerInfo,
                parentId: String,
                page: Int,
                pageSize: Int,
                params: LibraryParams?
            ): ListenableFuture<LibraryResult<ImmutableList<MediaItem>>> {
                val items = repository.items.map { item ->
                    val metadata = MediaMetadata.Builder()
                        .setIsBrowsable(false)
                        .setIsPlayable(true)
                        .build()

                    MediaItem.Builder()
                        .setUri(item.url)
                        .setMediaMetadata(metadata)
                        .setMediaId(item.name)
                        .build()
                }
                return Futures.immediateFuture(LibraryResult.ofItemList(items, params))
            }
        }).build()
    }

    @UnstableApi
    override fun onTaskRemoved(rootIntent: Intent?) {
        pauseAllPlayersAndStopSelf()
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo) = session

    override fun onDestroy() {
        session?.run {
            player.release()
            release()
            session = null
        }
        super.onDestroy()
    }
}