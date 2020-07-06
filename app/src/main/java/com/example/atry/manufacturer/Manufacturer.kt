package com.example.atry.manufacturer

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
import com.example.atry.databinding.FragmentManufacturerBinding
import kotlinx.android.synthetic.main.fragment_manufacturer.*

class Manufacturer : Fragment() {
    private lateinit var viewModel: ManufacturerViewModel
    private val idProduct: Int = 1 // This is supposed to arrive through safeArgs later.

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentManufacturerBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_manufacturer,
            container,
            false
        )

        val application = requireNotNull(this.activity).application
        val dataSource = ConzoomDatabase.getInstance(application)
        val viewModelFactory =
            ManufacturerViewModelFactory(
                dataSource,
                application
            )

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ManufacturerViewModel::class.java)
        binding.manufacturerViewModel = viewModel
        binding.setLifecycleOwner(this) // Allows to use LiveData to automatically update DataBinding layouts

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                viewModel.saveValues(idProduct, edit_business_name.text.toString())
            }
        })

        viewModel.onSaveValuesComplete.observe(this, Observer {completed ->
            if (completed) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })

        viewModel.manufacturerLoaded.observe(this, Observer { manufacturer ->
            Log.d("TAG: ", "Manufacturer loaded: $manufacturer")
        })
        return binding.root
    }

    private fun navigationClicked () {
        view?.findNavController()?.navigate(R.id.action_manufacturer_to_user)
    }
}