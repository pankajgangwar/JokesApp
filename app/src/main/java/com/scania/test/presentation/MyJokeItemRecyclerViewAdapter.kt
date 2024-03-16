package com.scania.test.presentation

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.scania.test.databinding.FragmentFavouriteJokeItemBinding
import com.scania.test.domain.Joke

class MyJokeItemRecyclerViewAdapter(private val jokeList: List<Joke>)
    : RecyclerView.Adapter<MyJokeItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentFavouriteJokeItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val joke = jokeList[position]
        holder.idView.text = joke.delivery
    }

    override fun getItemCount(): Int = jokeList.size

    inner class ViewHolder(binding: FragmentFavouriteJokeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
    }

}