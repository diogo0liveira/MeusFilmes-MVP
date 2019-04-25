package com.dao.mymovies.features.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dao.mymovies.R
import com.dao.mymovies.base.Recycler
import com.dao.mymovies.databinding.ViewRowMovieBinding
import com.dao.mymovies.model.Movie

/**
 * Created in 03/08/18 15:27.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesAdapter(
        context: Context,
        private val listener: MovieViewOnClickListener) :
        Recycler.Adapter<Movie, MyMoviesAdapter.ViewHolder>(context, COMPARATOR)
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
         val binding: ViewRowMovieBinding = this.inflate(parent, R.layout.view_row_movie)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, item: Movie?)
    {
        holder.binding.movie = item
    }

    class ViewHolder(val binding: ViewRowMovieBinding, private val listener: MovieViewOnClickListener) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener
    {
        init
        {
            this.itemView.setOnClickListener(this)
        }

        override fun onClick(view: View)
        {
            listener.onMovieViewOnClick(binding.movie!!)
        }
    }

    companion object
    {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Movie>()
        {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = (oldItem.id == newItem.id)
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = (oldItem == newItem)
        }
    }

    interface MovieViewOnClickListener
    {
        fun onMovieViewOnClick(movie: Movie)
    }
}