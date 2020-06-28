package com.example.atry.ingredientsTable

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.R
import com.example.atry.database.ConzoomDatabase
import com.example.atry.databinding.FragmentIngredientsTableBinding

class IngredientsTable : Fragment() {
    private lateinit var viewModel: IngredientsTableViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentIngredientsTableBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ingredients_table,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application).ingredientDatabaseDao
        val viewModelFactory =
            IngredientsTableViewModelFactory(
                dataSource,
                application
            )

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IngredientsTableViewModel::class.java)
        binding.ingredientsTableViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.onAddButtonClicked.observe(this, Observer { addClicked ->
            if (addClicked) {
                additionClicked()
                viewModel.onAdditionCompleted()
            }
        })

        viewModel.ingredients.observe(this, Observer { ingredients ->
            Log.d ("TAG: ", "Ingredientes desde el observer: $ingredients")
            // TODO Build the table with ingredients
        })

        return binding.root
    }

    private fun additionClicked () {
        view?.findNavController()?.navigate(R.id.action_ingredientsTable_to_ingredientAdd)
    }

    private fun navigationClicked () {
        view?.findNavController()?.navigate(R.id.action_packaging_to_manufacturer)
    }
}
