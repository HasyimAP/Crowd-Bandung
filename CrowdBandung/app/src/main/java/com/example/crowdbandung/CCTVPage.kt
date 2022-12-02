package com.example.crowdbandung


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.VideoView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.crowdbandung.databinding.ActivityCctvPageBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.PlayerControlView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

class CctvPage : AppCompatActivity() {

    private lateinit var root: ConstraintLayout
    private lateinit var exoPlayerView: PlayerControlView

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSource: MediaSource

    private lateinit var urlType: URLType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cctv_page)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        findView()
        initPlayer()

        supportActionBar?.hide()

        val buttonClick = findViewById<ImageButton>(R.id.backButton)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun findView() {
        root = findViewById(R.id.root)
        exoPlayerView = findViewById(R.id.videoView)
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()

        exoPlayerView.player = exoPlayer

        createMediaSource()

        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
    }

    private fun createMediaSource() {
        urlType = URLType.HLS
        urlType.url = "https://bharadwajpro.github.io/m3u8-player/player/#https://pelindung.bandung.go.id:3443/video/DPKP3/tamanmusikempat.m3u8"

        exoPlayer.seekTo(0)

        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)

        mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(Uri.parse(urlType.url))
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}

enum class URLType(var url: String) {
    HLS(""), MP4("")
}