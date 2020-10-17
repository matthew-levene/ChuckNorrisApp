package com.ml.chucknorrisapp.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.databinding.FragmentHomeBinding
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.util.LoadingState
import com.ml.chucknorrisapp.view.adapter.JokeAdapter
import com.ml.chucknorrisapp.viewmodel.FavouriteJokeViewModel
import com.ml.chucknorrisapp.viewmodel.JokeViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.NumberFormatException

class HomeFragment : Fragment(){

    private val jokeViewModel:JokeViewModel by viewModel()
    private val favouriteJokeViewModel:FavouriteJokeViewModel by viewModel()

    private lateinit var searchEditText: TextInputLayout
    private lateinit var searchButton: Button

    private lateinit var jokeAdapter:JokeAdapter

    /**
     * Function is used to bind the layout file to application components
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.jokeViewModel = jokeViewModel
        binding.homeFragment = this

        return binding.root
    }

    /**
     * Function is executed when the view becomes visible to the user
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSearchViews(view)
        initRecyclerView(view)
        setupObservers()
    }

    /**
     * Function initialises the RecyclerView and Adapter
     */
    private fun initRecyclerView(view: View){
        jokeAdapter = JokeAdapter(JokeAdapter.JokeClick {
            jokeViewModel.storeJokeResponse(it)
        })
        view.findViewById<RecyclerView>(R.id.joke_display_recyclerview).apply {
            adapter = jokeAdapter
        }
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

        if(!query.isNullOrBlank()){
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
        val bottomNavView:BottomNavigationView = requireActivity().findViewById(R.id.navigationView)
        Snackbar.make(bottomNavView, text, Snackbar.LENGTH_LONG).apply {
            anchorView = bottomNavView
        }.show()
    }

    /**
     * Function displays a AlertDialog to the user confirming if they want
     * to save the joke to their favourites
     */
    private fun showJokeOptionsDialog(jokeResponse: JokeResponse){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.save_joke_to_favourites_text))
            .setIcon(R.drawable.ic_star)
            .setMessage(jokeResponse.value[jokeResponse.jokeSelectedId].joke)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes_option_text)) { dialogInterface: DialogInterface, _: Int ->
                favouriteJokeViewModel.addFavouriteJoke(
                    jokeResponse.value[jokeResponse.jokeSelectedId]
                )
                dialogInterface.dismiss()
            }
            .setNegativeButton(getString(R.string.no_option_text)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

    /**Function hides the on-screen keyboard when the user presses the search button
     */
    private fun hideKeyboard(){
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    /**
     * Function registers observers registered to the LiveData variables in the JokeViewModel
     */
    private fun setupObservers(){
        //Listen for any changes in state on the local room database
        jokeViewModel.jokeLiveData.observe(viewLifecycleOwner, {

            it?.let {
                jokeAdapter.jokesList = it.value
                jokeAdapter.jokeResponse = it
            }
        })

        //Listen for any changes in loading state on the REST API call
        jokeViewModel.loadingState.observe(viewLifecycleOwner, {
            when(it.status){
                LoadingState.Status.RUNNING -> showSnackBar(getString(R.string.matches_retrieving_text))
                LoadingState.Status.FAILED ->  showSnackBar(getString(R.string.matches_failed_text))
            }
        })

        //Listen for any explicit jokes retrieved by the REST API call
        jokeViewModel.explicitJokeFound.observe(viewLifecycleOwner, {
            Thread.sleep(500)
            showSnackBar(getString(R.string.unable_show_joke_explicit_references_text))
        })

        //Listen for any joke click events saved to the view model
        jokeViewModel.jokeSelectedProperty.observe(viewLifecycleOwner, {
            showJokeOptionsDialog(it)
        })
    }


}