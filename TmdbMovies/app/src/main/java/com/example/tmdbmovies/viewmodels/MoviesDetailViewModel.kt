package com.example.tmdbmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbmovies.errorlog.Resource
import com.example.tmdbmovies.model.MoviesDetailResponse
import com.example.tmdbmovies.repo.MoviesDetailRepo

class MoviesDetailViewModel : ViewModel() {
    var movieDetail: MutableLiveData<Resource<MoviesDetailResponse>>? = null


    fun init(movie_id: Int) {
        movieDetail = MoviesDetailRepo.getMovieDetail(movie_id)
    }


    fun getMovieDetail(): LiveData<Resource<MoviesDetailResponse>>? {
        movieDetail?.let {
            return it
        } ?: kotlin.run {
            return null
        }
    }

}