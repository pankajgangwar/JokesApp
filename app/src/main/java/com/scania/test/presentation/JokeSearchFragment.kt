package com.scania.test.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.scania.test.R
import com.scania.test.databinding.FragmentJokeSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.text.StringBuilder

@AndroidEntryPoint
class JokeSearchFragment : Fragment() {

    private val viewModel : HomeViewModel by viewModels()
    private lateinit var binding: FragmentJokeSearchBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentJokeSearchBinding.inflate(layoutInflater)
        binding.navigateBackSearchToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        binding.searchButton.setOnClickListener {
            val url = buildUrl()
            findNavController().navigate(
                R.id.action_jokeSearchFragment_to_jokeSearchResultFragment,
                bundleOf( JokeSearchResultFragment.ARG_KEY_URL to url )
            )
        }
        binding.categoryAny.setOnCheckedChangeListener { buttonView, isChecked ->
            toggleCustomCheckBox(!isChecked)
        }
        return binding.root
    }

    private fun toggleCustomCheckBox(enabled : Boolean) {
        binding.christmasCheckbox.isEnabled = enabled
        binding.programmingCheckbox.isEnabled = enabled
        binding.miscCheckbox.isEnabled = enabled
        binding.darkCheckbox.isEnabled = enabled
        binding.miscCheckbox.isEnabled = enabled
        binding.punCheckbox.isEnabled = enabled
        binding.spookyCheckbox.isEnabled = enabled
    }

    private fun buildUrl() : String {
        val urlBuilder = StringBuilder()
        if(binding.categoryAny.isChecked) {
            urlBuilder.append("Any")
        }else if(binding.categoryCustom.isChecked && anyCustomCheckboxIsEnabled()){
            val categoryBuilder = StringBuilder()
            if(binding.programmingCheckbox.isChecked){
                categoryBuilder.append("Programming").append(",")
            }
            if(binding.miscCheckbox.isChecked){
                categoryBuilder.append("Miscellaneous").append(",")
            }
            if(binding.darkCheckbox.isChecked){
                categoryBuilder.append("Dark").append(",")
            }
            if(binding.punCheckbox.isChecked){
                categoryBuilder.append("Pun").append(",")
            }
            if(binding.spookyCheckbox.isChecked){
                categoryBuilder.append("Spooky").append(",")
            }
            if(binding.christmasCheckbox.isChecked){
                categoryBuilder.append("Christmas").append(",")
            }
            categoryBuilder.deleteCharAt(categoryBuilder.length - 1)
            urlBuilder.append(categoryBuilder.toString())
        }
        val lang = binding.languageOptionsSpinner.selectedItem.toString()
        val langCode = lang.split(" - ")[0]
        urlBuilder.append("?").append("lang=${langCode}")

        if(anyBlacklistFlagIsEnabled()){
            val blacklistFlagBuilder = StringBuilder()
            if(binding.nsfwCheckbox.isChecked){
                blacklistFlagBuilder.append("nsfw").append(",")
            }
            if(binding.religiousCheckbox.isChecked){
                blacklistFlagBuilder.append("religious").append(",")
            }
            if(binding.politicalCheckbox.isChecked){
                blacklistFlagBuilder.append("political").append(",")
            }
            if(binding.racistCheckbox.isChecked){
                blacklistFlagBuilder.append("racist").append(",")
            }
            if(binding.sexistCheckbox.isChecked){
                blacklistFlagBuilder.append("sexist").append(",")
            }
            if(binding.explicitCheckbox.isChecked){
                blacklistFlagBuilder.append("explicit").append(",")
            }
            blacklistFlagBuilder.deleteCharAt(blacklistFlagBuilder.length - 1)
            urlBuilder.append("&").append("blacklistFlags=$blacklistFlagBuilder")
        }
        val searchQuery = binding.searchForJokeEditText.text.toString()
        if(searchQuery.isNotEmpty()){
            urlBuilder.append("&").append("contains=${searchQuery}")
        }
        urlBuilder.append("&amount=100")
        return urlBuilder.toString()
    }

    private fun anyCustomCheckboxIsEnabled(): Boolean {
        return (binding.programmingCheckbox.isChecked || binding.miscCheckbox.isChecked ||
                binding.darkCheckbox.isChecked || binding.punCheckbox.isChecked || binding.spookyCheckbox.isChecked
                || binding.christmasCheckbox.isChecked)
    }

    private fun anyBlacklistFlagIsEnabled(): Boolean {
        return (binding.nsfwCheckbox.isChecked || binding.religiousCheckbox.isChecked ||
                binding.politicalCheckbox.isChecked || binding.racistCheckbox.isChecked || binding.sexistCheckbox.isChecked
                || binding.explicitCheckbox.isChecked)
    }

}