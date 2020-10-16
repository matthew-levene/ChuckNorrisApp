package com.ml.chucknorrisapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.databinding.RowJokeItemBinding
import com.ml.chucknorrisapp.model.Joke
import com.ml.chucknorrisapp.model.JokeResponse

/**
 * Class is used to provide data to the RecyclerView using data binding
 */
class JokeAdapter(private val jokeClick: JokeClick) : RecyclerView.Adapter<JokeAdapter.JokeViewHolder>(){

    lateinit var jokeResponse: JokeResponse
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
            it.jokeResponse = jokeResponse
            it.position = position
            it.jokeClick = jokeClick
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

    /**
     * Class is used to handle click events on the RecyclerView
     */
    class JokeClick(val block: (JokeResponse) -> Unit){
        fun onClick(jokeResponse: JokeResponse, position: Int){
            jokeResponse.jokeSelectedId = position
            block(jokeResponse)
        }
    }
}