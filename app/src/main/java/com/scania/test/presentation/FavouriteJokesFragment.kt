package com.scania.test.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scania.test.R
import com.scania.test.databinding.FragmentFavouriteJokeListBinding
import com.scania.test.domain.Joke
import com.scania.test.domain.JokesRoomUseCase
import com.scania.test.domain.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class FavouriteJokesFragment : Fragment() {

    lateinit var binding: FragmentFavouriteJokeListBinding
    private val viewModel : HomeViewModel by activityViewModels()
    private val TAG = FavouriteJokesFragment::class.java.simpleName

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFavouriteJokeListBinding.inflate(layoutInflater)
        binding.searchForJoke.setOnClickListener {
            findNavController().navigate(R.id.action_favouriteJokesFragment_to_jokeSearchFragment)
        }
        binding.favouriteJokeToolbar.setTitleTextAppearance(requireContext(), R.style.Theme_ToolBar)

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
        observerFavouriteJokes()
        return binding.root
    }

    private fun observerFavouriteJokes(){
        viewModel.getFavouriteJokes()
        viewLifecycleOwner.lifecycleScope.launch {
            launch(Dispatchers.Main) {
                viewModel.favouriteJokesStateFlow.collect { uiState ->
                    //Log.d(TAG, " ${uiState}")
                    when(uiState) {
                        is UIState.Loading -> {
                            binding.favouriteJokesProgress.visibility = View.VISIBLE
                            binding.savedJokeRecyclerView.visibility = View.GONE
                        }
                        is UIState.Success -> {
                            binding.favouriteJokesProgress.visibility = View.GONE
                            binding.savedJokeRecyclerView.visibility = View.VISIBLE

                            if(uiState.jokes.isEmpty()){
                                findNavController().navigate(R.id.action_favouriteJokesFragment_to_jokeSearchFragment)
                            }else{
                                val adapter = MyJokeItemRecyclerViewAdapter(uiState.jokes,
                                    object  : MyJokeItemRecyclerViewAdapter.onItemClickListener {
                                        override fun onClick(joke: Joke) {
                                            lifecycleScope.launch(Dispatchers.IO) {
                                                viewModel.removeJoke(joke)
                                            }
                                            Toast.makeText(requireContext(), "Joke removed from favourites", Toast.LENGTH_SHORT).show()
                                        }
                                    })
                                binding.savedJokeRecyclerView.adapter = adapter
                            }
                        }

                        is UIState.Error -> {

                        }
                    }
                }
            }
        }
    }
}