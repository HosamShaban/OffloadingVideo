@file:Suppress("DEPRECATION")

package com.example.offloadingvideo_streaming

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import kotlinx.android.synthetic.main.activity_main.*


 @Suppress("DEPRECATION")
 class MainActivity : AppCompatActivity() {

    lateinit var player: ExoPlayer
    var videoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    var playWhenReady = true
     var currentWindow = 0
     var playBackPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

     fun initVideo(){
         player = ExoPlayer.Builder(this).build()
         PlayerView.player = player
         val uri = Uri.parse(videoUrl)
         val mediaItem = MediaItem.fromUri(uri)
         val dataSourceFactory = DefaultDataSourceFactory(this, "exoplayer-codelab")
         val media = ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(mediaItem)


         player.playWhenReady = playWhenReady
         player.seekTo(currentWindow , playBackPosition.toLong())
         player.prepare(media , false , false)

     }

     fun releaseVideo(){
         if (player != null){
             playWhenReady = player.playWhenReady
             playBackPosition = player.currentPosition.toInt()
             currentWindow = player.currentWindowIndex
             player.release()
         }
     }

     override fun onStart() {
         super.onStart()
         initVideo()
     }

     override fun onResume() {
         super.onResume()
         if (player != null){
             initVideo()
         }
     }

     override fun onPause() {
         super.onPause()
         releaseVideo()
     }

     override fun onStop() {
         super.onStop()
         releaseVideo()
     }

     }
