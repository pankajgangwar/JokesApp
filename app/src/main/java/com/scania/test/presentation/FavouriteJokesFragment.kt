package com.scania.test.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
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
    private val viewModel : HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentFavouriteJokeListBinding.inflate(layoutInflater)
        observerFavouriteJokes()
        return binding.root
    }

    private fun observerFavouriteJokes(){
        viewLifecycleOwner.lifecycleScope.launch {
            launch(Dispatchers.IO) {
                /*val jokes = arrayListOf<Joke>()
                jokes.add(Joke(delivery = "This is joke 1", id = 1, flags = Joke.Flags()))
                jokes.add(Joke(delivery = "This is joke 2", id = 2, flags = Joke.Flags()))
                jokes.add(Joke(delivery = "This is joke 3", id = 3, flags = Joke.Flags()))
                jokes.add(Joke(delivery = "This is joke 4", id = 4, flags = Joke.Flags()))

                for(joke in jokes){
                    viewModel.saveJoke(joke)
                }*/
                //delay(5_000)
                viewModel.getFavouriteJokes()
            }
            launch {
                viewModel.favouriteJokesStateFlow.collect { uiState ->
                    when(uiState) {
                        is UIState.Loading -> {
                            binding.favouriteJokesProgress.visibility = View.VISIBLE
                            binding.savedJokeRecyclerView.visibility = View.GONE
                        }
                        is UIState.Success -> {
                            binding.favouriteJokesProgress.visibility = View.GONE
                            binding.savedJokeRecyclerView.visibility = View.VISIBLE

                            if(uiState.jokes.isEmpty()){

                            }
                            binding.savedJokeRecyclerView.adapter = MyJokeItemRecyclerViewAdapter(uiState.jokes)
                        }

                        is UIState.Error -> {

                        }
                    }
                }
            }
        }
    }
}