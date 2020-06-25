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
import com.example.atry.databinding.FragmentPackagingBinding

class Packaging : Fragment() {
    private lateinit var viewModel: PackagingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPackagingBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_packaging,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(PackagingViewModel::class.java)

        binding.packagingViewModel = viewModel
        binding.setLifecycleOwner(this)

        // Call to the ViewModel when Spinners are updated
        spinnerAdapterMaker(binding.spinnerReturnable,
            resources.getStringArray(R.array.returnable),
            "returnable")
        spinnerAdapterMaker(binding.spinnerReusable,
            resources.getStringArray(R.array.reusable),
            "reusable")
        spinnerAdapterMaker(binding.spinnerRecyclable,
            resources.getStringArray(R.array.recyclable),
            "recyclable")
        spinnerAdapterMaker(binding.spinnerCompostable,
            resources.getStringArray(R.array.compostable),
            "compostable")
        spinnerAdapterMaker(binding.spinnerRawMaterialsRecycled,
            resources.getStringArray(R.array.rawMaterialsRecycled),
            "rawMaterialsRecyclable")
        spinnerAdapterMaker(binding.spinnerCertificated,
            resources.getStringArray(R.array.certificated),
            "certificated")

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })

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
                    "returnable" -> viewModel.onReturnableChange(elements.get(position))
                    "reusable" -> viewModel.onReusableChange(elements.get(position))
                    "recyclable" -> viewModel.onRecyclableChange(elements.get(position))
                    "compostable" -> viewModel.onCompostableChange(elements.get(position))
                    "rawMaterialsRecyclable" -> viewModel.onRawMaterialsRecycledChange(elements.get(position))
                    "certificated" -> viewModel.onCertificatedChange(elements.get(position))
                }
            }
        }
    }

    private fun navigationClicked () {
        view?.findNavController()?.navigate(R.id.action_packaging_to_manufacturer)
    }
}
