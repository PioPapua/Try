package com.example.atry.product

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
import com.example.atry.databinding.FragmentProductBinding


class Product : Fragment() {
    private lateinit var viewModel: ProductViewModel
    private lateinit var args: ProductArgs

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentProductBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product,
            container,
            false
        )

        // Create and bind viewModel with Database through viewModelFactory.
        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application)
        val viewModelFactory =
            ProductViewModelFactory(
                dataSource,
                application
            )

        // Get safe arguments (barcode and textRecognized)
        args = ProductArgs.fromBundle(requireArguments())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel::class.java)
        viewModel.onBarcodeReceived(args.barcode)

        binding.productViewModel = viewModel
        binding.setLifecycleOwner(this) // Allows to use LiveData to automatically update DataBinding layouts

        // Call to the ViewModel when Spinners are updated
        spinnerAdapterMaker(binding.spinnerCategoryType,
            resources.getStringArray(R.array.category_types),
                "categoryType")
        spinnerAdapterMaker(binding.spinnerPortionType,
            resources.getStringArray(R.array.portion_types),
            "portionType")

        viewModel.onSaveValuesCompleted.observe(this, Observer { saved ->
            if (saved) {
                navigationClicked(viewModel.id.value!!)
                viewModel.onNavigationCompleted()
            }
        })

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                viewModel.saveValues()
            }
        })

        viewModel.setInitialValues(args.barcode, args.textRecognized)
        // TODO Call viewModel to process text from args.textRecognized to get Portion value and type when Product is new.

        return binding.root
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
                    "portionType" -> viewModel.onPortionTypeChange(elements.get(position))
                }
            }
        }
    }

    private fun navigationClicked (idProduct: Int) {
        val action = ProductDirections.actionProductToNutritionFacts(idProduct, args.textRecognized)
        view?.findNavController()?.navigate(action)
    }
}
