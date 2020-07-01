package com.example.atry.nutritionFacts

import android.os.Bundle
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
import com.example.atry.databinding.FragmentNutritionFactsBinding

class NutritionFacts : Fragment() {
    private lateinit var viewModel: NutritionFactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNutritionFactsBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_nutrition_facts,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application).nutritionFactDao
        val viewModelFactory =
            NutritionFactsViewModelFactory(
                dataSource,
                application
            )

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NutritionFactsViewModel::class.java)
        binding.nutritionFactsViewModel = viewModel
        binding.setLifecycleOwner(this)

        // Add Nutrition Facts button allows us to navigate to Nutrition Facts table to add new entries to our list.
        viewModel.onAddNutritionFacts.observe(this, Observer { addNutritionFactsClicked ->
            if (addNutritionFactsClicked) {
                onAddNutritionFactsClicked()
                viewModel.onAddNutritionFactsCompleted()
            }
        })

        // Add Button stores the values on the BD
        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })

        return binding.root
    }

    // Allows us to manually add optional Nutrition Facts that are not listed by default.
    private fun onAddNutritionFactsClicked () {
        view?.findNavController()?.navigate(R.id.action_nutritionFacts_to_nutritionFactsTable)

    }

    private fun navigationClicked () {
        view?.findNavController()?.navigate(R.id.action_nutritionFacts_to_packaging)
    }
}
