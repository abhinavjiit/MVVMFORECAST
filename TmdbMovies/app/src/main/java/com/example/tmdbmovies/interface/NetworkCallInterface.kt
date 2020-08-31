package com.example.tmdbmovies.`interface`

interface NetworkCallInterface {
    fun onSuccess(success: String)
    fun onFailure(fail: String)
}