package com.example.crowdbandung


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.SilenceMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import kotlin.math.pow

class CctvPage : AppCompatActivity() {

    private lateinit var root: ConstraintLayout
    private lateinit var videoView: StyledPlayerView

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var mediaItem: MediaItem
    private lateinit var silenceMediaSource: SilenceMediaSource
    private lateinit var mergingMediaSource: MergingMediaSource
    private lateinit var concatenatingMediaSource: ConcatenatingMediaSource

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
            finish()
        }

        val title : TextView = findViewById(R.id.title)
        title.text = intent.getStringExtra("title")

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true){
                override fun handleOnBackPressed() {
                    startActivity(Intent(this@CctvPage, MainActivity::class.java))
                    finish()
                }
            })
    }

    private fun findView() {
        root = findViewById(R.id.root)
        videoView = findViewById(R.id.videoView)
    }

    private fun initPlayer() {
        exoPlayer = ExoPlayer.Builder(this).build()
        exoPlayer.addListener(playerListener)

        videoView.player = exoPlayer

        createMediaSource()

        exoPlayer.setMediaSource(mediaSource)
        exoPlayer.prepare()
    }

    private fun createMediaSource() {
        urlType = URLType.HLS
//        urlType.url = "https://pelindung.bandung.go.id:3443/video/DPKP3/tamanmusikempat.m3u8"
//        urlType.url = "https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8"
//        urlType.url = "http://45.118.114.26/camera/GedebageTimur.m3u8"
//        urlType.url = "http://103.17.183.107:8080/a3bfe9d130f7a8830cb78d8b1885de6d/hls/dishub/ChVTuxTE9X/s.m3u8"
//        urlType.url = "http://atcs-dishub.bandung.go.id/camera/Buahbatu.m3u8"
        urlType.url = intent.getStringExtra("link").toString()

        exoPlayer.seekTo(0)

        val dataSourceFactory: DataSource.Factory = DefaultDataSource.Factory(this)

        mediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(
            MediaItem.fromUri(Uri.parse(urlType.url))
        )

//        mediaItem = MediaItem.fromUri(urlType.url)

        silenceMediaSource = SilenceMediaSource(12*(10.0.pow(7)).toLong())

        mergingMediaSource = MergingMediaSource(mediaSource, silenceMediaSource)

        concatenatingMediaSource = ConcatenatingMediaSource(mediaSource, silenceMediaSource)
    }

    override fun onResume() {
        super.onResume()

        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()

        exoPlayer.removeListener(playerListener)
        exoPlayer.stop()
        exoPlayer.clearMediaItems()

        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    private var playerListener = object : Player.Listener {
        override fun onRenderedFirstFrame() {
            super.onRenderedFirstFrame()

            videoView.useController = true
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)

            Toast.makeText(this@CctvPage, "${error.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

enum class URLType(var url: String) {
    HLS("")
}