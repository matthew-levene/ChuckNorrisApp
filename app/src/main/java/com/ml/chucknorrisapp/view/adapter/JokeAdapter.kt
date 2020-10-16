package com.ml.chucknorrisapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.databinding.RowJokeItemBinding
import com.ml.chucknorrisapp.model.Joke

/**
 * Class is used to provide data to the RecyclerView using data binding
 */
class JokeAdapter : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>(){

    var jokesList: List<Joke> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    /**
     * Function is executed when the binding layout is expected to be inflated
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val view = RowJokeItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JokeViewHolder(view)
    }

    /**
     * Function is executed after the binding layout has been inflated.
     * Data is then bound to the views using data binding
     */
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.binding.also {
            it.joke = jokesList[position]
        }
    }

    /**
     * Function records how many items are jokeList List
     * Returns Integer value
     */
    override fun getItemCount(): Int {
        return jokesList.size
    }

    /**
     * Subclass is used to provide custom ViewHolder functionality to the JokeAdapter class
     */
    class JokeViewHolder(val binding: RowJokeItemBinding) : RecyclerView.ViewHolder(binding.root)
}