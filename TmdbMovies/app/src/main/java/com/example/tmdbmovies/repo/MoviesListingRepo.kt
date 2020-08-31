package com.example.tmdbmovies.repo

import androidx.lifecycle.MutableLiveData
import com.example.tmdbmovies.BaseApplication
import com.example.tmdbmovies.Constants
import com.example.tmdbmovies.errorlog.Resource
import com.example.tmdbmovies.model.MovieListData
import com.example.tmdbmovies.model.MoviesResponse
import com.example.tmdbmovies.network.MoviesApis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MoviesListingRepo {


    fun getMovies(): MutableLiveData<Resource<ArrayList<MovieListData>>> {
        val movies = MutableLiveData<Resource<ArrayList<MovieListData>>>()
        BaseApplication.getInstance().getRetrofitt().create(MoviesApis::class.java)
            .getMoviesList(
                Constants.API_KEY,
                "en-US",
                "popularity.desc",
                include_adult = false,
                include_video = false,
                page = 1
            ).enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    movies.value = Resource.error(t.message!!, null)
                }

                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    val res = response.body()
                    res?.results?.let {
                        movies.value = Resource.success(it)
                    } ?: run {
                        movies.value = Resource.error("something went wrong", null)
                    }
                }
            })
        return movies
    }

    fun getTrendingMovies(): MutableLiveData<ArrayList<MovieListData>> {
        val movies = MutableLiveData<ArrayList<MovieListData>>()
        BaseApplication.getInstance().getRetrofitt().create(MoviesApis::class.java)
            .trendingMovies(
                Constants.API_KEY
            ).enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    movies.value = null
                }

                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    val res = response.body()
                    res?.results?.let {
                        movies.value = it
                    }
                }
            })
        return movies
    }
}