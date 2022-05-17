package com.appdesk.movieapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.appdesk.movieapp.databinding.FragmentMovieDetailsBinding
import com.appdesk.movieapp.utils.Constants
import com.appdesk.movieapp.utils.Resource
import com.appdesk.movieapp.viewModel.MovieViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var _binding: FragmentMovieDetailsBinding
    private val binding get() = _binding!!
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.backPress.setOnClickListener {
            findNavController().popBackStack()
        }
        getMovieDetailFromViewModel(args.movieId)
    }

    private fun getMovieDetailFromViewModel(movieId: Int)
    {
        viewModel.getMoviesDetails(movieId)
        viewModel._movieDetails.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    it.data!!.apply {
                        Picasso.get().load(Constants.IMAGE_URL+this.poster_path).into(binding.posterImage)
                        binding.movieOriginalTitle.text = this.original_title
                        binding.movieTitle.text = this.title
                        binding.releaseDate.text = this.release_date
                        binding.language.text = this.original_language
                        binding.overview.text = this.overview
                    }
                }
                is Resource.Error ->{
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}