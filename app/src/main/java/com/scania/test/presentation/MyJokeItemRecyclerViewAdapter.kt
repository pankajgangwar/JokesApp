package com.scania.test.presentation

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.scania.test.R
import com.scania.test.databinding.FragmentFavouriteJokeItemBinding
import com.scania.test.domain.Joke

class MyJokeItemRecyclerViewAdapter(private val jokeList: List<Joke>,
                                    private val callback:  onItemClickListener)
    : RecyclerView.Adapter<MyJokeItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentFavouriteJokeItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val joke = jokeList[position]
        holder.favouriteIcon.setOnClickListener {
            callback.onClick(joke)
        }
        holder.favouriteIcon.setImageResource(R.drawable.ic_favourite_selected)
        if(joke.joke.isEmpty()){
            holder.idView.text = joke.setup
            holder.contentView.text = joke.delivery
        }else{
            holder.idView.text = joke.joke
            holder.contentView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = jokeList.size

    inner class ViewHolder(binding: FragmentFavouriteJokeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.jokeTitle
        val contentView: TextView = binding.jokeDesc
        val favouriteIcon: AppCompatImageView = binding.icFavouriteImg
    }

    interface onItemClickListener {
        fun onClick(joke : Joke)
    }

}