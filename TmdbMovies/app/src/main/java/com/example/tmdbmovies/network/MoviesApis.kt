package com.example.tmdbmovies.network

import com.example.tmdbmovies.model.MoviesDetailResponse
import com.example.tmdbmovies.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApis {

    @GET("/3/discover/movie")
    fun getMoviesList(
        @Query("api_key") api_key: String,
        @Query("language")
        language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: Boolean,
        @Query("include_video") include_video: Boolean,
        @Query("page") page: Int
    ): Call<MoviesResponse>

    //https://api.themoviedb.org/3/trending/all/day?api_key=5256b8b85017814410c26e90a77ce54d
    @GET("/3/trending/all/day")
    fun trendingMovies(
        @Query("api_key") api_key: String
    ): Call<MoviesResponse>

    //https://api.themoviedb.org/3/movie/703771?api_key=5256b8b85017814410c26e90a77ce54d&language=en-US&append_to_response=videos%2Cimages
    @GET("/3/movie/{movie_id}")
    fun getMoviesDetail(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("append_to_response") append_to_response: String
    ):Call<MoviesDetailResponse>


}


