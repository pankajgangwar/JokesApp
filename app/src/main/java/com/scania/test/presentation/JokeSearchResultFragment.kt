package com.scania.test.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scania.test.R
import com.scania.test.databinding.FragmentSearchResultListBinding
import com.scania.test.domain.Joke
import com.scania.test.domain.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class JokeSearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultListBinding
    private val viewModel : HomeViewModel by activityViewModels()
    private var jokeSearchList = listOf<Joke>()
    private val TAG = "SearchResultFragment"

    companion object {
        val ARG_KEY_URL = "urlKey"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = FragmentSearchResultListBinding.inflate(layoutInflater)

        binding.jokeSearchResultToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        binding.jokeSearchResultToolbar.setTitleTextAppearance(requireContext(), R.style.Theme_ToolBar)

        lifecycleScope.launch {
            launch(Dispatchers.IO) {
                viewModel.searchForJokes(requireArguments().getString(ARG_KEY_URL, ""))
            }

            launch(Dispatchers.Main) {
                viewModel.searchJokesStateFlow.collectLatest { uiState ->
                    when(uiState){
                        is UIState.Loading -> {
                            binding.searchedJokesProgress.visibility = View.VISIBLE
                            binding.jokeSearchResultList.visibility = View.GONE

                            binding.noJokesImageView.visibility = View.GONE
                            binding.noJokesFoundText.visibility = View.GONE
                        }
                        is UIState.Success -> {
                            binding.searchedJokesProgress.visibility = View.GONE
                            binding.jokeSearchResultList.visibility = View.VISIBLE

                            binding.noJokesImageView.visibility = View.GONE
                            binding.noJokesFoundText.visibility = View.GONE

                            jokeSearchList = uiState.jokes
                            val adapter = JokeSearchResultRecyclerViewAdapter(jokeSearchList, callBack)
                            binding.jokeSearchResultList.adapter = adapter
                        }
                        is UIState.Error -> {
                            binding.searchedJokesProgress.visibility = View.GONE
                            binding.jokeSearchResultList.visibility = View.GONE

                            binding.noJokesImageView.visibility = View.VISIBLE
                            binding.noJokesFoundText.visibility = View.VISIBLE
                            binding.noJokesFoundText.text = uiState.message

                            Log.e(TAG, "Error ${uiState.message}")
                        }
                    }
                }
            }
        }
        return binding.root
    }

    private val callBack = object  : JokeSearchResultRecyclerViewAdapter.onItemClickListener {
        override fun onClick(position : Int) {
            if(jokeSearchList[position].favourite){
                Toast.makeText(requireContext(), "Joke is already added to favourites", Toast.LENGTH_SHORT).show()
            }else{
                lifecycleScope.launch(Dispatchers.IO) {
                    val insertedId = viewModel.saveJoke(jokeSearchList[position])
                    if(insertedId >= 0){
                        withContext(Dispatchers.Main){
                            val adapter = binding.jokeSearchResultList.adapter as JokeSearchResultRecyclerViewAdapter
                            adapter.updateJoke(position)
                            Toast.makeText(requireContext(), "Joke added to favourites", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

}