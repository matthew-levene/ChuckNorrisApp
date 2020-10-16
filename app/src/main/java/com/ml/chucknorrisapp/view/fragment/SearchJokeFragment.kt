package com.ml.chucknorrisapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.databinding.FragmentSearchJokeBinding
import com.ml.chucknorrisapp.viewmodel.JokeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Class is used to handle search input events from the user
 */
class SearchJokeFragment : Fragment() {

    private val jokeViewModel:JokeViewModel by viewModel()
    lateinit var searchEditText: EditText
    lateinit var searchButton: Button

    /**
     * Function is used to bind the layout file to application componetns
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSearchJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.jokeViewModel = jokeViewModel
        binding.searchJokeFragment = this

        return binding.root
    }

    /**
     * Function is executed when the view becomes visibile to the user
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchViews(view)
    }

    /**
     * Initialise Search Views
     * @param view
     */
    private fun initSearchViews(view: View){
        searchEditText = view.findViewById(R.id.joke_search_edittext)
        searchButton = view.findViewById(R.id.search_button)
    }

    /**
     * Function handles the search input and passes it to the JokeViewModel
     */
    fun handleSearchInput(){

    }
}