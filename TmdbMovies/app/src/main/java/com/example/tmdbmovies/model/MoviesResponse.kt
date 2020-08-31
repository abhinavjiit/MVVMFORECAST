package com.example.tmdbmovies.model

class MoviesResponse(
    var page: String? = null,
    var total_results: String? = null,
    var total_pages: String? = null,
    var results: ArrayList<MovieListData>? = null
)
