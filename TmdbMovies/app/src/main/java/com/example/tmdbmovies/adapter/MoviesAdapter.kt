package com.example.tmdbmovies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbmovies.R
import com.example.tmdbmovies.model.MovieListData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movies_adapter.view.*

class MoviesAdapter(val click: RecyclerViewClick) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var movies: ArrayList<MovieListData>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.movies_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (null == movies) 0 else movies?.size!!
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ViewHolder) {
            holder.apply {

                Picasso.get()
                    .load("https://image.tmdb.org/t/p/" + "w300" + movies?.get(position)?.poster_path)
                    .into(articleImageView1)
                txvArticleTitle1.text = movies?.get(position)?.title
                txvAuthorName1.text = movies?.get(position)?.overview
                viewCountTextView1.text =
                    "vote count " + movies?.get(position)?.vote_count.toString()

                commentCountTextView1.text =
                    "vote avg " + movies?.get(position)?.vote_average.toString()

                recommendCountTextView1.text =
                    "release date " + movies?.get(position)?.release_date.toString()

                if (movies?.get(position)?.media_type.isNullOrBlank() || movies?.get(position)?.media_type.equals(
                        "movie"
                    )
                ) {
                    tv.visibility = View.GONE
                } else {
                    tv.visibility = View.VISIBLE
                }
            }
        }
    }

    fun setListData(moviesList: ArrayList<MovieListData>?) {
        movies = moviesList
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        internal val articleImageView1: ImageView
        internal val txvArticleTitle1: TextView
        internal val txvAuthorName1: TextView
        internal val viewCountTextView1: TextView
        internal val commentCountTextView1: TextView
        internal val recommendCountTextView1: TextView
        internal val tv: TextView

        init {
            articleImageView1 = view.articleImageView1
            txvArticleTitle1 = view.txvArticleTitle1
            txvAuthorName1 = view.txvAuthorName1
            viewCountTextView1 = view.viewCountTextView1
            commentCountTextView1 = view.commentCountTextView1
            recommendCountTextView1 = view.recommendCountTextView1
            tv = view.tv
            articleImageView1.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            click.onclick(movies?.get(adapterPosition)?.id!!)
        }
    }


    interface RecyclerViewClick {
        fun onclick(id: Int)
    }
}