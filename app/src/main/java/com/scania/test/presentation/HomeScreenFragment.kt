package com.scania.test.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.scania.test.R
import com.scania.test.databinding.FragmentHomeScreenBinding
import com.scania.test.domain.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment() {

    lateinit var binding: FragmentHomeScreenBinding
    private val viewModel : HomeViewModel by activityViewModels()
    //private lateinit var viewModel : HomeViewModel
    private val TAG = HomeScreenFragment::class.java.simpleName


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        bindProgressButton(binding.homeScreenContinueButton)
        observerFavouriteJokes()
        return binding.root
    }

    private fun observerFavouriteJokes(){
        viewModel.getFavouriteJokes()
        viewLifecycleOwner.lifecycleScope.launch {
            launch(Dispatchers.Main) {
                viewModel.favouriteJokesStateFlow.collect { uiState ->
                    when(uiState) {
                        is UIState.Loading -> {
                            //binding.homeScreenContinueButton.showProgress()
                        }
                        is UIState.Success -> {
                            delay(3_000)
                           // binding.homeScreenContinueButton.hideProgress()
                            if(uiState.jokes.isEmpty()){
                                findNavController().navigate(R.id.action_homeScreenFragment_to_jokeSearchFragment)
                            }else{
                                findNavController().navigate(R.id.action_homeScreenFragment_to_FavouriteJokesFragment)
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