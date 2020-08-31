package com.example.tmdbmovies.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.tmdbmovies.R
import com.example.tmdbmovies.adapter.MoviesAdapter
import com.example.tmdbmovies.errorlog.Resource
import com.example.tmdbmovies.model.MovieListData
import com.example.tmdbmovies.viewmodels.MoviesListViewModel

class MoviesListActivity : AppCompatActivity(), MoviesAdapter.RecyclerViewClick {

    @BindView(R.id.recyclerView)
    lateinit var recyclerView: RecyclerView

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    @BindView(R.id.recyclerView_trending)
    lateinit var recyclerView_trending: RecyclerView
    lateinit var moviesListViewModel: MoviesListViewModel
    private val adapter: MoviesAdapter by lazy {
        MoviesAdapter(this)
    }
    private val adapterr: MoviesAdapter by lazy {
        MoviesAdapter(this)
    }
    var moviesList: ArrayList<MovieListData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView_trending.layoutManager =
            LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = adapter
        recyclerView_trending.adapter = adapterr
        adapter.setListData(moviesList)
        adapterr.setListData(moviesList)
        moviesListViewModel = ViewModelProvider(this).get(MoviesListViewModel::class.java)
        moviesListViewModel.setMoviesIntoUi().observe(this, Observer { moviesList ->
            when (moviesList.status) {
                Resource.Status.SUCCESS -> {
                    Toast.makeText(this, "fetched data", Toast.LENGTH_SHORT).show()
                    adapter.setListData(moviesList.data)
                    adapter.notifyDataSetChanged()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, moviesList.message, Toast.LENGTH_SHORT).show()

                }
                else -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }
            }
        }
        )

        moviesListViewModel.setTrendingintoUi().observe(this, Observer {
            adapterr.setListData(it)
            adapterr.notifyDataSetChanged()
        })

        moviesListViewModel.toastMsg?.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onclick(id: Int) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }
}
