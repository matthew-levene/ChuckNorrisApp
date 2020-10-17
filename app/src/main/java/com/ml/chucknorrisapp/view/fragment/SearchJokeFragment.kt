package com.ml.chucknorrisapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.databinding.FragmentSearchJokeBinding
import com.ml.chucknorrisapp.viewmodel.JokeViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.NumberFormatException

/**
 * Class is used to handle search input events from the user
 */
class SearchJokeFragment : Fragment() {

    private val jokeViewModel:JokeViewModel by viewModel()
    lateinit var searchEditText: TextInputLayout
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
        searchEditText = view.findViewById(R.id.joke_id_search_edittext)
        searchButton = view.findViewById(R.id.search_button)
    }

    /**
     * Function converts the search input to an Integer value
     * and checks if it is within range of the joke IDs
     * If it is, then the ID is passed to the jokeViewModel
     */
    fun handleSearchInput(){

        val query = searchEditText.editText?.text.toString()

        if(query.isNotEmpty()){
            try{
                val jokeId = Integer.parseInt(query)
                if(jokeId in 1..520) {
                    jokeViewModel.searchSpecificJoke(jokeId)
                    searchEditText.editText?.setText("")
                }
                else
                    throw NumberFormatException()

                hideKeyboard()
            }
            catch (nfe: NumberFormatException){
                showSnackBar(getString(R.string.please_enter_valid_num_1_and_520))
                hideKeyboard()
            }
        }
        else {
            showSnackBar(getString(R.string.please_enter_valid_num_1_and_520))
            hideKeyboard()
        }
    }

    /** Function displays a message to the user about the loading state of their request
     *  @param text - String value representing the message to the user
     */
    private fun showSnackBar(text: String){
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }

    /**Function hides the on-screen keyboard when the user presses the search button
     */
    private fun hideKeyboard(){
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}