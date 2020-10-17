package com.ml.chucknorrisapp.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.ml.chucknorrisapp.R
import com.ml.chucknorrisapp.databinding.FragmentFavouritesBinding

import com.ml.chucknorrisapp.model.JokeResponse

import com.ml.chucknorrisapp.view.adapter.JokeAdapter
import com.ml.chucknorrisapp.viewmodel.FavouriteJokeViewModel
import com.ml.chucknorrisapp.viewmodel.JokeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class FavouritesFragment : Fragment(){

    private val jokeViewModel: JokeViewModel by viewModel()
    private val favouriteJokeViewModel: FavouriteJokeViewModel by viewModel()
    private lateinit var jokeAdapter:JokeAdapter

    /**
     * Function is used to bind the layout file to application components
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Function is executed when the view becomes visible to the user
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
        jokeAdapter = JokeAdapter(JokeAdapter.JokeClick {
            jokeViewModel.storeJokeResponse(it)
        })
        view.findViewById<RecyclerView>(R.id.favourites_recyclerview).apply {
            adapter = jokeAdapter
        }
    }

    /**
     * Function registers observers registered to the LiveData variables in the FavouriteJokeViewModel
     */
    private fun setupObservers(){
        //Listen for any changes in state on the local room database
        favouriteJokeViewModel.favouriteJokeLiveData.observe(viewLifecycleOwner, {

            it?.let {
                jokeAdapter.jokesList = it
                jokeAdapter.jokeResponse = JokeResponse(0,1, it)
            }
        })

        //Listen for any joke click events saved to the view model
        jokeViewModel.jokeSelectedProperty.observe(viewLifecycleOwner, {
            showJokeOptionsDialog(it)
        })
    }

    /** Function displays a message to the user about the loading state of their request
     *  @param text - String value representing the message to the user
     */
    private fun showSnackBar(text: String){
        val bottomNavView: BottomNavigationView = requireActivity().findViewById(R.id.navigationView)
        Snackbar.make(bottomNavView, text, Snackbar.LENGTH_LONG).apply {
            anchorView = bottomNavView
        }.show()
    }

    /**
     * Function displays a AlertDialog to the user confirming if they want
     * to remove from favourites
     */
    private fun showJokeOptionsDialog(jokeResponse: JokeResponse){
        AlertDialog.Builder(requireContext())
            .setTitle("Remove From Favourites?")
            .setIcon(R.drawable.ic_star)
            .setMessage(jokeResponse.value[jokeResponse.jokeSelectedId].joke)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.yes_option_text)) { dialogInterface: DialogInterface, _: Int ->
                favouriteJokeViewModel.deleteFavouriteJoke(
                    jokeResponse.value[jokeResponse.jokeSelectedId]
                )
                dialogInterface.dismiss()

                showSnackBar(getString(R.string.successfully_deleted_from_favourites_text))
            }
            .setNegativeButton(getString(R.string.no_option_text)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }

}
