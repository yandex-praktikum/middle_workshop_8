package yandex.practicum.middle.android.data

import javax.inject.Inject

data class Item(val name: String, val url: String)

class MediaRepository @Inject constructor() {
    private val _urls = listOf( // https://gist.github.com/Fazzani/8f89546e188f8086a46073dc5d4e2928
        "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8",
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    )

    val items = _urls.map { url ->
        Item(name = url.substring(url.indexOfLast { it == '/' } + 1), url = url)
    }
}