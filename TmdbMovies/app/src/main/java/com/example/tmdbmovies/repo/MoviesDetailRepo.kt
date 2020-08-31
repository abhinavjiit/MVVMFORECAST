package com.example.tmdbmovies.repo

import androidx.lifecycle.MutableLiveData
import com.example.tmdbmovies.BaseApplication
import com.example.tmdbmovies.Constants
import com.example.tmdbmovies.errorlog.Resource
import com.example.tmdbmovies.model.MoviesDetailResponse
import com.example.tmdbmovies.network.MoviesApis
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object MoviesDetailRepo {

    fun getMovieDetail(moviesId: Int): MutableLiveData<Resource<MoviesDetailResponse>> {
        val data = MutableLiveData<Resource<MoviesDetailResponse>>()
        val ret = BaseApplication.getInstance().retrofit
        val a = ret.create(MoviesApis::class.java)
        val call = a.getMoviesDetail(
            moviesId,
            Constants.API_KEY, "en-US", "videos,images"
        )
        call.enqueue(object : Callback<MoviesDetailResponse> {
            override fun onFailure(call: Call<MoviesDetailResponse>, t: Throwable) {
                data.value = Resource.error(t.message!!, null)
            }

            override fun onResponse(
                call: Call<MoviesDetailResponse>,
                response: Response<MoviesDetailResponse>
            ) {
                if (response.isSuccessful) {
                    data.value = Resource.success(response.body())
                }
            }

        })
        return data
    }
}