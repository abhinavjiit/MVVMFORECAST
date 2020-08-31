package com.example.tmdbmovies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tmdbmovies.R
import com.example.tmdbmovies.errorlog.Resource
import com.example.tmdbmovies.viewmodels.MoviesDetailViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var moviesDetailViewModel: MoviesDetailViewModel
    private lateinit var videoPlay: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movies_detail_fragment)
        videoPlay = findViewById(R.id.videoPlay)
        val id = intent.getIntExtra("id", 0)
        lifecycle.addObserver(videoPlay)
        moviesDetailViewModel = ViewModelProvider(this).get(MoviesDetailViewModel::class.java)
        moviesDetailViewModel.init(id)
        moviesDetailViewModel.getMovieDetail()?.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {

                    videoPlay.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            // youTubePlayer.loadVideo(it.data?.videos?.results?.get(0)?.key!!, 0f)
                            youTubePlayer.loadOrCueVideo(
                                lifecycle,
                                it.data?.videos?.results?.get(0)?.key!!,
                                0f
                            )
                        }
                    })
                }
                Resource.Status.ERROR -> {

                }
                else -> {
                }
            }

        })


    }

    override fun onPause() {
        videoPlay.release()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}