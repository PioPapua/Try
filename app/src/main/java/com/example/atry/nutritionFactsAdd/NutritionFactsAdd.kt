package com.example.atry.nutritionFactsAdd

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
import com.example.atry.R
import com.example.atry.database.ConzoomDatabase
import com.example.atry.databinding.FragmentNutritionFactsAddBinding


class NutritionFactsAdd : Fragment() {
    private lateinit var viewModel: NutritionFactsAddViewModel
    private lateinit var args: NutritionFactsAddArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentNutritionFactsAddBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_nutrition_facts_add,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application).nutritionFactDao
        val viewModelFactory =
            NutritionFactsAddViewModelFactory(
                dataSource,
                application
            )

        // Get safe arguments (idProduct and textRecognized)
        args = NutritionFactsAddArgs.fromBundle(requireArguments())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(NutritionFactsAddViewModel::class.java)
        binding.nutritionFactsAddViewModel = viewModel
        binding.setLifecycleOwner(this)

        // Call to the ViewModel when Spinner is updated
        spinnerAdapterMaker(binding.spinnerPortionType,
            resources.getStringArray(R.array.portion_types),
            "portionType")

        viewModel.onAddButtonClicked.observe(viewLifecycleOwner, Observer { nextClicked ->
            if (nextClicked) {
                onAddButtonClicked()
                viewModel.onNavigationCompleted()
            }
        })
        return binding.root
    }
    private fun onAddButtonClicked () {
        val action = NutritionFactsAddDirections.actionNutritionFactsAddToNutritionFactsTable(args.idProduct, args.textRecognized)
        view?.findNavController()?.navigate(action)
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
                    "portionType" -> viewModel.onPortionTypeChange(elements.get(position))
                }
            }
        }
    }
}
