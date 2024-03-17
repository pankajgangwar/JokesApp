package com.scania.test.presentation

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.scania.test.R

import com.scania.test.databinding.FragmentSearchItemBinding
import com.scania.test.domain.Joke

class JokeSearchResultRecyclerViewAdapter(private val searchedJokes: List<Joke>,
                                          private val callback : onItemClickListener)
    : RecyclerView.Adapter<JokeSearchResultRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(FragmentSearchItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val joke = searchedJokes[position]
        holder.favouriteIcon.setOnClickListener {
            callback.onClick(position)
        }
        Log.d("Adapter", "${joke.id} -> ${joke.favourite}")
        if(joke.favourite){
            holder.favouriteIcon.setImageResource(R.drawable.ic_favourite_selected)
        }else{
            holder.favouriteIcon.setImageResource(R.drawable.ic_favourite_un_selected)
        }
        if(joke.joke.isEmpty()){
            holder.idView.text = joke.setup
            holder.contentView.text = joke.delivery
        }else{
            holder.idView.text = joke.joke
            holder.contentView.visibility = View.GONE
        }
    }

    fun updateJoke(position: Int){
        searchedJokes[position].favourite = true
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int = searchedJokes.size

    inner class ViewHolder(binding: FragmentSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.jokeLine1
        val contentView: TextView = binding.jokeLine2
        val favouriteIcon: AppCompatImageView = binding.icFavouriteImg
    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }

}