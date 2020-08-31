package com.example.tmdbmovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbmovies.errorlog.Resource
import com.example.tmdbmovies.model.MovieListData
import com.example.tmdbmovies.repo.MoviesListingRepo

class MoviesListViewModel() : ViewModel() {

    var moviesListData: MutableLiveData<Resource<ArrayList<MovieListData>>>
    var trendingMovies: MutableLiveData<ArrayList<MovieListData>>
    var toastMsg: MutableLiveData<String>? = null


    init {
        moviesListData = MoviesListingRepo.getMovies()
        trendingMovies = MoviesListingRepo.getTrendingMovies()
    }

    fun setMoviesIntoUi(): LiveData<Resource<ArrayList<MovieListData>>> {
        return moviesListData
    }

    fun setTrendingintoUi(): LiveData<ArrayList<MovieListData>> {
        return trendingMovies
    }
/*
    override fun onSuccess(success: String) {
        toastMsg?.value = success

    }

    override fun onFailuer(failed: String) {
        toastMsg?.value = failed

    }*/


}