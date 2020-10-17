package com.ml.chucknorrisapp.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.model.JokeResponse
import com.ml.chucknorrisapp.util.LoadingState
import com.ml.chucknorrisapp.view.adapter.JokeAdapter
import com.ml.chucknorrisapp.view.adapter.JokeAdapter.*
import com.ml.chucknorrisapp.viewmodel.FavouriteJokeViewModel
import com.ml.chucknorrisapp.viewmodel.JokeViewModel
import org.koin.android.viewmodel.ext.android.viewModel



/**
 * Class is used to retrieve and display JokeResponse instances from the local database
 */
class DisplayJokeFragment : Fragment() {

    private val favouriteJokeViewModel:FavouriteJokeViewModel by viewModel()
    private val viewModel:JokeViewModel by viewModel()
    private lateinit var jokeAdapter:JokeAdapter

    /**
     * Function inflates the fragment display joke layout
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_display_joke, container, false)
    }

    /**
    * Function is executed when the view becomes visibile to the user
    */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView(view)
        setupObservers()
    }

    /**
     * Function initialises the RecyclerView and Adapter
     */
    private fun initRecyclerView(view: View){
        jokeAdapter = JokeAdapter(JokeClick {
            viewModel.storeJokeResponse(it)
        })
        view.findViewById<RecyclerView>(R.id.joke_display_recyclerview).apply {
            adapter = jokeAdapter
        }
    }

    /**
     * Function registers observers registered to the LiveData variables in the JokeViewModel
     */
    private fun setupObservers(){
        //Listen for any changes in state on the local room database
        viewModel.jokeLiveData.observe(viewLifecycleOwner, {

            it?.let {
                jokeAdapter.jokesList = it.value
                jokeAdapter.jokeResponse = it
            }
        })

        //Listen for any changes in loading state on the REST API call
        viewModel.loadingState.observe(viewLifecycleOwner, {
            when(it.status){
                LoadingState.Status.SUCCESS -> showSnackBar(getString(R.string.matches_retrieved_text))
                LoadingState.Status.RUNNING -> showSnackBar(getString(R.string.matches_retrieving_text))
                LoadingState.Status.FAILED ->  showSnackBar(getString(R.string.matches_failed_text))
            }
        })

        //Listen for any explicit jokes retrieved by the REST API call
        viewModel.explicitJokeFound.observe(viewLifecycleOwner, {
            showSnackBar(getString(R.string.unable_show_joke_explicit_references_text))
        })

        //Listen for any joke click events saved to the view model
        viewModel.jokeSelectedProperty.observe(viewLifecycleOwner, {
            showJokeOptionsDialog(it)
        })
    }

    /** Function displays a message to the user about the loading state of their request
     *  @param text - String value representing the message to the user
     */
    private fun showSnackBar(text: String){
        Snackbar.make(requireView(), text, Snackbar.LENGTH_LONG).show()
    }

    /**
     * Function displays a AlertDialog to the user confirming if they want
     * to save the joke to their favourites
     */
    private fun showJokeOptionsDialog(jokeResponse: JokeResponse){
        AlertDialog.Builder(requireContext())
            .setTitle("Save Joke to Favourites?")
            .setIcon(R.drawable.ic_star)
            .setMessage(jokeResponse.value[jokeResponse.jokeSelectedId].joke)
            .setCancelable(false)
            .setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                favouriteJokeViewModel.addFavouriteJoke(
                    jokeResponse.value[jokeResponse.jokeSelectedId]
                )
                dialogInterface.dismiss()
            }
            .setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }


}