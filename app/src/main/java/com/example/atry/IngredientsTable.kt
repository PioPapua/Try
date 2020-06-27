package com.example.atry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.database.Database
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
        val dataSource = Database.getInstance(application).ingredientDatabaseDao
        val viewModelFactory = IngredientsTableViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IngredientsTableViewModel::class.java)
        binding.ingredientsTableViewModel = viewModel
        binding.setLifecycleOwner(this)

        viewModel.onAddButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                additionClicked()
                viewModel.onAdditionCompleted()
            }
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
