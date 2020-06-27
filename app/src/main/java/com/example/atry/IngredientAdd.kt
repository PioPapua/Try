package com.example.atry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.atry.database.Database
import com.example.atry.databinding.FragmentIngredientAddBinding


class IngredientAdd : Fragment() {
    private lateinit var viewModel: IngredientAddViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentIngredientAddBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_ingredient_add,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = Database.getInstance(application).ingredientDatabaseDao
        val viewModelFactory = IngredientAddViewModelFactory(dataSource, application)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(IngredientAddViewModel::class.java)
        binding.ingredientAddViewModel = viewModel
        binding.setLifecycleOwner(this)

        // Call to the ViewModel when Spinner is updated
        spinnerAdapterMaker(binding.spinnerCategoryType,
            resources.getStringArray(R.array.category_types),
            "categoryType")
        viewModel.onAddButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                onAddButtonClicked()
                viewModel.onNavigationCompleted()
            }
        })
        return binding.root
    }
    private fun onAddButtonClicked () {
        view?.findNavController()?.navigate(R.id.action_ingredientAdd_to_ingredientsTable)
    }

    private fun spinnerAdapterMaker (spinner: Spinner, elements: Array<String>, viewModelElement: String){
        spinner.adapter = ArrayAdapter<String>(requireContext(),
            android.R.layout.simple_list_item_1,
            elements
        )
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Case not supported as there is no void option for the user to select.
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (viewModelElement) {
                    "categoryType" -> viewModel.onCategoryTypeChange(elements.get(position))
                }
            }
        }
    }
}
