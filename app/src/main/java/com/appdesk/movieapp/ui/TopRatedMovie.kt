package com.appdesk.movieapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.appdesk.movieapp.R
import com.appdesk.movieapp.adapters.RvMovieAdapter
import com.appdesk.movieapp.data.Result
import com.appdesk.movieapp.databinding.FragmentTopRatedMovieBinding
import com.appdesk.movieapp.utils.Resource
import com.appdesk.movieapp.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedMovie : Fragment() {

    lateinit var binding: FragmentTopRatedMovieBinding

    val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: RvMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopRatedMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.getTopRatedMovies()
        observeViewModel()
    }

    private fun setRecyclerView(list: List<Result>) {
        movieAdapter = RvMovieAdapter(list){

        }
        binding.movieRecycler.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        binding.movieRecycler.adapter = RvMovieAdapter(list){gotoMovieDetail(it)}
    }

    private fun observeViewModel() {
        viewModel._topRatedMovieLiveData.observe(viewLifecycleOwner){ response ->
            when(response) {
                is Resource.Success -> {
                    response.data?.let { list -> setRecyclerView(list) }
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
    private fun gotoMovieDetail(result: Result){
        findNavController().navigate(
            TopRatedMovieDirections.actionTopRatedMovieToMovieDetailsFragment(result.id)
        )
    }
}