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

        viewModel.onNextButtonClicked.observe(this, Observer { nextClicked ->
            if (nextClicked) {
                navigationClicked()
                viewModel.onNavigationCompleted()
            }
        })

        return binding.root
    }

    private fun navigationClicked () {
        view?.findNavController()?.navigate(R.id.action_packaging_to_manufacturer)
    }
}
