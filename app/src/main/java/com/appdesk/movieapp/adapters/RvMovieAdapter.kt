package com.appdesk.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appdesk.movieapp.data.Result
import com.appdesk.movieapp.databinding.FragmentListViewMovieBinding
import com.appdesk.movieapp.utils.Constants
import com.squareup.picasso.Picasso

class RvMovieAdapter(
    private val listOfMovies : List<Result>,
    // click listener using lambda function
    private val clickListener : (Result) -> Unit
): RecyclerView.Adapter<MovieViewHolder>() {

    // har item ki layout file kaunsi hai
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = FragmentListViewMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    // kya data dikhana hai
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.apply {
            listOfMovies[position].apply{
                Picasso.get().load(Constants.IMAGE_URL+this.poster_path).into(binding.movieImage)
                binding.movieYear.text = release_date
                binding.movieTitle.text = title
                binding.movieListContainer.setOnClickListener{clickListener(this)}
            }
        }

    }

    // count of items
    override fun getItemCount(): Int = listOfMovies.size
}
class MovieViewHolder (val binding : FragmentListViewMovieBinding): RecyclerView.ViewHolder(binding.root)