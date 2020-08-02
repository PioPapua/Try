package com.example.atry.labelAdd

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
import com.example.atry.databinding.FragmentLabelAddBinding


class LabelAdd : Fragment() {
    private lateinit var viewModel: LabelAddViewModel
    private lateinit var args: LabelAddArgs

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLabelAddBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_label_add,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application).labelDao
        val viewModelFactory =
            LabelAddViewModelFactory(
                dataSource,
                application
            )

        // Get safe arguments (idProduct, textRecognized)
        args = LabelAddArgs.fromBundle(requireArguments())

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LabelAddViewModel::class.java)
        binding.labelAddViewModel = viewModel
        binding.setLifecycleOwner(this)

        // Call to the ViewModel when Spinner is updated
        spinnerAdapterMaker(binding.spinnerCategoryType,
            resources.getStringArray(R.array.category_types),
            "categoryType")

        viewModel.onAddButtonClicked.observe(viewLifecycleOwner, Observer { nextClicked ->
            if (nextClicked) {
                onAddButtonClicked()
                viewModel.onNavigationCompleted()
            }
        })
        return binding.root
    }
    private fun onAddButtonClicked () {
        view?.findNavController()?.navigate(R.id.action_labelAdd_to_labelsTable)
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
